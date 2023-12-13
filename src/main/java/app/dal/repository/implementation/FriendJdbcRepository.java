package app.dal.repository.implementation;

import app.dal.entity.Friend;
import app.dal.repository.FriendRepository;
import app.dal.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class FriendJdbcRepository implements FriendRepository {

    private final UserRepository userRepository;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Friend save(Friend friend) {
        String sql = """
                INSERT INTO Friend (user_id, user_friend_id) 
                VALUES (:userId, :userFriendId)
                """;

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("userId", friend.getUser().getId());
        params.addValue("userFriendId", friend.getFriend().getId());

        int rowsAffected = namedParameterJdbcTemplate.update(sql, params);

        if (rowsAffected == 1) {
            Integer generatedId = namedParameterJdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", new MapSqlParameterSource(), Integer.class);
            friend.setId(generatedId);
            return friend;
        } else {
            throw new RuntimeException("Save friend failed");
        }
    }

    @Override
    public List<Friend> getFriendByUserId(int userId) {
        String sql = "SELECT id, user_id, user_friend_id FROM Friend WHERE user_id = :userId";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("userId", userId);

        return namedParameterJdbcTemplate.query(
                sql,
                params,
                (rs, rowNum) ->
                        new Friend(
                                rs.getInt("id"),
                                userRepository.findUserById(rs.getInt("user_id")).orElse(null),
                                userRepository.findUserById(rs.getInt("user_friend_id")).orElse(null)
                        )
        );
    }
}
