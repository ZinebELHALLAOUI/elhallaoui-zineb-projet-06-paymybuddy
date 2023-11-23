package app.controller.transfer.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferRequest {
    private int accountReceiverId;
    private BigDecimal amount;
}
