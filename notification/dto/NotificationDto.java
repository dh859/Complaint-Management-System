package com.cms.cmsapp.notification.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class NotificationDto {
    private String title;
    private String message;
    private boolean isRead;
    private LocalDateTime createdAt;
    private String sender;
    private String reciever;
}
