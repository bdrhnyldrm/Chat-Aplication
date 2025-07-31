package com.chatapp.realtime_chat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration // Bu sınıfın bir Spring yapılandırma sınıfı olduğunu belirtir.
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Şifreleme için BCrypt algoritmasını kullanan bir PasswordEncoder bean'i tanımlar.
        return new BCryptPasswordEncoder(); // Şifreleri güvenli bir şekilde hashlemek için kullanılır.
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Uygulamanın güvenlik yapılandırmasını tanımlar.
        http
                .csrf(csrf -> csrf.disable()) // CSRF korumasını devre dışı bırakır (örnek amaçlı, üretimde dikkatli olunmalıdır).
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // Tüm HTTP isteklerine izin verir (herkese açık erişim).
                );

        return http.build(); // Güvenlik yapılandırmasını oluşturur ve döndürür.
    }
}
