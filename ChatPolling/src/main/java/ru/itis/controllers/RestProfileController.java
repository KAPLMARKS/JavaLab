package ru.itis.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.dto.UserDto;
import ru.itis.models.User;
import ru.itis.security.UserSecurityImpl;

@RestController
public class RestProfileController {

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/rest/profile")
    public ResponseEntity<UserDto> getSelf() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserSecurityImpl userDetails = (UserSecurityImpl) authentication.getDetails();
        User user = userDetails.getUser();
        System.out.println(userDetails);
        return ResponseEntity.ok(UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .build());
    }
}