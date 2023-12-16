package app.service.implemantation;

import app.controller.dto.TransferRequest;
import app.dal.entity.Transfer;
import app.dal.entity.User;
import app.dal.repository.TransferRepository;
import app.dal.repository.UserRepository;
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
    public void sendMoney(final TransferRequest transferRequest, User user) {
        boolean isUserReceiverExist = this.userRepository.isUserExistByAccountId(transferRequest.getAccountReceiverId());
        if (!isUserReceiverExist) {
            throw new RuntimeException("Receiver does not exist");
        }


        if (this.isNotSufficientSold(soldCalculatorService.calculate(user.getAccount()), transferRequest.getAmount())) {
            throw new RuntimeException("Insufficient sold");
        }

        final Transfer transfer = new Transfer(
                user.getAccountId(),
                transferRequest.getAccountReceiverId(),
                transferRequest.getAmount(),
                COMMISSION,
                clock.instant()
        );

        transferRepository.save(transfer);
    }

    @Override
    public List<Transfer> getTransfersByUser(User user) {
        List<Transfer> transfers = transferRepository.findSendTransfersByAccountId(user.getAccountId());
        return transfers;
    }

    private boolean isSufficientSold(final BigDecimal sold, final BigDecimal transferAmount) {
        BigDecimal commissionPercentage = BigDecimal.valueOf(COMMISSION);
        BigDecimal fractionCommission = commissionPercentage.divide(BigDecimal.valueOf(100));
        BigDecimal commissionTransfer = fractionCommission.multiply(transferAmount);
        return sold.subtract(commissionTransfer).compareTo(transferAmount) >= 0;
    }

    private boolean isNotSufficientSold(final BigDecimal sold, final BigDecimal transferAmount) {
        return !isSufficientSold(sold, transferAmount);
    }


}
