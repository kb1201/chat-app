package hr.fer.ppks.chat.service;


import hr.fer.ppks.chat.db.model.Room;
import hr.fer.ppks.chat.db.model.RoomUser;
import hr.fer.ppks.chat.db.repository.RoomRepository;
import hr.fer.ppks.chat.db.repository.UserRepository;
import hr.fer.ppks.chat.dto.UserRequest;
import hr.fer.ppks.chat.dto.UserResponse;
import hr.fer.ppks.chat.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponse create(final UserRequest rq) {
        String encodedPassword = passwordEncoder.encode(rq.getPassword());
        RoomUser user = UserMapper.toEntity(rq);
        user.setPassword(encodedPassword);

        RoomUser result = this.userRepository.save(user);
        return UserMapper.toResponse(result);
    }


    public UserResponse addUserToRoom(Long userId, UUID roomId) {
        Optional<Room> roomOpt = roomRepository.findById(roomId);
        Optional<RoomUser> userOpt = userRepository.findById(userId);

        if (roomOpt.isEmpty() || userOpt.isEmpty()) return null;

        Room room = roomOpt.get();
        RoomUser owner = userOpt.get();
        owner.getRooms().add(room);

        return UserMapper.toResponse(userRepository.save(owner));
    }

    public boolean removeRoom(Long userId, UUID roomId) {
        Optional<Room> roomOpt = roomRepository.findById(roomId);
        Optional<RoomUser> userOpt = userRepository.findById(userId);

        if (roomOpt.isEmpty() || userOpt.isEmpty()) return false;

        Room room = roomOpt.get();
        RoomUser user = userOpt.get();
        boolean removed = user.getRooms().remove(room);
        if (removed) {
            roomRepository.save(room);
        }
        return removed;
    }

    public List<UserResponse> findAllUsersByRoomId(UUID roomId) {
        return userRepository.findAllByRoomId(roomId)
                .stream().map(UserMapper::toResponse).collect(Collectors.toList());
    }
}