package app.service;

import app.dal.entity.User;
import app.dal.entity.Withdrawal;
import app.controller.dto.WithdrawalRequest;

import java.util.List;

public interface WithdrawalService {
    void withdrawMoney(WithdrawalRequest withdrawalRequest, User user);

    List<Withdrawal> getWithdrawalsByUser(User user);
}
