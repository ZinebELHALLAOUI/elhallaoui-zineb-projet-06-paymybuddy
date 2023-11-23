package app.dal.repository;

import app.dal.entity.Deposit;
import app.dal.entity.Transfer;

import java.util.List;

public interface TransferRepository {

    List<Transfer> findTransfersByAccountId(int accountId);
    Transfer save(Transfer transfer);
}
