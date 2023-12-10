package app.dal.entity;

import lombok.Data;

import java.util.List;

@Data
public class Account {
    private Integer id;
    private String accountNumber;
    private List<Deposit> deposits;// TODO could be lazy loaded
    private List<Withdrawal> withdrawals;// TODO could be lazy loaded
    private List<Transfer> transfers;// TODO could be lazy loaded
}
