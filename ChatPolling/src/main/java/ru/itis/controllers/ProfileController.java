package ru.itis.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.models.User;
import ru.itis.security.UserSecurityImpl;

@Controller
public class ProfileController {

    @GetMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView getProfilePage() {
        UserSecurityImpl userDetails = (UserSecurityImpl) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        User user = userDetails.getUser();
        ModelAndView modelAndView = new ModelAndView("profile");
        modelAndView.addObject("user", user);
        return modelAndView;
    }


}