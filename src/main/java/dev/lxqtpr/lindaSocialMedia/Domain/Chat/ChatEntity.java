package dev.lxqtpr.lindaSocialMedia.Domain.Chat;

import dev.lxqtpr.lindaSocialMedia.Domain.User.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class ChatEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;

    String description;

    @ManyToMany(mappedBy = "rooms")

    Set<UserEntity> members;

    String avatar;
}
