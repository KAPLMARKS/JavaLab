package ru.itis.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

@Component
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Value("marmeladkamarat@gmail.com")
    private String userName;

    @Override
    public void sendMail(String subject, String text, String email) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            try {
                MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
                messageHelper.setFrom(userName);
                messageHelper.setTo(email);
                messageHelper.setSubject(subject);
                messageHelper.setText(text, true);
            } catch (javax.mail.MessagingException e) {
                throw new IllegalArgumentException(e);
            }
        };
        emailSender.send(messagePreparator);
    }
}