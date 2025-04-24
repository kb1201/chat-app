package hr.fer.ppks.chat.controller;


import hr.fer.ppks.chat.dto.LoginResponse;
import hr.fer.ppks.chat.dto.RoomId;
import hr.fer.ppks.chat.dto.UserRequest;
import hr.fer.ppks.chat.dto.UserResponse;
import hr.fer.ppks.chat.service.AuthService;
import hr.fer.ppks.chat.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

//TODO extract services to interfaces
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;
    private final AuthService authService;


    @PostMapping()
    public ResponseEntity<UserResponse> createUser(final @Valid @RequestBody UserRequest rq) {
        return new ResponseEntity<>(this.service.create(rq), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody UserRequest loginUserDto) {
        var loginResponse = authService.login(loginUserDto.getUsername(), loginUserDto.getPassword());
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/{userId}/rooms")
    public ResponseEntity<?> addMember(@PathVariable Long userId, @RequestBody RoomId roomId) {
        var dto = service.addUserToRoom(userId, roomId.getRoomId());
        if (dto == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Could not add member to room"));
        }

        return ResponseEntity.ok(dto);
    }


    @DeleteMapping("/{userId}/rooms/{roomId}")
    public ResponseEntity<?> removeMember(@PathVariable UUID roomId, @PathVariable Long userId) {
        boolean removed = service.removeRoom(userId, roomId);
        if (!removed) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Could not remove member from room"));
        }
        return ResponseEntity.noContent().build();
    }
}