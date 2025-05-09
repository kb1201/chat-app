package hr.fer.ppks.chat.controller;


import hr.fer.ppks.chat.dto.LoginResponse;
import hr.fer.ppks.chat.dto.RoomId;
import hr.fer.ppks.chat.dto.UserRequest;
import hr.fer.ppks.chat.dto.UserResponse;
import hr.fer.ppks.chat.service.AuthService;
import hr.fer.ppks.chat.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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


    @Operation(summary = "Create a new user", responses = {
            @ApiResponse(responseCode = "201", description = "User created successfully")
    })
    @PostMapping()
    public ResponseEntity<UserResponse> createUser(final @Valid @RequestBody UserRequest rq) {
        return new ResponseEntity<>(this.service.create(rq), HttpStatus.CREATED);
    }

    @Operation(summary = "Authenticate user and return JWT token", responses = {
            @ApiResponse(responseCode = "200", description = "Authenticated successfully"),
            @ApiResponse(responseCode = "401", description = "Invalid credentials")
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody UserRequest loginUserDto) {
        var loginResponse = authService.login(loginUserDto.getUsername(), loginUserDto.getPassword());
        return ResponseEntity.ok(loginResponse);
    }


    @Operation(summary = "Add a user to a room", responses = {
            @ApiResponse(responseCode = "200", description = "User added to room"),
            @ApiResponse(responseCode = "500", description = "Failed to add user to room")
    })
    @PostMapping("/{userId}/rooms")
    public ResponseEntity<?> addMember(@PathVariable Long userId, @RequestBody RoomId roomId) {
        var dto = service.addUserToRoom(userId, roomId.getRoomId());
        if (dto == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Could not add member to room"));
        }

        return ResponseEntity.ok(dto);
    }


    @Operation(summary = "Remove a user from a room", responses = {
            @ApiResponse(responseCode = "204", description = "User removed from room"),
            @ApiResponse(responseCode = "500", description = "Failed to remove user from room")
    })
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