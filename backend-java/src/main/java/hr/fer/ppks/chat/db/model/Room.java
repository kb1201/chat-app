package hr.fer.ppks.chat.db.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "rooms")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Room {

    @Id
    private UUID id;

    @Column(nullable = false, unique = true)
    private String name;

}
