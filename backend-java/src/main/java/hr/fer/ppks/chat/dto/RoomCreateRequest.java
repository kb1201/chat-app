package hr.fer.ppks.chat.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@Schema(description = "Request to create a new chat room")
public class RoomCreateRequest {

    @NotBlank(message = "Room name is required")
    @Schema(description = "Name of the room", example = "general")
    private String name;
}