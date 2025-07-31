package com.chatapp.realtime_chat.controller;

import com.chatapp.realtime_chat.model.ChatMessage;
import com.chatapp.realtime_chat.repository.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Controller // Bu sınıfın bir Spring Controller olduğunu belirtir.
public class ChatController {

    @Autowired // Spring tarafından SimpMessagingTemplate bean'inin otomatik olarak bağlanmasını sağlar.
    private SimpMessagingTemplate messagingTemplate; // Mesajları belirli bir hedefe göndermek için kullanılır.

    @Autowired // Spring tarafından ChatMessageRepository bean'inin otomatik olarak bağlanmasını sağlar.
    private ChatMessageRepository chatMessageRepository; // Mesajları veritabanına kaydetmek için kullanılır.

    @MessageMapping("/chat") // Gelen mesajları /app/chat endpoint'inde dinler.
    public void sendMessage(@Payload ChatMessage message) {
        message.setTimestamp(LocalDateTime.now()); // Mesajın gönderildiği zamanı ayarlar.
        message.setStatus("SENT"); // Mesajın durumunu "SENT" olarak ayarlar.

        chatMessageRepository.save(message); // Mesajı veritabanına kaydeder.

        // Gönderilen mesajı, alıcının dinlediği kanala iletir.
        messagingTemplate.convertAndSend(
                "/topic/messages/" + message.getReceiver(), // Alıcının dinlediği kanal.
                message // Gönderilecek mesaj.
        );
    }
}
