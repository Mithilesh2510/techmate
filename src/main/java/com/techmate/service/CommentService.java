package com.techmate.service;

import com.techmate.dto.CommentRequest;
import com.techmate.dto.CommentResponse;
import com.techmate.entity.Comment;
import com.techmate.entity.Ticket;
import com.techmate.entity.User;
import com.techmate.repository.CommentRepository;
import com.techmate.repository.TicketRepository;
import com.techmate.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    public CommentResponse addComment(Long ticketId, CommentRequest request) {
        User user = getCurrentUser();
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        Comment comment = Comment.builder()
                .message(request.getMessage())
                .ticket(ticket)
                .author(user)
                .build();

        Comment saved = commentRepository.save(comment);
        return mapToResponse(saved);
    }

    public List<CommentResponse> getComments(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));
        return commentRepository.findByTicketOrderByCreatedAtAsc(ticket)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    private CommentResponse mapToResponse(Comment comment) {
        return new CommentResponse(
                comment.getId(),
                comment.getMessage(),
                comment.getAuthor().getEmail(),
                comment.getAuthor().getName(),
                comment.getAuthor().getRole().name(),
                comment.getCreatedAt()
        );
    }
}