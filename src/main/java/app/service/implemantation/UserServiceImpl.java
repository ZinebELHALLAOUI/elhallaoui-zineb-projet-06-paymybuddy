package app.service.implemantation;

import app.dal.entity.User;
import app.dal.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final static int fakeCurrentUserId = 1;//tmp code
    private final UserRepository userRepository;

    @Override
    public boolean isUserExist(int userId) {
        int count = userRepository.countUsersByUserId(userId);
        return count > 0;
    }

    @Override
    public User getCurrentUser() {
        Optional<User> user = userRepository.findUserById(fakeCurrentUserId);
        return user.get();
    }

}
