package app.service;

import app.dto.DepositRequest;

public interface DepositService {
    void deposeMoney(DepositRequest depositRequest);
}
