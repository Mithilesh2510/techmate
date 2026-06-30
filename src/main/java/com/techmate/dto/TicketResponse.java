package com.techmate.dto;

import com.techmate.entity.TicketPriority;
import com.techmate.entity.TicketStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketResponse {
    private Long id;
    private String title;
    private String description;
    private TicketStatus status;
    private TicketPriority priority;
    private String createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}