package dev.lxqtpr.lindaSocialMedia.Domain.Message;

import dev.lxqtpr.lindaSocialMedia.Domain.Chat.ChatEntity;
import dev.lxqtpr.lindaSocialMedia.Domain.User.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Getter
@Setter
public class MessageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @OneToOne
    @JoinColumn(name = "chat_id")
    ChatEntity chat;

    @OneToOne
    @JoinColumn(name = "sender_id")
    UserEntity sender;

    Long recipientId;

    String text;

    @ElementCollection
    Set<String> files;

    LocalTime timestamp;

    @Enumerated(EnumType.STRING)
    MessageStatus status;
}