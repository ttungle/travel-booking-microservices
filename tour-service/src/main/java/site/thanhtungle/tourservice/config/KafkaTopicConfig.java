package site.thanhtungle.tourservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Value("${kafka.topic.notification-topic}")
    private String notificationTopic;

    @Bean
    public NewTopic createNotificationTopic() {
        return TopicBuilder.name(notificationTopic).build();
    }
}
