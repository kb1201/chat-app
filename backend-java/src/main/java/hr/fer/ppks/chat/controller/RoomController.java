package hr.fer.ppks.chat.controller;

import hr.fer.ppks.chat.db.model.Message;
import hr.fer.ppks.chat.db.model.RoomUser;
import hr.fer.ppks.chat.dto.RoomCreateRequest;
import hr.fer.ppks.chat.dto.RoomResponse;
import hr.fer.ppks.chat.service.MessageService;
import hr.fer.ppks.chat.service.RoomService;
import hr.fer.ppks.chat.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;
    private final MessageService messageService;
    private final UserService userService;

    @PostMapping
    public RoomResponse createRoom(@RequestBody @Valid RoomCreateRequest roomCreateRequest) {
        RoomUser user = (RoomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return roomService.createRoom(roomCreateRequest.getName(), user);
    }

    @GetMapping("/{roomId}")
    public RoomResponse getRoomById(@PathVariable UUID roomId) {
        return roomService.getRoomById(roomId);
    }


    @GetMapping("/name/{name}")
    public List<RoomResponse> getRoomByName(@PathVariable String name) {
        return roomService.getRoomByName(name);
    }


    @GetMapping("/{roomId}/messages")
    public ResponseEntity<?> getMessages(@PathVariable UUID roomId) {
        List<Message> messages = messageService.getMessagesByRoom(roomId);
        return ResponseEntity.ok(messages);
    }


    @GetMapping("/{roomId}/users")
    public ResponseEntity<?> getUsers(@PathVariable UUID roomId) {
        var users = userService.findAllUsersByRoomId(roomId);
        return ResponseEntity.ok(users);
    }

}
