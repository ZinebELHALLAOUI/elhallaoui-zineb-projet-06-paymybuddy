package app.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DepositDto {
    private BigDecimal deposit;
    private String date;
}
