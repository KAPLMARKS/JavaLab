package ru.itis.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itis.dto.MessageDto;
import ru.itis.models.Chat;
import ru.itis.models.Message;
import ru.itis.repositories.ChatRepository;
import ru.itis.repositories.MessageRepository;
import ru.itis.repositories.UsersRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ChatMessageServiceImpl implements ChatMessageService {
    private final List<Message> messages = new ArrayList<>();

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private UsersRepository usersRepository;
    @Override
    public void addChatMessage(MessageDto messageDto, Long chatId) {
        Optional<Chat> optionalChat = chatRepository.find(chatId);
        if (optionalChat.isPresent()) {
            Chat chat = optionalChat.get();
            Message message = Message.builder()
                    .text(messageDto.getText())
                    .user(usersRepository.find(messageDto.getUserId()).get())
                    .chat(chat)
                    .build();
            messages.add(message);
            messageRepository.save(message);
            messages.notifyAll();
        }
    }

    @Override
    public List<MessageDto> getAllChatMessages(Chat chat) {
        return fromChatMessage(messageRepository.getAllByChat(chat));
    }

    private List<MessageDto> fromChatMessage(List<Message> messages) {
        List<MessageDto> messageDtos = new ArrayList<>();
        for (Message message : messages)
            messageDtos.add(MessageDto.builder()
                    .text(message.getText())
                    .userId(message.getUser().getId())
                    .build());
        return messageDtos;
    }

    @Override
    public List<MessageDto> getMessages(Long id) {
        synchronized (messages) {
            if (messages.isEmpty()) {
                try {
                    messages.wait();
                } catch (InterruptedException e) {
                    throw new IllegalArgumentException(e);
                }
            }
        }
        List<Message> chatMessages = messages.stream()
                .filter(message -> message
                        .getUser()
                        .getId()
                        .equals(id))
                .collect(Collectors.toList());
        messages.clear();
        return fromChatMessage(chatMessages);
    }
}