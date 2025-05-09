package hr.fer.ppks.chat.controller;


import hr.fer.ppks.chat.db.model.RoomUser;
import hr.fer.ppks.chat.dto.MessageResponse;
import hr.fer.ppks.chat.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;


    @MessageMapping("/rooms/{roomId}/messages")
    @SendTo("/topic/room.{roomId}")
    public MessageResponse sendMessage(@Payload String content, @DestinationVariable UUID roomId, Principal principal) {
        var user = (RoomUser) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();

        return messageService.createMessage(roomId, user.getId(), content);
    }

}
