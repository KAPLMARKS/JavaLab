package ru.itis.controllers;

import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itis.dto.SignUpDto;
import ru.itis.services.SignUpService;

import java.io.IOException;

@Controller
public class SignUpController {

    @Autowired
    private SignUpService signUpService;

    @GetMapping(value = "/signUp")
    public String getSignUp() {
        return "signUp";
    }

    @PostMapping(value = "/signUp")
    public String signUp(SignUpDto signUpDto) {
        try {
            signUpService.signUp(signUpDto);
        } catch (IOException | TemplateException e) {
            throw new IllegalArgumentException();
        }
        return "signUp";
    }

}