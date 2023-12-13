package app.service.implemantation;

import app.dal.entity.User;
import app.dal.repository.AccountRepository;
import app.dal.repository.UserRepository;
import app.service.SoldCalculatorService;
import app.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final SoldCalculatorService soldCalculatorService;

    @Override
    public BigDecimal getSoldOfCurrentUser() {
        User currentUser = userRepository.getCurrentUser();
        return soldCalculatorService.calculate(currentUser.getAccount());
    }

    @Override
    public Optional<String> getUserNameByAccountId(int accountId) {
        Optional<User> user = userRepository.findUserByAccountId(accountId);
        return user.map(u -> u.getFirstName() + " " + u.getLastName());
    }
}
