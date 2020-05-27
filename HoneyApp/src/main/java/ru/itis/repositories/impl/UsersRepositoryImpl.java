package ru.itis.repositories.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.itis.models.Role;
import ru.itis.models.State;
import ru.itis.models.User;
import ru.itis.repositories.UsersRepository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Component(value = "usersRepository")
public class UsersRepositoryImpl implements UsersRepository {
    private static final String SQL_SELECT_CODE = "SELECT * FROM honeyapp.users WHERE code = ?";
    private static final String SQL_SELECT_LOGIN = "SELECT * FROM honeyapp.users WHERE email = ?";
    private static final String SQL_SELECT_BY_ID = "select * from honeyapp.users where id = ?";
    private static final String SQL_SELECT_ALL = "select * from honeyapp.users";
    private static final String SQL_INSERT = "insert into honeyapp.users(username, email, password, state, code, role) values (?, ?, ?, ?, ?, ?)";
    private static final String SQL_DELETE = "DELETE FROM honeyapp.users WHERE id = ?";
    private static final String SQL_UPDATE = "UPDATE honeyapp.users SET state = ? WHERE email = ?";
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private RowMapper<User> userRowMapper = (row, rowNumber) -> {
        Role role;
        String strRole = row.getString("role");
        if(strRole.equals("ADMIN"))
            role = Role.ADMIN;
        else
            role = Role.USER;
        State state;
        String strState = row.getString("state");
        if(strState.equals("CONFIRMED"))
            state = State.CONFIRMED;
        else
            state = State.NOT_CONFIRMED;
        return User.builder()
                .username(row.getString("username"))
                .id(row.getLong("id"))
                .email(row.getString("email"))
                .password(row.getString("password"))
                .code(row.getString("code"))
                .state(state)
                .role(role)
                .build();
    };

    @Override
    public Optional<User> findByConfirmCode(String code) {
        try {
            User user = jdbcTemplate.queryForObject(SQL_SELECT_CODE, new Object[]{code}, userRowMapper);
            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void update(String email) {
        jdbcTemplate.update(SQL_UPDATE, State.CONFIRMED.toString(), email);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        try {
            User user = jdbcTemplate.queryForObject(SQL_SELECT_LOGIN, new Object[]{email}, userRowMapper);
            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> find(Long id) {
        try {
            User user = jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, new Object[]{id}, userRowMapper);
            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query(SQL_SELECT_ALL, userRowMapper);
    }

    @Override
    public void save(User entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection
                    .prepareStatement(SQL_INSERT);
            statement.setString(1, entity.getUsername());
            statement.setString(2, entity.getEmail());
            statement.setString(3, entity.getPassword());
            statement.setString(4, entity.getState().toString());
            statement.setString(5, entity.getCode());
            statement.setString(6, Role.USER.toString());
            return statement;
        }, keyHolder);

        entity.setId((Long) keyHolder.getKey());
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(SQL_DELETE, new Object[]{id});
    }
}