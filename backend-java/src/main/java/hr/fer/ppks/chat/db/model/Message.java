package hr.fer.ppks.chat.db.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "messages")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Message {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Instant created;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private RoomUser user;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;
}
