package app.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferDto {
    private String connection;
    private String description = "Fake description";
    private BigDecimal money;
    private String date;
}
