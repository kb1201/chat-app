package hr.fer.ppks.chat.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class RoomCreateRequest {
    @NotBlank(message = "Room name is required")
    private String name;
}