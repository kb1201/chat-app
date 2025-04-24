package hr.fer.ppks.chat.db.repository;

import hr.fer.ppks.chat.db.model.RoomUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<RoomUser, Long> {
    RoomUser findByUsername(final String username);

    @Query("""
        SELECT r FROM RoomUser r
        JOIN r.rooms room
        WHERE room.id = :roomId
    """)
    List<RoomUser> findAllByRoomId(@Param("roomId") UUID roomId);
}