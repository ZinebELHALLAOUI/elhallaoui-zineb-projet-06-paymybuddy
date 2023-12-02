package app.service;

import app.dal.entity.Deposit;
import app.dto.DepositRequest;

import java.util.List;

public interface DepositService {
    void deposeMoney(DepositRequest depositRequest);

    List<Deposit> getDepositsOfCurrentUSer();
}
