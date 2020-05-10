package ru.itis.repositories.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.itis.dto.MessageDto;
import ru.itis.models.Message;
import ru.itis.repositories.MessageRepository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class MessageRepositoryImpl implements MessageRepository {
    private static final String SQL_SAVE = "insert into honeyapp.message(userid, pageid, text) " +
            "VALUES(?,?,?) ";
    private static final String SQL_FIND_UNREAD = "select * from honeyapp.message";

    private static final String SQL_UPDATE_STATUS = "UPDATE honeyapp.message set status = true where id = ?";

    private static final String SQL_SELECT_ALL_PAGES_IDS = "select pageid from honeyapp.message";

    private RowMapper<Message> messageRowMapper = (row, rowNumber) ->
            Message.builder()
                    .id(row.getLong("id"))
                    .pageId(row.getString("pageid"))
                    .text(row.getString("text"))
                    .userId(row.getLong("userid"))
                    .build();
    private RowMapper<String> pageRowMapper = (row, number) ->
            String.valueOf(row.getString("pageid"));

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<MessageDto> findAllMessages() {
        try {
            List<Message> messages = jdbcTemplate.query(SQL_FIND_UNREAD,messageRowMapper);
            List<MessageDto> messageDto = new ArrayList<>();
            for(Message message: messages) {
                messageDto.add(message.transformToMessageDto());
            }
            return messageDto;
        } catch (EmptyResultDataAccessException e) {
            throw new EmptyResultDataAccessException(0);
        }
    }

    @Override
    public void updateStatusOfMessage(Long id) {
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_STATUS);
            preparedStatement.setLong(1, id);
            return preparedStatement;
        });
    }

    @Override
    public List<String> getAllPageIds() {
        List<String> pageIds = jdbcTemplate.query(SQL_SELECT_ALL_PAGES_IDS,pageRowMapper);
        return pageIds;
    }


    @Override
    public Optional<Message> find(Long aLong) {
        return Optional.empty();
    }

    @Override
    public List<Message> findAll() {
        return null;
    }

    @Override
    public void save(Message entity) {
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection
                    .prepareStatement(SQL_SAVE,
                            Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, entity.getUserId());
            statement.setString(2, entity.getPageId());
            statement.setString(3, entity.getText());
            return statement;
        });

    }

    @Override
    public void delete(Long aLong) {

    }

}