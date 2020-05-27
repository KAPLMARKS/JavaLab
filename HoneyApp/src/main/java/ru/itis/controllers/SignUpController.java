package ru.itis.controllers;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itis.dto.SignUpDto;
import ru.itis.services.SignUpService;

import javax.validation.Valid;

@Controller
public class SignUpController {

    @Autowired
    private SignUpService signUpService;

    @GetMapping(value = "/signUp")
    public String getSignUp(Model model) {
        model.addAttribute("signUpDto", new SignUpDto());
        return "signUp";
    }

    @SneakyThrows
    @PostMapping(value = "/signUp")
    public String signUp(@Valid SignUpDto signUpDto, BindingResult result, Model model) {
        if(!(result.hasErrors()))
            if (signUpService.signUp(signUpDto))
                return "redirect:/signIn";
        model.addAttribute("signUpDto", signUpDto);
        return "signUp";
    }

}