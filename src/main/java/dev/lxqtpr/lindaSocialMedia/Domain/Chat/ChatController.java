package dev.lxqtpr.lindaSocialMedia.Domain.Chat;

import dev.lxqtpr.lindaSocialMedia.Domain.Message.MessageEntity;
import dev.lxqtpr.lindaSocialMedia.Domain.Message.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatController {
    private final ChatRepository chatRepository;
    private final MessageRepository messageRepository;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat")
    public void processMessage(@Payload String message){

        messagingTemplate.convertAndSendToUser(
                "1",
                "/queue/messages",
                message
        );
    }
}
