package net.ddns.cloudtecnologia.chat;

import net.ddns.cloudtecnologia.chat.client.Chrome;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ChatWhatsappSpringOpenaiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChatWhatsappSpringOpenaiApplication.class, args);
    }


    @Bean(name = "iniciarChat")
    public CommandLineRunner iniciarChat() {

        return args -> {
            Chrome chrome = new Chrome();
            //

            chrome.confiugurarChrome();
            chrome.logarWhatsApp();
            chrome.processarMsg();


        };
    }


}
