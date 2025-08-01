package com.chatapp.realtime_chat.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity // Bu sınıfın bir JPA varlık sınıfı olduğunu belirtir.
@Data // Lombok tarafından getter, setter, toString, equals ve hashCode metodlarını otomatik oluşturur.
@NoArgsConstructor // Parametresiz bir yapıcı metod oluşturur.
@AllArgsConstructor // Tüm alanları içeren bir yapıcı metod oluşturur.
@Builder // Builder tasarım desenini kullanarak nesne oluşturmayı sağlar.
@Table(name = "messages") // Bu varlığın veritabanında "messages" tablosuna karşılık geldiğini belirtir.
public class ChatMessage {

    @Id // Bu alanın birincil anahtar olduğunu belirtir.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Birincil anahtarın otomatik olarak artırılacağını belirtir.
    private Long id; // Mesajın benzersiz kimliği.

    private String sender; // Mesajı gönderen kişinin kullanıcı adı veya kimliği.
    private String receiver; // Mesajın alıcısının kullanıcı adı veya kimliği.
    private String content; // Mesajın içeriği.

    private LocalDateTime timestamp; // Mesajın gönderildiği tarih ve saat.

    private String status; // Mesajın durumu (örneğin: SENT, DELIVERED, READ).
}
