package app.service.implemantation;

import app.controller.transfer.dto.TransferRequest;
import app.dal.entity.Transfer;
import app.dal.entity.User;
import app.dal.repository.TransferRepository;
import app.service.SendMoneyService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Clock;

@Service
@AllArgsConstructor
@Transactional
public class SendMoneyServiceImpl implements SendMoneyService {

    private static final double COMMISSION = 0.50D;
    private final UserService userService;
    private final TransferRepository transferRepository;
    private final Clock clock;

    @Override
    public void sendMoney(final TransferRequest transferRequest) {
        boolean userExist = this.userService.isUserExist(transferRequest.getAccountReceiverId());
        if (!userExist) {
            throw new RuntimeException("Receiver does not exist");
        }

        final User currentUser = this.userService.getCurrentUser();
        final BigDecimal soldOfCurrentUser = currentUser.computeSoldOfAccount();

        if (this.isNotSufficientSold(soldOfCurrentUser, transferRequest.getAmount())) {
            throw new RuntimeException("Insufficient sold");
        }

        final Transfer transfer = new Transfer(
                currentUser.getAccountId(),
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
