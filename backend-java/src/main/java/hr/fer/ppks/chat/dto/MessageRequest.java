package hr.fer.ppks.chat.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class MessageRequest {
    @NotBlank
    private String content;
}
