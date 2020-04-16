package ru.itis.controllers;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itis.dto.SignUpDto;
import ru.itis.services.SignUpService;
import org.springframework.stereotype.Controller;

@Controller
public class SignUpController {

    @Autowired
    private SignUpService signUpService;

    @GetMapping(value = "/signUp")
    public String getSignUp() {
        return "signUp";
    }

    @SneakyThrows
    @PostMapping(value = "/signUp")
    public String signUp(SignUpDto signUpDto) {
        signUpService.signUp(signUpDto);
        return "signUp";
    }

}