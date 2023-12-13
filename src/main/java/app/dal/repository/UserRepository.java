package app.dal.repository;

import app.dal.entity.User;

import java.util.Optional;

public interface UserRepository {
    int countUsersByAccountId(int accountId);

    Optional<User> findUserByEmail(String email);

    Optional<User> findUserById(int userId);

    boolean isUserExistByAccountId(int accountId);

    User getCurrentUser();

    User save(User user);

    Optional<User> findUserByAccountId(int accountId);
}
