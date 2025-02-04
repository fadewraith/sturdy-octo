package com.sendgriddemo;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        var emailService = new EmailService();
        var body = """
                Hello, from SendGrid!
                
                I am testing out the Java SDK for SendGrid and this is a multiline string in Java.
                
                Thank You,
                SendGrid Demo
                """;

        try {
            emailService.sendEmail(
                    "test1@test.com",
                    "Sendgrid Demo",
                    body
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}