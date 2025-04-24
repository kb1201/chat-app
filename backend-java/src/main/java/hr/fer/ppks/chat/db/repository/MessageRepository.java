package hr.fer.ppks.chat.db.repository;

import hr.fer.ppks.chat.db.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MessageRepository extends JpaRepository<Message, UUID> {
    List<Message> findByRoomId(UUID roomId);
}
