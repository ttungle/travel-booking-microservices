package site.thanhtungle.reviewservice.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import site.thanhtungle.commons.model.dto.NotificationMessageDTO;

@Service
@RequiredArgsConstructor
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
