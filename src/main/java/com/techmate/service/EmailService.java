package com.techmate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendTicketStatusUpdate(String toEmail, String ticketTitle, String newStatus) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("techmate.support100@gmail.com");
            message.setTo(toEmail);
            message.setSubject("TechMate: Your ticket status has been updated");
            message.setText(
                    "Hello,\n\n" +
                            "Your ticket \"" + ticketTitle + "\" has been updated to: " + newStatus.replace("_", " ") + ".\n\n" +
                            "Log in to TechMate to view details or reply.\n\n" +
                            "- TechMate Support"
            );
            mailSender.send(message);
        } catch (Exception e) {
            System.out.println("Failed to send email: " + e.getMessage());
        }
    }
}