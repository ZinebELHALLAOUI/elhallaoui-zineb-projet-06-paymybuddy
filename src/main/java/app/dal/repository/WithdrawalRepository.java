package app.dal.repository;

import app.dal.entity.Withdrawal;

import java.util.List;

public interface WithdrawalRepository {
    List<Withdrawal> findsWithdrawalsByAccountId(int accountId);
}
