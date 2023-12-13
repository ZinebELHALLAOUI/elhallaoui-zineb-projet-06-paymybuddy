package app.dal.repository;

import app.dal.entity.Transfer;

import java.util.List;

public interface TransferRepository {

    List<Transfer> findSendAndReceiveTransfersByAccountId(int accountId);
    List<Transfer> findSendTransfersByAccountId(int accountId);
    Transfer save(Transfer transfer);
}
