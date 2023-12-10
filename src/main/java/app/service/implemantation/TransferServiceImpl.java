package app.service.implemantation;

import app.dal.entity.Transfer;
import app.dal.entity.User;
import app.dal.repository.TransferRepository;
import app.dal.repository.UserRepository;
import app.dto.TransferRequest;
import app.service.SoldCalculatorService;
import app.service.TransferService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Clock;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class TransferServiceImpl implements TransferService {

    private static final double COMMISSION = 0.50D;
    private final UserRepository userRepository;
    private final TransferRepository transferRepository;
    private final SoldCalculatorService soldCalculatorService;
    private final Clock clock;

    @Override
    public void sendMoney(final TransferRequest transferRequest) {
        boolean isUserReceiverExist = this.userRepository.isUserExist(transferRequest.getAccountReceiverId());
        if (!isUserReceiverExist) {
            throw new RuntimeException("Receiver does not exist"); //TODO exception à personnaliser
        }

        final User userSender = this.userRepository.getCurrentUser();

        if (this.isNotSufficientSold(soldCalculatorService.calculate(userSender.getAccount()), transferRequest.getAmount())) {
            throw new RuntimeException("Insufficient sold");//TODO exception à personnaliser
        }

        final Transfer transfer = new Transfer(
                userSender.getAccountId(),
                transferRequest.getAccountReceiverId(),
                transferRequest.getAmount(),
                COMMISSION,
                clock.instant()
        );

        transferRepository.save(transfer);
    }

    @Override
    public List<Transfer> getTransfersOfCurrentUSer() {
        final User currentUser = this.userRepository.getCurrentUser();
        List<Transfer> transfers = transferRepository.findTransfersByAccountId(currentUser.getAccountId());
        return transfers;
    }

    private boolean isSufficientSold(final BigDecimal sold, final BigDecimal transferAmount) {
        return sold.compareTo(transferAmount.multiply(BigDecimal.ONE.add(BigDecimal.valueOf(COMMISSION)))) >= 0;
    }

    private boolean isNotSufficientSold(final BigDecimal sold, final BigDecimal transferAmount) {
        return !isSufficientSold(sold, transferAmount);
    }


}
