package com.chatapp.realtime_chat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration // Bu sınıfın bir Spring yapılandırma sınıfı olduğunu belirtir.
@EnableWebSocketMessageBroker // WebSocket mesajlaşma desteğini etkinleştirir.
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic"); // Mesajların dinleneceği kanalın ön ekini tanımlar.
        config.setApplicationDestinationPrefixes("/app"); // Mesajların gönderileceği kanalın ön ekini tanımlar.
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/chat") // WebSocket bağlantısı için kullanılacak endpoint'i tanımlar.
                .setAllowedOriginPatterns("*") // CORS yapılandırması: Tüm kaynaklara izin verir.
                .withSockJS(); // SockJS desteği ekler, WebSocket desteklenmeyen tarayıcılar için yedek çözüm.
    }
}
