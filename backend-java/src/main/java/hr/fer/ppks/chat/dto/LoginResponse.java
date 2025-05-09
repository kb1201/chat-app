package hr.fer.ppks.chat.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@Schema(description = "Response returned after successful login")
public class LoginResponse {

    @Schema(description = "JWT token", example = "eyJhbGciOiJIUzI1NiIsIn...")
    private String token;

    @Schema(description = "Token expiration time in seconds", example = "3600")
    private long expiresIn;

    @Schema(description = "ID of the authenticated user", example = "42")
    private Long id;
}
