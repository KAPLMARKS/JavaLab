package ru.itis.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.dto.MessageDto;
import ru.itis.models.Message;
import ru.itis.repositories.MessageRepository;
import ru.itis.repositories.UsersRepository;

import java.util.*;

@Service
public class ChatServiceImpl implements ChatService {
    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public List<MessageDto> takeAllMessages() {
        return messageRepository.findAllMessages();
    }

    @Override
    public void addMessage(MessageDto messageDto) {
        Message message = Message.builder()
                .pageId(messageDto.getPageId())
                .text(messageDto.getText())
                .userId(Integer.parseInt(messageDto.getUserId()))
                .build();
        messageRepository.save(message);
    }

    @Override
    public void updateStatusOfMessage(long id) {
        messageRepository.updateStatusOfMessage(id);
    }

    @Override
    public List<String> getAllPageIds() {
        return messageRepository.getAllPageIds();
    }


}