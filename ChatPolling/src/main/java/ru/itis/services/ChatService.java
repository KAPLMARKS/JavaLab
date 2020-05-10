package ru.itis.services;

import ru.itis.dto.MessageDto;

import java.util.List;

public interface ChatService {
    List<MessageDto> takeAllMessages();

    void addMessage(MessageDto messageDto);

    void updateStatusOfMessage(long id);

    List<String> getAllPageIds();
}