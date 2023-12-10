package app.service.implemantation;

import app.dal.entity.User;
import app.dal.entity.Withdrawal;
import app.dal.repository.UserRepository;
import app.dal.repository.WithdrawalRepository;
import app.dto.WithdrawalRequest;
import app.service.WithdrawalService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class WithdrawalServiceImpl implements WithdrawalService {
    private final UserRepository userRepository;
    private final Clock clock;
    private final WithdrawalRepository withdrawalRepository;

    @Override
    //checker le solde.
    public void withdrawMoney(WithdrawalRequest withdrawalRequest) {
        final User currentUser = this.userRepository.getCurrentUser();
        final Withdrawal withdrawal = new Withdrawal();
        withdrawal.setAccountId(currentUser.getAccountId());
        withdrawal.setAmount(withdrawalRequest.getAmount());
        withdrawal.setInstant(clock.instant());

        withdrawalRepository.save(withdrawal);
    }

    @Override
    public List<Withdrawal> getWithdrawalsOfCurrentUSer() {
        final User currentUser = this.userRepository.getCurrentUser();
        List<Withdrawal> withdrawals = withdrawalRepository.findsWithdrawalsByAccountId(currentUser.getAccountId());
        return withdrawals;
    }
}
