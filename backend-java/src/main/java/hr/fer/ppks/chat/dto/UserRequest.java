package hr.fer.ppks.chat.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Schema(description = "User credentials used for login or registration")
public class UserRequest {

    @Email(message = "Username must be a valid email address")
    @Schema(description = "Email address as username", example = "john.doe@example.com")
    private String username;

    @Pattern(regexp = "^(?=.*[0-9])(?=.*[A-Z]).{8,}$",
            message = "Password must contain at least one number, one uppercase letter, and be longer than 8 characters")
    @Schema(description = "Secure password", example = "StrongPass123")
    private String password;
}