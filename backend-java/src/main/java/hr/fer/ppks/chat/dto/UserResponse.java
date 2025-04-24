package hr.fer.ppks.chat.dto;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private String username;
    private Long id;
    private Set<RoomResponse> rooms;
}