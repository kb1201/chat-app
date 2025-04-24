package hr.fer.ppks.chat.mapper;

import hr.fer.ppks.chat.db.model.Room;
import hr.fer.ppks.chat.dto.RoomResponse;

public class RoomMapper {
    public static RoomResponse toResponse(final Room room) {
        return RoomResponse.builder()
                .name(room.getName())
                .id(room.getId())
                .build();
    }
}
