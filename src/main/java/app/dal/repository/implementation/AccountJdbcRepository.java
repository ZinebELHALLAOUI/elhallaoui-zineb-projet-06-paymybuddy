package app.dal.repository.implementation;

import app.dal.entity.Account;
import app.dal.repository.AccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class AccountJdbcRepository implements AccountRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Account save(Account account) {
        String sql = """
                INSERT INTO Account (account_number)
                VALUES (:accountNumber)
                """;

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("accountNumber", account.getAccountNumber());
        int rowsAffected = namedParameterJdbcTemplate.update(sql, params);

        if (rowsAffected == 1) {
            Integer generatedId = namedParameterJdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", new MapSqlParameterSource(), Integer.class);
            account.setId(generatedId);
            return account;
        } else {
            throw new RuntimeException("Save account failed");
        }
    }
}
