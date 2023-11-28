package app.service;

import app.dto.WithdrawalRequest;

public interface WithdrawalService {
    void withdrawalMoney(WithdrawalRequest withdrawalRequest);
}
