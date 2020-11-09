package com.inft.awm.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
/**
 * Sending email service
 *
 * @author Yao Shi
 * @version 1.0
 * @date 21/10/2020 08:22 pm
 */
@Service
public class MailServiceImp {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    public void sendSimpleMail(String to, String subject, String content) {
        //create SimpleMailMessage
        SimpleMailMessage message = new SimpleMailMessage();
        //sender
        message.setFrom(from);
        //receiver
        message.setTo(to);
        //subject
        message.setSubject(subject);
        //content
        message.setText(content);
        //send
        mailSender.send(message);
    }
}
