package app.dal.repository;

import app.dal.entity.Account;

public interface AccountRepository {
    Account save(Account account);
}
