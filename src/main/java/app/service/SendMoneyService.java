package app.service;

import app.dto.TransferRequest;

public interface SendMoneyService {
    void sendMoney(TransferRequest transferRequest);
}
