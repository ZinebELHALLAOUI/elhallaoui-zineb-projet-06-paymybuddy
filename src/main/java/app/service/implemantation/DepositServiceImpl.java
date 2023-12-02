package app.service.implemantation;

import app.dal.entity.Deposit;
import app.dal.entity.Transfer;
import app.dal.entity.User;
import app.dal.repository.DepositRepository;
import app.dal.repository.UserRepository;
import app.dto.DepositRequest;
import app.service.DepositService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class DepositServiceImpl implements DepositService {
    private final UserRepository userRepository;
    private final Clock clock;
    private final DepositRepository depositRepository;

    @Override
    public void deposeMoney(DepositRequest depositRequest) {
        final User currentUser = this.userRepository.getCurrentUser();
//TODO Check negative deposit
        final Deposit deposit = new Deposit();
        deposit.setAccountId(currentUser.getAccountId());
        deposit.setAmount(depositRequest.getAmount());
        deposit.setInstant(clock.instant());

        depositRepository.save(deposit);

    }

    @Override
    public List<Deposit> getDepositsOfCurrentUSer() {
        final User currentUser = this.userRepository.getCurrentUser();
        List<Deposit> deposits = depositRepository.findDepositsByAccountId(currentUser.getAccountId());
        return deposits;
    }
}
