package al.library.revenuetracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }


        public void sendSimpleMessage(String recipientEmail, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("xbejleri@gmail.com");
        message.setTo(recipientEmail);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }
}
