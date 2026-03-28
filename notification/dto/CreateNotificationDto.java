package com.cms.cmsapp.notification.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreateNotificationDto {
    private String title;
    private String message;
    private List<Long> recipientUserId;
    private boolean sendEmail;
}
