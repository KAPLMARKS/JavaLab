package chat.controllers;

import chat.models.Room;
import chat.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RoomRestController {

    @Autowired
    private RoomService roomService;

    @GetMapping("/api/rooms")
    public ResponseEntity<List<Room>> getRooms(){
        return ResponseEntity.ok(roomService.findAll());
    }
}
