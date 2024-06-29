package site.thanhtungle.tourservice.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import site.thanhtungle.tourservice.model.dto.request.notification.NotificationMessageDTO;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    @Value("${kafka.topic.notification-topic}")
    private String notificationTopic;

    public void sendNotification(NotificationMessageDTO notificationMessageDTO) {
        Message<NotificationMessageDTO> message = MessageBuilder
                .withPayload(notificationMessageDTO)
                .setHeader(KafkaHeaders.TOPIC, notificationTopic)
                .build();
        kafkaTemplate.send(message);
    }
}
