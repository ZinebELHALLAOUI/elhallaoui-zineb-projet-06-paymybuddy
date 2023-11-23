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
}
