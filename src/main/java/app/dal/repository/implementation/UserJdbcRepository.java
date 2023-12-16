package app.dal.repository.implementation;

import app.dal.entity.Account;
import app.dal.entity.User;
import app.dal.repository.*;
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

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final DepositRepository depositRepository;
    private final WithdrawalRepository withdrawalRepository;
    private final TransferRepository transferRepository;
    private final AccountRepository accountRepository;

    @Override
    public int countUsersByAccountId(int accountId) {
        final String sql = "SELECT COUNT(*) FROM User WHERE id_account = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, accountId);
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        final String sql = """
                SELECT
                    u.id as user_id, u.first_name, u.last_name, u.email, u.password,
                    a.id as account_id, a.account_number
                FROM
                    User u
                INNER JOIN
                    Account a ON u.id_account = a.id
                WHERE
                    u.email = :email
                """;


        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("email", email);

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
                account.setTransfers(transferRepository.findSendAndReceiveTransfersByAccountId(account.getId()));

                user.setAccount(account);

                return user;
            }));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }

    }

    @Override
    public Optional<User> findUserById(int userId) {
        final String sql = """
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
                account.setTransfers(transferRepository.findSendAndReceiveTransfersByAccountId(account.getId()));

                user.setAccount(account);

                return user;
            }));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean isUserExistByAccountId(int accountId) {
        int count = this.countUsersByAccountId(accountId);
        return count > 0;
    }

    @Override
    public User save(User user) {
        Account newAccount = accountRepository.save(user.getAccount());
        user.setAccount(newAccount);
        final String userSqlInsert = """
                INSERT INTO User (first_name, last_name, email, password, id_account)
                VALUES (:firstName,:lastName,:email,:password,:accountId)
                """;
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("firstName", user.getFirstName());
        params.addValue("lastName", user.getLastName());
        params.addValue("email", user.getEmail());
        params.addValue("password", user.getPassword());
        params.addValue("accountId", user.getAccountId());
        int rowsAffected = namedParameterJdbcTemplate.update(userSqlInsert, params);

        if (rowsAffected == 1) {
            Integer generatedId = namedParameterJdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", new MapSqlParameterSource(), Integer.class);
            user.setId(generatedId);
            this.saveAuthority(user);
            return user;
        } else {
            throw new RuntimeException("Save user failed");
        }
    }

    @Override
    public Optional<User> findUserByAccountId(int accountId) {
        final String sql = """
                SELECT
                    u.id as user_id, u.first_name, u.last_name, u.email, u.password,
                    a.id as account_id, a.account_number
                FROM
                    User u
                INNER JOIN
                    Account a ON u.id_account = a.id
                WHERE
                    u.id_account = :accountId
                """;


        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("accountId", accountId);

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
                account.setTransfers(transferRepository.findSendAndReceiveTransfersByAccountId(account.getId()));

                user.setAccount(account);

                return user;
            }));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    private void saveAuthority(User user) {
        final String authoritySqlInsert = """
                INSERT INTO Authority (email, authority)
                VALUES (:email,:authority)
                """;
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("email", user.getEmail());
        params.addValue("authority", "USER");
        int rowsAffected = namedParameterJdbcTemplate.update(authoritySqlInsert, params);
        if (rowsAffected <= 0) throw new RuntimeException("Save authority failed");
    }

}
