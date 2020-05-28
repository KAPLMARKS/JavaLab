package chat.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class ChatController {

    @GetMapping("/chat")
    public String getChat(Model model, HttpServletRequest request,
                          @RequestParam Long room, HttpServletResponse response) {
        model.addAttribute("room", room);
        return "chat";
    }
}
