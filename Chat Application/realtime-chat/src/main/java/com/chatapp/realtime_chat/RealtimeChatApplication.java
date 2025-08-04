package com.chatapp.realtime_chat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RealtimeChatApplication {

	public static void main(String[] args) {
		SpringApplication.run(RealtimeChatApplication.class, args);
		System.out.println("http://localhost:8080");
	}

}

//Ne zaman mvn clean install yazmamız gerekli?
// 1 - Yeni bağımlılıklar eklendiği zaman (pom.xml dosyasında değişiklik yapıldığında)
// 2 - Proje yapısında değişiklik yapıldığında (örneğin, yeni bir sınıf eklendiğinde veya mevcut sınıflarda değişiklik yapıldığında)
// 3 - Proje yapılandırma dosyalarında (örneğin, application.properties veya application.yml) değişiklik yapıldığında.

