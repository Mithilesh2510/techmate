package com.techmate.service;

import com.techmate.dto.TicketRequest;
import com.techmate.dto.TicketResponse;
import com.techmate.entity.Ticket;
import com.techmate.entity.TicketStatus;
import com.techmate.entity.User;
import com.techmate.repository.TicketRepository;
import com.techmate.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;

    public TicketResponse createTicket(TicketRequest request) {
        User user = getCurrentUser();

        Ticket ticket = Ticket.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .priority(request.getPriority())
                .status(TicketStatus.OPEN)
                .createdBy(user)
                .build();

        Ticket saved = ticketRepository.save(ticket);

        try {
            emailService.sendTicketCreatedConfirmation(user.getEmail(), saved.getTitle());
        } catch (Exception e) {
            System.err.println("Email failed: " + e.getMessage());
        }

        return mapToResponse(saved);
    }

    public List<TicketResponse> getMyTickets() {
        User user = getCurrentUser();
        return ticketRepository.findByCreatedBy(user)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<TicketResponse> getAllTickets() {
        return ticketRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public TicketResponse getTicketById(Long id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));
        return mapToResponse(ticket);
    }

    public TicketResponse updateTicketStatus(Long id, TicketStatus status) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));
        ticket.setStatus(status);
        Ticket updated = ticketRepository.save(ticket);

        try {
            emailService.sendTicketStatusUpdate(
                    ticket.getCreatedBy().getEmail(),
                    ticket.getTitle(),
                    status.name()
            );
        } catch (Exception e) {
            System.err.println("Email failed: " + e.getMessage());
        }

        return mapToResponse(updated);
    }

    public void deleteTicket(Long id) {
        ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));
        ticketRepository.deleteById(id);
    }

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    private TicketResponse mapToResponse(Ticket ticket) {
        return new TicketResponse(
                ticket.getId(),
                ticket.getTitle(),
                ticket.getDescription(),
                ticket.getStatus(),
                ticket.getPriority(),
                ticket.getCreatedBy().getEmail(),
                ticket.getCreatedAt(),
                ticket.getUpdatedAt()
        );
    }
}