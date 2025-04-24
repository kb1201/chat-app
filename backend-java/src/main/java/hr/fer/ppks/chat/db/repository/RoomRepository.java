package hr.fer.ppks.chat.db.repository;

import hr.fer.ppks.chat.db.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RoomRepository extends JpaRepository<Room, UUID> {
    @Query("SELECT r FROM Room r WHERE r.name LIKE %:name%")
    List<Room> findByNameContaining(@Param("name") String name);

}
