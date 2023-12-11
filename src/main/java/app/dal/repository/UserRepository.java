package app.dal.repository;

import app.dal.entity.User;

import java.util.Optional;

public interface UserRepository {
    int countUsersByUserId(int userId);

    Optional<User> findUserByEmail(String email);

    boolean isUserExist(int userId);

    User getCurrentUser();

    User save(User user);

}
