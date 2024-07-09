package site.thanhtungle.notificationservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Value("${ws.storm.endpoint}")
    private String notificationEndpoint;

    @Value("${ws.prefix}")
    private String brokerPrefix;

    @Value("${ws.application.destination.prefix}")
    private String applicationDestinationPrefix;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint(notificationEndpoint).setAllowedOriginPatterns("*");
        registry.addEndpoint(notificationEndpoint).setAllowedOriginPatterns("*").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker(brokerPrefix);
        registry.setApplicationDestinationPrefixes(applicationDestinationPrefix);
    }
}
