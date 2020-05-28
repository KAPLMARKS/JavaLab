package chat.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class RoomController {

    @GetMapping("/rooms")
    public String getRooms(Model model, HttpServletRequest request, Authentication authentication,
                           HttpServletResponse response) {
        return "rooms";
    }
}
