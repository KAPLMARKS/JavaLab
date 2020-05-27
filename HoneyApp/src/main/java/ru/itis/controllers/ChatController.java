package ru.itis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.dto.MessageDto;
import ru.itis.models.Chat;
import ru.itis.security.UserSecurityImpl;
import ru.itis.services.ChatMessageService;
import ru.itis.services.ChatService;

import java.util.List;

@Controller
public class ChatController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private ChatMessageService chatMessageService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/chat")
    public ModelAndView getChat() {
        ModelAndView modelAndView = new ModelAndView();
        UserSecurityImpl userSecurity = (UserSecurityImpl) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        Chat chat = chatService.getChat(userSecurity);
        List<MessageDto> messages = chatMessageService.getAllChatMessages(chat);
        modelAndView.addObject("userId", userSecurity.getUser().getId());
        modelAndView.addObject("chatId", chat.getId());
        modelAndView.addObject("messages", messages);
        modelAndView.setViewName("chat");
        return modelAndView;
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/chat/{userId}")
    public ModelAndView getAdminChat(@PathVariable Long userId) {
        ModelAndView modelAndView = new ModelAndView();
        UserSecurityImpl userSecurity = (UserSecurityImpl) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        Chat chat = chatService.getChatByUserId(userId);
        List<MessageDto> messages = chatMessageService.getAllChatMessages(chat);
        modelAndView.addObject("userId", userSecurity.getUser().getId());
        modelAndView.addObject("chatId", chat.getId());
        modelAndView.addObject("messages", messages);
        modelAndView.setViewName("chat");
        return modelAndView;
    }
}