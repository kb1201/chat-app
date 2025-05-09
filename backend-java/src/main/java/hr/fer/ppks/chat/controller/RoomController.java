package hr.fer.ppks.chat.controller;

import hr.fer.ppks.chat.db.model.Message;
import hr.fer.ppks.chat.db.model.RoomUser;
import hr.fer.ppks.chat.dto.RoomCreateRequest;
import hr.fer.ppks.chat.dto.RoomResponse;
import hr.fer.ppks.chat.service.MessageService;
import hr.fer.ppks.chat.service.RoomService;
import hr.fer.ppks.chat.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Operation(summary = "Create a new room", responses = {
            @ApiResponse(responseCode = "200", description = "Room created successfully")
    })
    @PostMapping
    public RoomResponse createRoom(@RequestBody @Valid RoomCreateRequest roomCreateRequest) {
        RoomUser user = (RoomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return roomService.createRoom(roomCreateRequest.getName(), user);
    }

    @Operation(summary = "Get a room by its ID", responses = {
            @ApiResponse(responseCode = "200", description = "Room found"),
            @ApiResponse(responseCode = "404", description = "Room not found")
    })
    @GetMapping("/{roomId}")
    public RoomResponse getRoomById(@PathVariable UUID roomId) {
        return roomService.getRoomById(roomId);
    }


    @Operation(summary = "Search rooms by name", responses = {
            @ApiResponse(responseCode = "200", description = "Rooms retrieved successfully")
    })
    @GetMapping("/name/{name}")
    public List<RoomResponse> getRoomByName(@PathVariable String name) {
        return roomService.getRoomByName(name);
    }


    @Operation(summary = "Get messages for a room", responses = {
            @ApiResponse(responseCode = "200", description = "Messages retrieved successfully")
    })
    @GetMapping("/{roomId}/messages")
    public ResponseEntity<?> getMessages(@PathVariable UUID roomId) {
        List<Message> messages = messageService.getMessagesByRoom(roomId);
        return ResponseEntity.ok(messages);
    }


    @Operation(summary = "Get users in a room", responses = {
            @ApiResponse(responseCode = "200", description = "Users retrieved successfully")
    })
    @GetMapping("/{roomId}/users")
    public ResponseEntity<?> getUsers(@PathVariable UUID roomId) {
        var users = userService.findAllUsersByRoomId(roomId);
        return ResponseEntity.ok(users);
    }

}
