package ru.itis.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.itis.models.User;
import ru.itis.security.UserSecurityImpl;
@Controller
public class ChatController {

    @GetMapping("/chat")
    public String getChatPage(Model model, Authentication authentication) {
        UserSecurityImpl userDetails = (UserSecurityImpl) authentication.getPrincipal();
        User user = userDetails.getUser();
        model.addAttribute("user", user);
        model.addAttribute("pageId", user.getId());
        return "chat";
    }
}