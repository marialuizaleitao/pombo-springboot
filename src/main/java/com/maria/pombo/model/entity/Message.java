package com.maria.pombo.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
@Data
public class Message implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotBlank(message = "Text is required")
    @Size(min = 1, max = 300, message = "Text must be between 1 and 300 characters")
    @Column(name = "text", length = 300, nullable = false)
    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "total_likes", nullable = false)
    private int totalLikes = 0;

    @Column(name = "total_reports", nullable = false)
    private int totalReports = 0;

    @Column(name = "is_blocked", nullable = false)
    private boolean isBlocked = false;
}
