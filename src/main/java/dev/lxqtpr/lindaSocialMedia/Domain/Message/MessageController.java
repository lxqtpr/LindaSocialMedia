package dev.lxqtpr.lindaSocialMedia.Domain.Message;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class MessageController {

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public String greeting() throws Exception {
        return "efqwq";
    }
}
