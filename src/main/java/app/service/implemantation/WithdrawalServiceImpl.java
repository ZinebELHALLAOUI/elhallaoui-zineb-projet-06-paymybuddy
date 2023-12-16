package app.service.implemantation;

import app.controller.dto.WithdrawalRequest;
import app.dal.entity.User;
import app.dal.entity.Withdrawal;
import app.dal.repository.UserRepository;
import app.dal.repository.WithdrawalRepository;
import app.service.SoldCalculatorService;
import app.service.WithdrawalService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Clock;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class WithdrawalServiceImpl implements WithdrawalService {
    private final Clock clock;
    private final WithdrawalRepository withdrawalRepository;
    private final SoldCalculatorService soldCalculatorService;

    @Override
    public void withdrawMoney(WithdrawalRequest withdrawalRequest, User user) {
        BigDecimal sold = soldCalculatorService.calculate(user.getAccount());
        if(sold.compareTo(withdrawalRequest.getAmount()) < 0){
            throw new RuntimeException("Withdraw greater than your sold");
        }
        final Withdrawal withdrawal = new Withdrawal();
        withdrawal.setAccountId(user.getAccountId());
        withdrawal.setAmount(withdrawalRequest.getAmount());
        withdrawal.setInstant(clock.instant());

        withdrawalRepository.save(withdrawal);
    }

    @Override
    public List<Withdrawal> getWithdrawalsByUser(User user) {
        List<Withdrawal> withdrawals = withdrawalRepository.findsWithdrawalsByAccountId(user.getAccountId());
        return withdrawals;
    }
}
