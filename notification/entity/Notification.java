package com.cms.cmsapp.notification.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.cms.cmsapp.common.Enums.EmailType;
import com.cms.cmsapp.notification.dto.NotificationDto;
import com.cms.cmsapp.user.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "notifications")
@Builder
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationId;

    private String title;
    private String message;

    @Enumerated(EnumType.STRING)
    private EmailType emailType;

    @Column(columnDefinition = "TINYINT(1) DEFAULT 0")
    @Builder.Default
    private boolean isRead=false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_user_id")
    private User createdByUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User recipient;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    public NotificationDto toDto() {
        return NotificationDto.builder()
                .title(this.title)
                .message(this.message)
                .isRead(this.isRead)
                .createdAt(this.createdAt)
                .build();
    }
}
