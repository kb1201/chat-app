package hr.fer.ppks.chat.dto;

import lombok.*;

import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class RoomResponse {
    private UUID id;

    private String name;

}
