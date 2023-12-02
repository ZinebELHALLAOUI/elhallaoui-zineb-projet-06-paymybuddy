package app.service.implemantation;

import app.dal.entity.User;
import app.dal.repository.UserRepository;
import app.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public BigDecimal getSoldOfCurrentUser() {
        User currentUser = userRepository.getCurrentUser();
        return currentUser.computeSoldOfAccount();
    }
}
