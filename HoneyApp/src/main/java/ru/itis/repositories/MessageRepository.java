package ru.itis.repositories;

import ru.itis.models.Chat;
import ru.itis.models.Message;

import java.util.List;

public interface MessageRepository extends CrudRepository<Long, Message>{
    List<Message> getAllByChat(Chat chat);
}