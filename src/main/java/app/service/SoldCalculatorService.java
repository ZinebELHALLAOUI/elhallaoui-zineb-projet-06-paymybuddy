package app.service;

import app.dal.entity.Account;

import java.math.BigDecimal;

public interface SoldCalculatorService {
    BigDecimal calculate(Account account);
}
