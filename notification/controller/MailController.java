package com.cms.cmsapp.notification.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cms.cmsapp.notification.dto.SendMailDto;
import com.cms.cmsapp.notification.service.NotificationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/mail")
public class MailController {

    private final NotificationService notificationService;

    public MailController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("/notify")
    public ResponseEntity<?> notify(@Valid @RequestBody SendMailDto dto) {
        notificationService.broadcastNotification(dto);
        return ResponseEntity.ok("Notification sent");
    }

}