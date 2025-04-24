package hr.fer.ppks.chat.mapper;

import hr.fer.ppks.chat.db.model.RoomUser;
import hr.fer.ppks.chat.dto.UserRequest;
import hr.fer.ppks.chat.dto.UserResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Set;
import java.util.stream.Collectors;

public class UserMapper {
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public static RoomUser toEntity(final UserRequest dto) {
        return RoomUser.builder()
                .username(dto.getUsername())
                .password(encoder.encode(dto.getPassword()))
                .build();
    }

    public static UserResponse toResponse(final RoomUser entity) {
        return UserResponse.builder()
                .rooms(
                        entity.getRooms() == null ? Set.of() :
                                entity.getRooms().stream()
                                        .map(RoomMapper::toResponse)
                                        .collect(Collectors.toSet())
                )
                .username(entity.getUsername())
                .id(entity.getId())
                .build();
    }

    public static UserResponse toResponseSimple(final RoomUser entity) {
        return UserResponse.builder()
                .username(entity.getUsername())
                .id(entity.getId())
                .build();
    }
}