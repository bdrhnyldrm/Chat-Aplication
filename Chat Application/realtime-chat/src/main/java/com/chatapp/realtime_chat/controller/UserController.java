package com.chatapp.realtime_chat.controller;

import com.chatapp.realtime_chat.model.User;
import com.chatapp.realtime_chat.repository.ChatMessageRepository;
import com.chatapp.realtime_chat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    // Tüm kullanıcıları getir (kendisi hariç)
    @GetMapping("/all/{username}")
    public List<String> getAllUsersExceptSelf(@PathVariable String username) {
        return userRepository.findAll().stream()
                .map(User::getUsername)
                .filter(name -> !name.equals(username))
                .collect(Collectors.toList());
    }

    // Bu kullanıcıyla konuşmuş kişileri getir (sohbet listesi)
    @GetMapping("/conversations/{username}")
    public Set<String> getConversationUsers(@PathVariable String username) {
        Set<String> users = new HashSet<>();

        chatMessageRepository.findBySender(username).forEach(msg -> users.add(msg.getReceiver()));
        chatMessageRepository.findByReceiver(username).forEach(msg -> users.add(msg.getSender()));

        users.remove(username); // Kendisini hariç tut
        return users;
    }
}
