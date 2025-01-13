package com.emailsending.service;

import com.emailsending.model.EmailRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.mail.MessagingException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public interface EmailService {

    public Boolean sendEmail(EmailRequest emailRequest) throws MessagingException, UnsupportedEncodingException;

    public void sendEmailWithAttachment(String email, MultipartFile[] file) throws IOException, MessagingException;
}
