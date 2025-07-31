package com.chatapp.realtime_chat.controller;

import com.chatapp.realtime_chat.model.User;
import com.chatapp.realtime_chat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.register(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        User found = userService.login(user.getUsername(), user.getPassword());
        return found != null ? "Giriş başarılı" : "Geçersiz kullanıcı adı veya şifre";
    }
}
