package app.service;

import app.dal.entity.Withdrawal;
import app.dto.WithdrawalRequest;

import java.util.List;

public interface WithdrawalService {
    void withdrawMoney(WithdrawalRequest withdrawalRequest);

    List<Withdrawal> getWithdrawalsOfCurrentUSer();
}
