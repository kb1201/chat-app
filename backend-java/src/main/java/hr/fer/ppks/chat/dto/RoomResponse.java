package hr.fer.ppks.chat.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@Schema(description = "Response with basic room info")
public class RoomResponse {

    @Schema(description = "UUID of the room", example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID id;

    @Schema(description = "Name of the room", example = "general")
    private String name;
}
