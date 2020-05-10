package ru.itis.repositories;

import ru.itis.dto.MessageDto;
import ru.itis.models.Message;

import java.util.List;

public interface MessageRepository extends CrudRepository<Long, Message> {
    List<MessageDto> findAllMessages();

    void updateStatusOfMessage(Long id);

    List<String> getAllPageIds();
}