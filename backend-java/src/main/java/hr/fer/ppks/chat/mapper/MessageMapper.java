package hr.fer.ppks.chat.mapper;

import hr.fer.ppks.chat.db.model.Message;
import hr.fer.ppks.chat.dto.MessageResponse;

public class MessageMapper {

    public static MessageResponse toResponse(final Message entity) {
        return MessageResponse.builder()
                .content(entity.getContent())
                .user(UserMapper.toResponseSimple(entity.getUser()))
                .room(RoomMapper.toResponse(entity.getRoom()))
                .created(entity.getCreated())
                .id(entity.getId())
                .build();
    }
}
