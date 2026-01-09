package com.Springboot.Connection.Controller;

import com.Springboot.Connection.Config.WebSocketBroadcaster;
import com.Springboot.Connection.dto.WebUpdateDTO;
import com.Springboot.Connection.model.Notification;
import com.Springboot.Connection.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Controller
public class WebSocketController {
    private final WebSocketBroadcaster broadcaster;

    public WebSocketController(WebSocketBroadcaster broadcaster) {
        this.broadcaster = broadcaster;
    }
    @Autowired
    SimpMessagingTemplate messagingTemplate;

    @Autowired
    NotificationRepository notificationRepository;

    // Receive message from client
    @MessageMapping("/forms") // client sends to /app/forms
    public void receiveForm(WebUpdateDTO dto) {
        Notification notification = new Notification();
        notification.setAccount(dto.getReference());
        notification.setMessage("Your table is ready!"); // customize message
        notification.setTimestamp(1);
        notification.setCode("TABLE_READY"); // not read yet
        notification.setPayload("HATDOG");
        notificationRepository.save(notification);
        System.out.println("Notification saved to DB for: " + dto.getReference());
        // Optional: broadcast to all clients
        try{
            messagingTemplate.convertAndSend("/topic/account."+dto.getReference(),notification);
            System.out.println("Notification sent via WebSocket to: " + dto.getReference());
        } catch (MessagingException e) {
            System.err.println("Failed to send WebSocket message: " + e.getMessage());
        }
    }
}
