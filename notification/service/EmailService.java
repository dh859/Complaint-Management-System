package com.cms.cmsapp.notification.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.cms.cmsapp.common.exceptions.InvalidOperationException;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
   
    @Autowired
    private JavaMailSender mailSender;

    @Async
    public void sendHtmlEmail(String to, String subject, String htmlBody) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlBody, true);

            mailSender.send(message);
            System.out.println("Email sent to " + to);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InvalidOperationException("Error sending email");
            
        }
    }

}
