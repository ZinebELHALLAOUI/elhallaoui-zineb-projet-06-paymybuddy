package app.dal.repository.implementation;

import app.dal.entity.Withdrawal;
import app.dal.repository.WithdrawalRepository;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class WithdrawalJdbcRepository implements WithdrawalRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<Withdrawal> findsWithdrawalsByAccountId(int accountId) {
        String sql = "SELECT id, account_id, amount, instant FROM Withdrawal WHERE account_id = :accountId";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("accountId", accountId);

        return namedParameterJdbcTemplate.query(
                sql,
                params,
                BeanPropertyRowMapper.newInstance(Withdrawal.class)
        );
    }

    @Override
    public Withdrawal save(Withdrawal withdrawal) {
        String sql = "INSERT INTO Withdrawal (account_id, amount, instant) " +
                "VALUES (:accountId, :amount, :instant)";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("accountId", withdrawal.getAccountId());
        params.addValue("amount", withdrawal.getAmount());
        params.addValue("instant", withdrawal.getInstant());

        int rowsAffected = namedParameterJdbcTemplate.update(sql, params);

        if (rowsAffected > 0) {
            Integer generatedId = namedParameterJdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", new MapSqlParameterSource(), Integer.class);
            withdrawal.setId(generatedId);
            return withdrawal;
        } else {
            throw new RuntimeException("Save withdrawal failed");
        }

    }
}
