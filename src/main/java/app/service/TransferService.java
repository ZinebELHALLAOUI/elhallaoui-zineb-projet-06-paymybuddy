package app.service;

import app.dal.entity.Transfer;
import app.dto.TransferRequest;

import java.util.List;

public interface TransferService {
    void sendMoney(TransferRequest transferRequest);

    List<Transfer> getTransfersOfCurrentUSer();
}
