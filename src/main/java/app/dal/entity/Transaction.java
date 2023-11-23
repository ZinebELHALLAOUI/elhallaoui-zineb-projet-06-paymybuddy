package app.dal.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
public abstract class Transaction {
    protected Integer id;
    protected int accountId;
    protected BigDecimal amount;
    protected Instant instant;
}
