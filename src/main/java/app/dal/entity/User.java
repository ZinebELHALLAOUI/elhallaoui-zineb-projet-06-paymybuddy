package app.dal.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class User {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Account account;// TODO could be lazy loaded

    public BigDecimal computeSoldOfAccount() {
        return this.getAccount().computeSold();
    }

    public int getAccountId() {
        return this.account.getId();
    }

}
