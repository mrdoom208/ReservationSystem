package com.Springboot.Connection.Config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class WebSocketBroadcaster {

    private static SimpMessagingTemplate template;

    @Autowired
    public WebSocketBroadcaster(SimpMessagingTemplate template) {
        WebSocketBroadcaster.template = template;
    }
    public void broadcast(String destination, Object message) {
        template.convertAndSend(destination, message);
    }
    //@param account   The username or unique account ID
    //@param destination The destination relative to /user, e.g., "/queue/forms"
    //@param message   The message payload (can be your DTO)
    public void sendToUser(String account, String destination, Object message) {
        template.convertAndSendToUser(account, destination, message);
    }


}
