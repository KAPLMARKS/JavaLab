package chat.services;

import chat.models.Room;

import java.util.List;

public interface RoomService {
    List<Room> findAll();
}
