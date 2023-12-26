package app.controller.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferDto {
    private String connection;
    private BigDecimal money;
    private String date;
}
