package com.cms.cmsapp.notification.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cms.cmsapp.application.Security.Utils.UserDetailsDto;
import com.cms.cmsapp.notification.dto.CreateNotificationDto;
import com.cms.cmsapp.notification.dto.NotificationDto;
import com.cms.cmsapp.notification.service.NotificationService;
import com.cms.cmsapp.user.entity.User;
import com.cms.cmsapp.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;
    private final UserService userService;

    @PostMapping("/send")
    public void sendNotification(@AuthenticationPrincipal UserDetailsDto sender,@RequestBody CreateNotificationDto dto) {
        User senderUser = userService.getById(sender.getUserId());
        notificationService.sendNotification(dto, senderUser);
    }


    @GetMapping
    public List<NotificationDto> getMyNotifications(
            @AuthenticationPrincipal User user
    ) {
        return notificationService.getUserNotifications(user.getUserId());
    }

    @GetMapping("/unread")
    public List<NotificationDto> getUnreadNotifications(
            @AuthenticationPrincipal User user
    ) {
        return notificationService.getUnreadNotifications(user.getUserId());
    }

    @PutMapping("/{id}/read")
    public void markAsRead(
            @PathVariable Long id,
            @AuthenticationPrincipal User user
    ) {
        notificationService.markAsRead(id, user.getUserId());
    }


    @PutMapping("/read-all")
    public void markAllAsRead(
            @AuthenticationPrincipal User user
    ) {
        notificationService.markAllAsRead(user.getUserId());
    }

    @DeleteMapping("/{id}")
    public void deleteNotification(
            @PathVariable Long id,
            @AuthenticationPrincipal User user
    ) {
        notificationService.deleteNotification(id, user.getUserId());
    }
}
