package com.emailsending.controller;

import com.emailsending.model.EmailRequest;
import com.emailsending.service.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

@RestController
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/send-email")
    private ResponseEntity<?> sendEmail(@RequestBody EmailRequest emailRequest) {
        try {
            emailService.sendEmail(emailRequest);
            return new ResponseEntity<>("Email sending success", HttpStatus.OK);
        } catch (MessagingException | UnsupportedEncodingException e) {
            return new ResponseEntity<>("Email send failed !Internal Server Error" + e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/email-with-attachment")
    public ResponseEntity<?> sendEmailWithAttachment(@RequestParam String email, @RequestParam(required = false) MultipartFile[] file) {
//        send multiple files use array for Multipart[]
        try {
            emailService.sendEmailWithAttachment(email, file);
            return new ResponseEntity<>("Email sending success", HttpStatus.OK);
        } catch (MessagingException | IOException e) {
            return new ResponseEntity<>("Email send failed !Internal Server Error" + e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
