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
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("techmate.support100@gmail.com");
        message.setTo(toEmail);
        message.setSubject("TechMate — Ticket Status Updated");
        message.setText(
                "Hello,\n\n" +
                        "Your ticket \"" + ticketTitle + "\" has been updated.\n\n" +
                        "New Status: " + newStatus.replace("_", " ") + "\n\n" +
                        "Log in to TechMate to view details and add comments:\n" +
                        "https://admirable-gumdrop-405170.netlify.app/login.html\n\n" +
                        "— TechMate Support Team"
        );
        mailSender.send(message);
    }

    public void sendTicketCreatedConfirmation(String toEmail, String ticketTitle) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("techmate.support100@gmail.com");
        message.setTo(toEmail);
        message.setSubject("TechMate — Ticket Submitted Successfully");
        message.setText(
                "Hello,\n\n" +
                        "Your IT support ticket has been submitted successfully.\n\n" +
                        "Ticket: \"" + ticketTitle + "\"\n" +
                        "Status: OPEN\n\n" +
                        "Our team will review it shortly. You can track progress here:\n" +
                        "https://admirable-gumdrop-405170.netlify.app/login.html\n\n" +
                        "— TechMate Support Team"
        );
        mailSender.send(message);
    }
}