package dev.lxqtpr.lindaSocialMedia.Core.WebSockets;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatMessage {

    private MessageType type;
    private String content;
    // todo: разве тут не юзер должен быть?
    private String sender;

}
