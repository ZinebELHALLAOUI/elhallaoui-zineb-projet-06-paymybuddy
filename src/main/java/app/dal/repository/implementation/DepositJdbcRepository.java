package app.dal.repository.implementation;

import app.dal.entity.Deposit;
import app.dal.repository.DepositRepository;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class DepositJdbcRepository implements DepositRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<Deposit> findDepositsByAccountId(int accountId) {
        String sql = "SELECT id, account_id, amount, instant FROM Deposit WHERE account_id = :accountId";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("accountId", accountId);

        return namedParameterJdbcTemplate.query(
                sql,
                params,
                BeanPropertyRowMapper.newInstance(Deposit.class)
        );
    }

    @Override
    public Deposit save(Deposit deposit) {
        String sql = "INSERT INTO Deposit (account_id, amount, instant) " +
                "VALUES (:accountId, :amount, :instant)";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("accountId", deposit.getAccountId());
        params.addValue("amount", deposit.getAmount());
        params.addValue("instant", deposit.getInstant());

        int rowsAffected = namedParameterJdbcTemplate.update(sql, params);

        if (rowsAffected > 0) {
            Integer generatedId = namedParameterJdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", new MapSqlParameterSource(), Integer.class);
            deposit.setId(generatedId);
            return deposit;
        } else {
            throw new RuntimeException("Save deposit failed");
        }
    }
}
