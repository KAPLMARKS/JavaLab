package ru.itis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itis.dto.MessageDto;
import ru.itis.services.ChatMessageService;

import java.util.List;

@RestController
public class RestMessageController {

    @Autowired
    private ChatMessageService chatMessageService;

    @GetMapping("/rest/messages")
    public ResponseEntity<List<MessageDto>> getMessages(@RequestParam("userId") Long id) {
        List<MessageDto> messages = chatMessageService.getMessages(id);
        return ResponseEntity.ok(messages);
    }

    @PostMapping("/rest/messages")
    public ResponseEntity<List<MessageDto>> receiveMessage(@RequestBody MessageDto messageDto, @RequestBody Long chatId) {
        chatMessageService.addChatMessage(messageDto, chatId);
        return ResponseEntity.ok().build();
    }
}