package app.dal.repository;

import app.dal.entity.Deposit;

import java.util.List;

public interface DepositRepository {
    List<Deposit> findDepositsByAccountId(int accountId);

    Deposit save(Deposit deposit);

}
