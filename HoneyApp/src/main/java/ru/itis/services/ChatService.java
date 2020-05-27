package ru.itis.services;

import ru.itis.models.Chat;
import ru.itis.security.UserSecurityImpl;

public interface ChatService {
    Chat getChat(UserSecurityImpl userSecurity);

    Chat getChatByUserId(Long id);
}