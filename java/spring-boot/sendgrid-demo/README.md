# Sending Emails in Java with SendGrid

A lightweight Java implementation for sending emails using SendGrid's API. This service offers superior deliverability, 
analytics, and a modern REST API compared to traditional JavaMail.

## Features

- Simple email sending with SendGrid's Java SDK
- Support for plain text emails
- Response tracking and status monitoring
- Exception handling and logging

## Requirements

- Java 17+
- SendGrid API Key
- Maven/Gradle for dependency management

## Dependencies

Add the following dependency to your project:

```xml
<dependency>
    <groupId>com.sendgrid</groupId>
    <artifactId>sendgrid-java</artifactId>
    <version>4.9.3</version>
</dependency>
```

## Getting Started

1. Set your SendGrid API key in `EmailService.java`
2. Configure sender email address
3. Build the project

## Usage

### Basic Email Sending

```java
EmailService emailService = new EmailService();

String emailBody = """
    Hello, from SendGrid!
    
    This is a multiline email message.
    
    Thank You,
    Your Name
    """;

emailService.sendEmail(
    "recipient@example.com",
    "Email Subject",
    emailBody
);
```

### Response Handling

The service provides detailed response information:

```java
// Response output example
Status Code: 202
Response Headers: {server=nginx...}
```

## Implementation Details

The `EmailService` class handles all email operations:

```java
public void sendEmail(String to, String subject, String content) throws IOException {
    Email from = new Email("sender@example.com");
    Email toEmail = new Email(to);
    Content emailContent = new Content("text/plain", content);
    Mail mail = new Mail(from, subject, toEmail, emailContent);
    
    SendGrid sg = new SendGrid(SENDGRID_API_KEY);
    Request request = new Request();
    // ... request configuration and sending
}
```

## Error Handling

The service includes robust error handling:

```java
try {
    emailService.sendEmail(to, subject, content);
} catch (IOException e) {
    // Handle email sending failure
    e.printStackTrace();
}
```

## Security Note

⚠️ Always store your SendGrid API key securely using environment variables or a secure configuration management system.

## Resources

- [SendGrid API Documentation](https://docs.sendgrid.com/api-reference/mail-send/mail-send)
- [SendGrid Java SDK](https://github.com/sendgrid/sendgrid-java)