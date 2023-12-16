package app.service.implemantation;

import app.controller.dto.SignUpRequest;
import app.dal.entity.Account;
import app.dal.entity.User;
import app.dal.repository.UserRepository;
import app.service.SoldCalculatorService;
import app.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public Optional<String> getUserNameByAccountId(int accountId) {
        Optional<User> user = userRepository.findUserByAccountId(accountId);
        return user.map(u -> u.getFirstName() + " " + u.getLastName());
    }

    @Override
    public void signup(SignUpRequest signUpRequest) {
        final String email = signUpRequest.getEmail();
        if (userRepository.findUserByEmail(email).isPresent()) {
            throw new RuntimeException("Account already exists");
        }
        final User user = new User();
        user.setFirstName(signUpRequest.getFirstname());
        user.setLastName(signUpRequest.getLastname());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        Account account = new Account();
        account.setAccountNumber(UUID.randomUUID().toString());
        user.setAccount(account);
        userRepository.save(user);
    }
}
