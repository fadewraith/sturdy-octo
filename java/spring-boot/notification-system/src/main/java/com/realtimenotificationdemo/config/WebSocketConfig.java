package com.realtimenotificationdemo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
//        topic prefix where the msg will be broadcast
//        ex - topic/cricket, /topic/orders
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
//        any name
//        all the urls can access
        registry.addEndpoint("/ws")
                .setAllowedOrigins("http://localhost:63342")
//                .setAllowedOrigins("*")
                .withSockJS(); // for fallback mechanism, coz some old browsers dont support
    }
}
