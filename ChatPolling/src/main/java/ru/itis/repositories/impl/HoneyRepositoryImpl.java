package ru.itis.repositories.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.itis.models.Honey;
import ru.itis.models.Message;
import ru.itis.repositories.HoneyRepository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Component(value = "honeyRepository")
public class HoneyRepositoryImpl implements HoneyRepository {

    private static final String SQL_SELECT_BY_ID = "select * from honeyapp.honey where id = ?";
    private static final String SQL_SELECT_BY_NAME = "select * from honeyapp.honey where name = ?";
    private static final String SQL_SELECT_ALL = "select * from honeyapp.honey";
    private static final String SQL_INSERT = "insert into honeyapp.honey(name, cost, description) values (?, ?, ?)";
    private static final String SQL_DELETE = "DELETE FROM honeyapp.honey WHERE id = ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    RowMapper<Honey> honeyRowMapper = (row, rowNumber) -> {
        return Honey.builder().id(row.getLong("id"))
                .name(row.getString("name"))
                .cost(row.getInt("cost"))
                .description(row.getString("description"))
                .build();
    };

    @Override
    public Optional<Honey> find(Long id) {
        try {
            Honey honey = jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, new Object[]{id}, honeyRowMapper);
            return Optional.ofNullable(honey);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Honey> findAll() {
        return jdbcTemplate.query(SQL_SELECT_ALL, honeyRowMapper);
    }

    @Override
    public void save(Honey entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection
                    .prepareStatement(SQL_INSERT);
            statement.setString(1, entity.getName());
            statement.setLong(2, entity.getCost());
            statement.setString(3, entity.getDescription());
            return statement;
        }, keyHolder);

        entity.setId((Long) keyHolder.getKey());
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(SQL_DELETE, new Object[]{id});
    }

    @Override
    public Optional<Honey> findByName(String name) {
        try {
            Honey honey = jdbcTemplate.queryForObject(SQL_SELECT_BY_NAME, new Object[]{name}, honeyRowMapper);
            return Optional.ofNullable(honey);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}