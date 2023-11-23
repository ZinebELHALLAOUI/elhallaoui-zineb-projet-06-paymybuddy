package app.dal.repository;

import app.dal.entity.User;

import java.util.Optional;

public interface UserRepository {
    int countUsersByUserId(int userId);

    Optional<User> findUserById(int userId);
}
