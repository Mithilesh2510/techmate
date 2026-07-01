package com.techmate.controller;

import com.techmate.dto.CommentRequest;
import com.techmate.dto.CommentResponse;
import com.techmate.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{ticketId}/comments")
    public ResponseEntity<CommentResponse> addComment(
            @PathVariable Long ticketId,
            @Valid @RequestBody CommentRequest request) {
        return ResponseEntity.ok(commentService.addComment(ticketId, request));
    }

    @GetMapping("/{ticketId}/comments")
    public ResponseEntity<List<CommentResponse>> getComments(
            @PathVariable Long ticketId) {
        return ResponseEntity.ok(commentService.getComments(ticketId));
    }
}