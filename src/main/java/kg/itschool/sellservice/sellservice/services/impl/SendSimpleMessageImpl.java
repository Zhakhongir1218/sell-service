package kg.itschool.sellservice.sellservice.services.impl;

import kg.itschool.sellservice.sellservice.services.SendSimpleMessage;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SendSimpleMessageImpl implements SendSimpleMessage {
    @Autowired
    private  JavaMailSender javaMailSender;

    @Override
    public void sendSimpleMessage(String to, String text) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("zhakhongir1218@gmail.com");
        message.setTo(to);
        message.setSubject("Код подтверждения");
        message.setText("Ваш код подтверждения: " + text);
        javaMailSender.send(message);

    }
}
