package app.dal.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@NoArgsConstructor
public class Transfer extends Transaction {
    private int accountReceiverId;
    private double commission;

    public Transfer(int accountSenderId, int accountReceiverId, BigDecimal amount, double commission, Instant instant) {
        this.accountId = accountSenderId;
        this.accountReceiverId = accountReceiverId;
        this.amount = amount;
        this.commission = commission;
        this.instant = instant;
    }

    public boolean isSender(Account account) {
        return this.accountId == account.getId();
    }
}
