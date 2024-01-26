package kz.iitu.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class SMTPService {

    private final JavaMailSender mailSender;
    @Value("${spring.mail.from}")
    private String fromEmail;

    public void sendMail(String email, String head, String msg) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        try {
            mailMessage.setFrom(fromEmail);
            mailMessage.setTo(email);
            mailMessage.setSubject(head);
            mailMessage.setText(msg);
            mailSender.send(mailMessage);
        } catch (MailException e) {
            e.printStackTrace();
        }
    }
}
