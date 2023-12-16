package app.service.implemantation;

import app.controller.dto.DepositRequest;
import app.dal.entity.Deposit;
import app.dal.entity.User;
import app.dal.repository.DepositRepository;
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
    private final Clock clock;
    private final DepositRepository depositRepository;

    @Override
    public void deposeMoney(DepositRequest depositRequest, User user) {
        final Deposit deposit = new Deposit();
        deposit.setAccountId(user.getAccountId());
        deposit.setAmount(depositRequest.getAmount());
        deposit.setInstant(clock.instant());

        depositRepository.save(deposit);

    }

    @Override
    public List<Deposit> getDepositsByUser(User user) {
        List<Deposit> deposits = depositRepository.findDepositsByAccountId(user.getAccountId());
        return deposits;
    }
}
