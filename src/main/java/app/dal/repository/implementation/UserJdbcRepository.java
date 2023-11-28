package app.dal.repository.implementation;

import app.dal.entity.Account;
import app.dal.entity.User;
import app.dal.repository.DepositRepository;
import app.dal.repository.TransferRepository;
import app.dal.repository.UserRepository;
import app.dal.repository.WithdrawalRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class UserJdbcRepository implements UserRepository {

    private final static int fakeCurrentUserId = 1;//tmp code

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final DepositRepository depositRepository;
    private final WithdrawalRepository withdrawalRepository;
    private final TransferRepository transferRepository;

    @Override
    public int countUsersByUserId(int userId) {
        String sql = "SELECT COUNT(*) FROM User WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, userId);
    }

    @Override
    public Optional<User> findUserById(int userId) {
        String sql = """
                SELECT
                    u.id as user_id, u.first_name, u.last_name, u.email, u.password,
                    a.id as account_id, a.account_number
                FROM
                    User u
                INNER JOIN
                    Account a ON u.id_account = a.id
                WHERE
                    u.id = :userId
                """;


        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("userId", userId);

        try {
            return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(sql, params, (rs, rowNum) -> {
                User user = new User();
                user.setId(rs.getInt("user_id"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));

                Account account = new Account();
                account.setId(rs.getInt("account_id"));
                account.setAccountNumber(rs.getString("account_number"));
                account.setDeposits(depositRepository.findDepositsByAccountId(account.getId()));
                account.setWithdrawals(withdrawalRepository.findsWithdrawalsByAccountId(account.getId()));
                account.setTransfers(transferRepository.findTransfersByAccountId(account.getId()));

                user.setAccount(account);

                return user;
            }));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }

    }

    @Override
    public boolean isUserExist(int userId) {
        int count = this.countUsersByUserId(userId);
        return count > 0;
    }

    @Override
    public User getCurrentUser() {
        Optional<User> user = this.findUserById(fakeCurrentUserId);
        return user.get();
    }

}
