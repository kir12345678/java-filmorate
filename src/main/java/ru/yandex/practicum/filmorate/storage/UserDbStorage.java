package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;


@Primary
@Repository
@RequiredArgsConstructor
public class UserDbStorage implements UserStorage {

    private final Logger log = LoggerFactory.getLogger(FilmDbStorage.class);
    @Autowired
    private final JdbcTemplate jdbcTemplate;

    @Override
    public User create(User user) throws NotFoundException {

        String userName = user.getName();
        if (userName == null) {
            userName = user.getLogin();
        } else if (userName.isEmpty()) {
            userName = user.getLogin();
        }

        Map<String, Object> values = new HashMap<>();
        values.put("name", userName);
        values.put("email", user.getEmail());
        values.put("login", user.getLogin());
        values.put("birthday", user.getBirthday());

        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("user")
                .usingGeneratedKeyColumns("id");

        return find(simpleJdbcInsert.executeAndReturnKey(values).intValue());
    }

    @Override
    public List<User> getAll() {
        String sql = "select * from users";

        return jdbcTemplate.query(sql, (rs, rowNum) -> makeUser(rs));
    }

    @Override
    public User update(User user) throws NotFoundException {
        find(user.getId());

        String sqlQuery = "update user set " +
                "name = ?, login = ?, email = ?, birthday = ? " +
                "where id = ?";
        jdbcTemplate.update(sqlQuery,
                user.getName(),
                user.getLogin(),
                user.getEmail(),
                user.getBirthday(),
                user.getId());

        if (user.getFriends() != null) {
            String friendsSqlQuery = "delete from friendship where user_id = ?";
            jdbcTemplate.update(friendsSqlQuery, user.getId());
            for (Integer friendId : user.getFriends()) {
                friendsSqlQuery = "insert into friendship(user_id, friend_id, is_accepted) " +
                        "values (?, ?, ?)";
                jdbcTemplate.update(friendsSqlQuery,
                        user.getId(),
                        friendId,
                        false);
            }
        }

        return find(user.getId());
    }

    @Override
    public User find(Integer id) throws NotFoundException {

        String sql = "select * from user where id = ?";

        List<User> userCollection = jdbcTemplate.query(sql, (rs, rowNum) -> makeUser(rs), id);
        if (userCollection.size() == 1) {
            return userCollection.get(0);
        } else {
            log.info("Пользователь не найден с ID - {}", id);
            throw new NotFoundException(String.format("Пользователя с id-%d не существует.", id));
        }
    }

    @Override
    public boolean delete(Integer id) {
        String sqlQuery = "delete from mpa where id = ?";
        jdbcTemplate.update(sqlQuery, id);

        sqlQuery = "delete from mpa where id = ?";
        return jdbcTemplate.update(sqlQuery, id) > 0;
    }

    private User makeUser(ResultSet rs) throws SQLException {
        Integer id = rs.getInt("id");
        String name = rs.getString("name");
        String email = rs.getString("email");
        String login = rs.getString("login");
        LocalDate birthday = rs.getDate("birthday").toLocalDate();

        String friendsSql = "select * from friendship where user_id = ?";
        List<Integer> friendsCollection = jdbcTemplate.query(friendsSql, (rs1, rowNum) -> makeUserFriend(rs1), id);

        return new User(id, name, email, login, birthday, new HashSet<Integer>(friendsCollection));
    }

    private Integer makeUserFriend(ResultSet rs) throws SQLException {
        Integer friendId = rs.getInt("friend_id");
        return friendId;
    }
}
