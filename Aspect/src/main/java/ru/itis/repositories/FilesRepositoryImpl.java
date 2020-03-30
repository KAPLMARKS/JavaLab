package ru.itis.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.itis.models.FileInfo;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Component
public class FilesRepositoryImpl implements FilesRepository {

    private static final String SQL_SELECT_ALL = "select * from honey.file_table";
    private static final String SQL_SELECT_BY_ID = "select * from honey.file_table where id = ?";
    private static final String SQL_SELECT_BY_STORAGE_NAME = "SELECT * FROM honey.file_table WHERE storage_file_name = ?";
    private static final String SQL_INSERT = "insert into honey.file_table(storage_file_name, original_file_name, size, type_mime, url) values (?, ?, ?, ?, ?)";
    private static final String SQL_DELETE = "DELETE FROM honey.file_table WHERE id = ?";


    private RowMapper<FileInfo> fileInfoRowMapper = (row, rowNumber) ->
            FileInfo.builder()
                    .id(row.getLong("id"))
                    .storageFileName(row.getString("storage_file_name"))
                    .originalFileName(row.getString("original_file_name"))
                    .size(row.getLong("size"))
                    .type(row.getString("type_mime"))
                    .url(row.getString("url"))
                    .build();

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<FileInfo> findAll() {
        return jdbcTemplate.query(SQL_SELECT_ALL, fileInfoRowMapper );
    }

    @Override
    public Optional<FileInfo> find(Long id) {
        try {
            FileInfo fileInfo = jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, new Object[]{id}, fileInfoRowMapper);
            return Optional.ofNullable(fileInfo);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<FileInfo> findByStorageName(String storageName) {
        try {
            FileInfo fileInfo = jdbcTemplate.queryForObject(SQL_SELECT_BY_STORAGE_NAME, new Object[]{storageName}, fileInfoRowMapper);
            return Optional.ofNullable(fileInfo);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void save(FileInfo entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection
                    .prepareStatement(SQL_INSERT);
            statement.setString(1, entity.getStorageFileName());
            statement.setString(2, entity.getOriginalFileName());
            statement.setLong(3, entity.getSize());
            statement.setString(4, entity.getType());
            statement.setString(5, entity.getUrl());
            return statement;
        }, keyHolder);

        entity.setId((Long) keyHolder.getKey());
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(SQL_DELETE, new Object[]{id});
    }


}