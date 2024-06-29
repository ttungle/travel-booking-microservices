package site.thanhtungle.notificationservice.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import site.thanhtungle.notificationservice.model.dto.request.NotificationMessageDTO;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationConSummer {

    private final Environment environment;

    @KafkaListener(
            topics = "${kafka.topic.notification-topic}",
            groupId = "${spring.kafka.consumer.group-id}",
            properties = {"spring.json.value.default.type=site.thanhtungle.notificationservice.model.dto.request.NotificationMessageDTO"}
    )
    public void listenNotification(@Payload NotificationMessageDTO notificationMessageDTO) {
        log.info("KAFKA-NOTIFICATION - Received kafka message for notification with groupId: {}, topic: {}, message: {}.",
                environment.getProperty("spring.kafka.consumer.group-id"),
                environment.getProperty("kafka.topic.notification-topic"),
                notificationMessageDTO.toString());
    }
}
