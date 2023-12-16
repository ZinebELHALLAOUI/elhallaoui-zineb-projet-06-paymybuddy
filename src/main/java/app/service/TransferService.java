package app.service;

import app.dal.entity.Transfer;
import app.controller.dto.TransferRequest;
import app.dal.entity.User;

import java.util.List;

public interface TransferService {
    void sendMoney(TransferRequest transferRequest, User user);

    List<Transfer> getTransfersByUser(User user);
}
