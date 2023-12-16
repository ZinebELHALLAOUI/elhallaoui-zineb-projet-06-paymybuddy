package app.controller.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class WithdrawalDto {
    private BigDecimal withdrawal;
    private String date;
}
