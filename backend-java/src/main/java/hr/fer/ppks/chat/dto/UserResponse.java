package hr.fer.ppks.chat.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "User response containing basic user info and joined rooms")
public class UserResponse {

    @Schema(description = "Username (email)", example = "john.doe@example.com")
    private String username;

    @Schema(description = "User ID", example = "42")
    private Long id;

    @Schema(description = "Rooms the user has joined")
    private Set<RoomResponse> rooms;
}