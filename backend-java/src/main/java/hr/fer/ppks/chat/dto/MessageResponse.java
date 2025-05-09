package hr.fer.ppks.chat.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Schema(description = "Represents a chat message response")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class MessageResponse {
    @Schema(description = "Message content", example = "Hello world!")
    private String content;

    @Schema(description = "Unique message ID")
    private UUID id;

    @Schema(description = "Timestamp when the message was created")
    private Instant created;

    @Schema(description = "Sender of the message")
    private UserResponse user;

    @Schema(description = "Room to which the message belongs")
    private RoomResponse room;
}
