package com.techmate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponse {
    private Long id;
    private String message;
    private String authorEmail;
    private String authorName;
    private String authorRole;
    private LocalDateTime createdAt;
}