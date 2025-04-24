package hr.fer.ppks.chat.service;


import hr.fer.ppks.chat.db.model.Message;
import hr.fer.ppks.chat.db.model.Room;
import hr.fer.ppks.chat.db.model.RoomUser;
import hr.fer.ppks.chat.db.repository.MessageRepository;
import hr.fer.ppks.chat.db.repository.RoomRepository;
import hr.fer.ppks.chat.db.repository.UserRepository;
import hr.fer.ppks.chat.dto.MessageResponse;
import hr.fer.ppks.chat.mapper.MessageMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;


//TODO replace with Optionals to avoid NPE
@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    // private final SimpMessagingTemplate messagingTemplate;


    public List<Message> getMessagesByRoom(UUID roomId) {
        return messageRepository.findByRoomId(roomId);
    }

    public Message getMessageById(UUID messageId) {
        return messageRepository.findById(messageId).orElse(null);
    }


    @Transactional
    public MessageResponse createMessage(UUID roomId, Long userId, String content) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new EntityNotFoundException("Room not found"));
        RoomUser user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Message message = Message.builder()
                .id(UUID.randomUUID())
                .content(content)
                .room(room)
                .user(user)
                .created(Instant.now())
                .build();

        // messagingTemplate.convertAndSend("/topic/rooms/" + roomId, message);

        return MessageMapper.toResponse(messageRepository.save(message));
    }
}
