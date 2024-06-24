package site.thanhtungle.notificationservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;
import site.thanhtungle.notificationservice.model.dto.NotificationResponseDTO;
import site.thanhtungle.notificationservice.service.NotificationService;

@RestController
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @SendTo("/topic/all")
    public void notificationToAll() {

    }

    @SendTo("/topic/users/{id}")
    public void notificationToUser(@DestinationVariable("id") String userId) {
        simpMessagingTemplate.convertAndSend("/topic/users/" + userId,
                new NotificationResponseDTO("Notification", "This is a notification", "24/06/2024"));
    }
}
