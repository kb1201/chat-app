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
@Schema(description = "Request to send a message in a chat room")
public class MessageRequest {

    @NotBlank
    @Schema(description = "Content of the message", example = "Hello, world!")
    private String content;
}