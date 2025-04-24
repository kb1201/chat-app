package hr.fer.ppks.chat.dto;

import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class MessageResponse {
    private String content;

    private UUID id;

    private Instant created;

    private UserResponse user;

    private RoomResponse room;
}
