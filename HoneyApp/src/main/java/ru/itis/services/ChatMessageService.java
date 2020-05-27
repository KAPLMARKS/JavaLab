package ru.itis.services;

import ru.itis.dto.MessageDto;
import ru.itis.models.Chat;

import java.util.List;

public interface ChatMessageService {
    void addChatMessage(MessageDto messageDto, Long chatId);
    List<MessageDto> getAllChatMessages(Chat chat);
    List<MessageDto> getMessages(Long id);
}