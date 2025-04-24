package hr.fer.ppks.chat.service;

import hr.fer.ppks.chat.db.model.Room;
import hr.fer.ppks.chat.db.model.RoomUser;
import hr.fer.ppks.chat.db.repository.RoomRepository;
import hr.fer.ppks.chat.db.repository.UserRepository;
import hr.fer.ppks.chat.dto.RoomResponse;
import hr.fer.ppks.chat.mapper.RoomMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    public RoomResponse createRoom(String name, RoomUser owner) {
        RoomUser persistentOwner = userRepository.findById(owner.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        //save room
        Room room = Room.builder().name(name).id(UUID.randomUUID()).build();
        var savedRoom = RoomMapper.toResponse(roomRepository.save(room));

        //set room member relationship
        persistentOwner.getRooms().add(room);
        userRepository.save(persistentOwner);

        return savedRoom;
    }

    public RoomResponse getRoomById(UUID roomId) {
        var room = roomRepository.findById(roomId);
        return room.map(RoomMapper::toResponse).orElse(null);

    }

    public List<RoomResponse> getRoomByName(String name) {
        var list = roomRepository.findByNameContaining(name);
        return list.stream().map(RoomMapper::toResponse).collect(Collectors.toList());
    }

}
