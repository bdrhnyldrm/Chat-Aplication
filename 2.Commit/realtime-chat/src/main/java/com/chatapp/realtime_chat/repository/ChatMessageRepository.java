package com.chatapp.realtime_chat.repository;

import com.chatapp.realtime_chat.model.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    // JpaRepository, ChatMessage varlığı için temel CRUD işlemlerini sağlar.

    List<ChatMessage> findBySenderAndReceiver(String sender, String receiver);
    // Belirli bir gönderici ve alıcı arasındaki mesajları döndürür.

    List<ChatMessage> findByReceiverAndSender(String receiver, String sender);
    // Belirli bir alıcı ve gönderici arasındaki mesajları döndürür (ters yönlü sorgu).
}
