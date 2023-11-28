package app.service.implemantation;

import app.controller.transfer.dto.TransferRequest;
import app.dal.entity.Transfer;
import app.dal.entity.User;
import app.dal.repository.TransferRepository;
import app.dal.repository.UserRepository;
import app.service.SendMoneyService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;

@Service
@AllArgsConstructor
@Transactional
public class SendMoneyServiceImpl implements SendMoneyService {

    private static final double COMMISSION = 0.50D;
    private final UserRepository userRepository;
    private final TransferRepository transferRepository;
    private final Clock clock;

    @Override
    public void sendMoney(final TransferRequest transferRequest) {
        boolean isUserReceiverExist = this.userRepository.isUserExist(transferRequest.getAccountReceiverId());
        if (!isUserReceiverExist) {
            throw new RuntimeException("Receiver does not exist"); //TODO exception à personnaliser
        }

        final User userSender = this.userRepository.getCurrentUser();
        final BigDecimal soldOfCurrentUser = userSender.computeSoldOfAccount();

        if (this.isNotSufficientSold(soldOfCurrentUser, transferRequest.getAmount())) {
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

    private boolean isSufficientSold(final BigDecimal sold, final BigDecimal transferAmount) {
        return sold.compareTo(transferAmount.multiply(BigDecimal.ONE.add(BigDecimal.valueOf(COMMISSION)))) >= 0;
    }

    private boolean isNotSufficientSold(final BigDecimal sold, final BigDecimal transferAmount) {
        return !isSufficientSold(sold, transferAmount);
    }

}
