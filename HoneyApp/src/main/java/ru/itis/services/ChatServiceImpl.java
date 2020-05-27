package ru.itis.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itis.models.Chat;
import ru.itis.repositories.ChatRepository;
import ru.itis.security.UserSecurityImpl;

import java.util.Optional;

@Component
public class ChatServiceImpl implements ChatService {
    @Autowired
    private ChatRepository chatRepository;

    @Override
    public Chat getChat(UserSecurityImpl userDetails) {
        Optional<Chat> optionalChat = chatRepository.findByUserId(userDetails.getUser().getId());
        if (optionalChat.isPresent()) {
            return optionalChat.get();
        } else {
            Chat chat = Chat.builder()
                    .owner(userDetails.getUser())
                    .build();
            chatRepository.save(chat);
            return chat;
        }
    }

    @Override
    public Chat getChatByUserId(Long id) {
        Optional<Chat> optionalChat = chatRepository.findByUserId(id);
        if (optionalChat.isPresent()) {
            return optionalChat.get();
        } else {
            throw new IllegalArgumentException("This user has no chat with u!");
        }
    }
}