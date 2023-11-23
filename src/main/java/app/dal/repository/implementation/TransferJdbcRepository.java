package app.dal.repository.implementation;

import app.dal.entity.Transfer;
import app.dal.repository.TransferRepository;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class TransferJdbcRepository implements TransferRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<Transfer> findTransfersByAccountId(int accountId) {
        String sql = """
                SELECT id, account_id_sender, account_id_receiver, amount, commission_percentage, instant
                FROM Transfer WHERE account_id_sender = :accountId
                """;

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("accountId", accountId);

        return namedParameterJdbcTemplate.query(
                sql,
                params,
                BeanPropertyRowMapper.newInstance(Transfer.class)
        );
    }

    @Override
    public Transfer save(Transfer transfer) {
        String sql = "INSERT INTO Transfer (account_id_sender, account_id_receiver, amount, commission_percentage, instant) " +
                "VALUES (:senderId, :receiverId, :amount, :commission, :instant)";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("senderId", transfer.getAccountId());
        params.addValue("receiverId", transfer.getAccountReceiverId());
        params.addValue("amount", transfer.getAmount());
        params.addValue("commission", transfer.getCommission());
        params.addValue("instant", transfer.getInstant());

        int rowsAffected = namedParameterJdbcTemplate.update(sql, params);

        if (rowsAffected > 0) {
            Integer generatedId = namedParameterJdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", new MapSqlParameterSource(), Integer.class);
            transfer.setId(generatedId);
            return transfer;
        } else {
            throw new RuntimeException("Save transfer failed");
        }
    }
}
