package app.service;

import app.controller.transfer.dto.TransferRequest;

public interface SendMoneyService {

    void sendMoney(TransferRequest transferRequest);


}
