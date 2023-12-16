package app.service;

import app.controller.dto.DepositRequest;
import app.dal.entity.Deposit;
import app.dal.entity.User;

import java.util.List;

public interface DepositService {
    void deposeMoney(DepositRequest depositRequest, User user);

    List<Deposit> getDepositsByUser(User user);
}
