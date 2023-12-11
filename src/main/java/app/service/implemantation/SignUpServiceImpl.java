package app.service.implemantation;

import app.dal.entity.Account;
import app.dal.entity.User;
import app.dal.repository.UserRepository;
import app.dto.SignUpRequest;
import app.service.SignUpService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@AllArgsConstructor
@Service
@Transactional
public class SignUpServiceImpl implements SignUpService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public void signup(SignUpRequest signUpRequest) {
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
