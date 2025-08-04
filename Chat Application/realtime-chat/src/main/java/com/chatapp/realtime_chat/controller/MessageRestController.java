package com.chatapp.realtime_chat.controller;

import com.chatapp.realtime_chat.model.ChatMessage;
import com.chatapp.realtime_chat.repository.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageRestController {

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate; // WebSocket status mesajı gönderenin kanalına tekrar göndermek için

    @GetMapping("/{user1}/{user2}")
    public List<ChatMessage> getChatHistory(@PathVariable String user1, @PathVariable String user2) {
        List<ChatMessage> sent = chatMessageRepository.findBySenderAndReceiver(user1, user2);
        List<ChatMessage> received = chatMessageRepository.findBySenderAndReceiver(user2, user1);

        sent.addAll(received);
        sent.sort(Comparator.comparing(ChatMessage::getTimestamp));

        return sent;
    }

    @PutMapping("/{id}/read")
    public ResponseEntity<String> markAsRead(@PathVariable Long id) {
        return chatMessageRepository.findById(id)
                .map(msg -> {
                    msg.setStatus("READ");
                    chatMessageRepository.save(msg);

                    // Gönderen kullanıcıya güncellenmiş mesajı gönder (WebSocket ile)
                    messagingTemplate.convertAndSend(
                            "/topic/messages/" + msg.getSender(),
                            msg
                    );

                    return ResponseEntity.ok("Mesaj okundu olarak işaretlendi");
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/mark-read/{receiver}/{sender}")
    public ResponseEntity<String> markConversationAsRead(
            @PathVariable String receiver,
            @PathVariable String sender
    ) {
        List<ChatMessage> unreadMessages = chatMessageRepository
                .findBySenderAndReceiverAndStatus(sender, receiver, "SENT");

        for (ChatMessage msg : unreadMessages) {
            msg.setStatus("READ");
            chatMessageRepository.save(msg);

            // Gönderene güncel mesajı geri gönder
            messagingTemplate.convertAndSend(
                    "/topic/messages/" + sender,
                    msg
            );
        }

        return ResponseEntity.ok("Seçilen konuşmadaki okunmamış mesajlar güncellendi");
    }





}
