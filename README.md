# Gerçek Zamanlı Sohbet Uygulaması — Proje Raporu
Tarih: 04.08.2025
## 1) Proje Özeti
Bu proje, Spring Boot tabanlı bir backend ile gerçek zamanlı mesajlaşma sağlayan bir web uygulamasıdır. İstemci tarafı; sade, modern ve karanlık tema odaklı vanilla HTML/JS arayüzle hazırlanmış, WebSocket (SockJS + STOMP) ve REST API'leri üzerinden sunucuya bağlanmaktadır.
Çalışma kapsamında mevcut statik sayfalar yeniden tasarlanmış, tek bir ortak style.css ile görsel tutarlılık sağlanmış ve kullanıcı akışları sadeleştirilmiştir.
## 2) Mimari ve Teknolojiler
### Sunucu (Backend)
• Spring Boot 3 — Web, Data JPA, Security, WebSocket
• Veritabanı — MySQL (JPA ile)
• Gerçek Zamanlı — SockJS/STOMP üzerinden /chat endpoint’i
### İstemci (Frontend)
• Statik HTML + ES6 JavaScript
• Ortak stil dosyası: style.css
## 3) Ekranlar ve Kullanıcı Deneyimi
### 3.1 Giriş Ekranı
<img width="1918" height="870" alt="login page" src="https://github.com/user-attachments/assets/4501e3ed-d67a-4fcc-a56f-e991afeab0bb" />
• Kilit ikonu ve başlık ile sade bir form düzeni
• Modern koyu tema
• Form gönderimi POST /auth/login ile yapılır; yanıt “Giriş başarılı” ise sohbet ekranına yönlendirilir.
### 3.2 Sohbet — Kullanıcı Görünümü
<img width="1918" height="866" alt="Bedirhan sohbet geçmişi" src="https://github.com/user-attachments/assets/34d2c634-769a-4d7b-bc5e-218edff173af" />
<img width="1918" height="862" alt="Resul sohbet geçmişi" src="https://github.com/user-attachments/assets/845dcd43-6b61-4978-9259-f5f1de316efa" />
- Sol panel: Kişi Listesi ve Sohbet Geçmişi
- Üstte alıcı seçimi yapılabilecek alan
- Mesaj balonları gönderen kişiye göre renklendirilir
- Mesaj durumu (SENT/READ) balon içinde görüntülenir
- Gerçek zamanlı iletiler ve anlık okundu güncellemeleri
- Sohbet açıldığında PUT /messages/mark-read/{currentUser}/{receiver} ile okunmamış mesajlar işaretlenir
## 4) Uygulama Akışları
### 1. Kimlik Doğrulama
- Kullanıcı /auth/login ile giriş yapar
- Başarılı girişte ?user= parametresi ile sohbet sayfasına yönlendirilir
### 2. Kişi ve Sohbet Listeleri
- Girişte /users/all/{user} ve /users/conversations/{user} çağrıları ile listeler doldurulur
### 3. Geçmiş ve Okundu
- Sohbet açıldığında /messages/{user}/{other} ile geçmiş yüklenir
- Aynı anda /messages/mark-read/{user}/{other} çalışır
### 4. Gerçek Zamanlı İletiler
- İstemci /chat üzerinden STOMP bağlantısı kurar
- /topic/messages/{user} kanalına abone olur
### 5. Mesaj Gönderme
- /app/chat hedefine STOMP mesajı (JSON) gönderilir
- Karşı taraf için anlık bildirim alınır
## 6) Güvenlik ve Doğrulama
- Spring Security ile HTTP istekleri kontrol altında
- WebSocket kanalı, uygulama içi yetkilendirme akışıyla entegre
- İstemci tarafında hassas veri saklanmaz, yalnızca kullanıcı adı URL parametresi olarak taşınır
