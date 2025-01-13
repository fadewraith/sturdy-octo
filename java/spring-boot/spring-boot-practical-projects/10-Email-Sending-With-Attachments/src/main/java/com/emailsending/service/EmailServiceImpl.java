package com.emailsending.service;

import com.emailsending.model.EmailRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public Boolean sendEmail(EmailRequest emailRequest) throws MessagingException, UnsupportedEncodingException {

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("dummy@test.com", emailRequest.getTitle());
        helper.setTo(emailRequest.getRecipientEmail());
        helper.setSubject(emailRequest.getSubject());
        helper.setText(emailRequest.getBody(), true);
        javaMailSender.send(message);
        return true;
    }

    @Override
    public void sendEmailWithAttachment(String email, MultipartFile[] files) throws IOException, MessagingException {

        ObjectMapper mapper = new ObjectMapper();
        EmailRequest emailRequest = mapper.readValue(email, EmailRequest.class);

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom("dummy@test.com", emailRequest.getTitle());
        helper.setTo(emailRequest.getRecipientEmail());
        helper.setSubject(emailRequest.getSubject());
        helper.setText(emailRequest.getBody());

        for(MultipartFile file: files) {
            if(!file.isEmpty()) {
                ByteArrayResource byteArrayResource = new ByteArrayResource(file.getBytes());
                helper.addAttachment(file.getOriginalFilename(), byteArrayResource);
            }
        }
        javaMailSender.send(message);
    }
}
