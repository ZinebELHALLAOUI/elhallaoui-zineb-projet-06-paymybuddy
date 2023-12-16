package app.service;

import app.controller.dto.SignUpRequest;

import java.util.Optional;

public interface UserService {

    Optional<String> getUserNameByAccountId(int accountId);
    void signup(SignUpRequest signUpRequest);

}
