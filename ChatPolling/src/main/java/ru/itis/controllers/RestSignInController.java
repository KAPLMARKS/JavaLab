package ru.itis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.dto.SignInDto;
import ru.itis.dto.TokenDto;
import ru.itis.services.SignInService;

@RestController
public class RestSignInController {

    @Autowired
    public SignInService signInService;

    @PostMapping("/rest/signIn")
    public ResponseEntity<TokenDto> signIn(@RequestBody SignInDto signInDto) {
        return ResponseEntity.ok(signInService.signInRest(signInDto));
    }
}