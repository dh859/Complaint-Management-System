package com.cms.cmsapp.notification.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.cms.cmsapp.common.exceptions.AccessDeniedException;
import com.cms.cmsapp.common.exceptions.ResourceNotFoundException;
import com.cms.cmsapp.complaint.entity.Complaint;
import com.cms.cmsapp.notification.dto.CreateNotificationDto;
import com.cms.cmsapp.notification.dto.NotificationDto;
import com.cms.cmsapp.notification.dto.SendMailDto;
import com.cms.cmsapp.notification.entity.Notification;
import com.cms.cmsapp.notification.repository.NotificationRepo;
import com.cms.cmsapp.user.entity.User;
import com.cms.cmsapp.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationService {
    
    private final NotificationRepo notificationRepository;
    private final EmailService emailService;
    private final TemplateEngine templateEngine;
    private final UserService userService;

    /* ---------------- BROADCAST ---------------- */

    public void broadcastNotification(SendMailDto dto) {
        for (String email : dto.getTo()) {
            sendSimpleNotification(email, dto);
        }
    }

    public void sendNotificationToUser(SendMailDto dto) {
        sendSimpleNotification(dto.getTo().get(0), dto);
    }

    public void sendNotification(CreateNotificationDto dto, User sender) {
        for (Long recipientId : dto.getRecipientUserId()) {
            User recipient = userService.getById(recipientId);
            Notification notification = Notification.builder()
                    .title(dto.getTitle())
                    .message(dto.getMessage())
                    .recipient(recipient)
                    .createdByUser(sender)
                    .build();
            notificationRepository.save(notification);

            if(dto.isSendEmail()) {
                SendMailDto mailDto = SendMailDto.builder()
                        .to(List.of(recipient.getEmail()))
                        .subject(dto.getTitle())
                        .message(dto.getMessage())
                        .build();
                sendSimpleNotification(recipient.getEmail(), mailDto);
            }
        }
    }

    public List<NotificationDto> getUserNotifications(Long userId) {
        User user = userService.getById(userId);
        return notificationRepository.findByRecipient(user).orElseThrow(() -> new ResourceNotFoundException("No notifications found for user"))
                .stream()
                .map(Notification::toDto)
                .toList();
    }

    public List<NotificationDto> getUnreadNotifications(Long userId) {
        User user = userService.getById(userId);
        return notificationRepository.findByRecipientAndIsReadFalse(user).orElseThrow(() -> new ResourceNotFoundException("No unread notifications found for user"))
                .stream()
                .map(Notification::toDto)
                .toList();
    }

    public void markAsRead(Long notificationId, Long userId) {
        Notification notification = notificationRepository.findByNotificationId(notificationId)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found"));
        if (!notification.getRecipient().getUserId().equals(userId)) {
            throw new AccessDeniedException("Unauthorized");
        }
        notification.setRead(true);
        notificationRepository.save(notification);
    }

    public void markAllAsRead(Long userId) {
        User user = userService.getById(userId);
        List<Notification> notifications = notificationRepository.findByRecipientAndIsReadFalse(user).orElseThrow(() -> new ResourceNotFoundException("No unread notifications found for user"));
        notifications.forEach(n -> n.setRead(true));
        notificationRepository.saveAll(notifications);
    }

    public void deleteNotification(Long notificationId, Long userId) {
        Notification notification = notificationRepository.findByNotificationId(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
        if (!notification.getRecipient().getUserId().equals(userId)) {
            throw new AccessDeniedException("Unauthorized to delete notification");
        }
        notificationRepository.delete(notification);
    }


    /* ---------------- SIMPLE NOTIFICATION ---------------- */

    private void sendSimpleNotification(String email, SendMailDto dto) {

        Context context = new Context();
        context.setVariable("title", dto.getTitle());
        context.setVariable("message", dto.getMessage());

        String html = templateEngine.process("simple-notification", context);

        emailService.sendHtmlEmail(
                email,
                dto.getSubject(),
                html);
    }

    /* ---------------- COMPLAINT STATUS ---------------- */

    public void sendComplaintStatusUpdate(Complaint complaint) {

        Context context = new Context();
        context.setVariable("userName", complaint.getRaisedByUser().getFullname());
        context.setVariable("complaintId", complaint.getComplaintId());
        context.setVariable("status", complaint.getStatus().name());

        String html = templateEngine.process("complaint-status", context);

        emailService.sendHtmlEmail(
                complaint.getRaisedByUser().getEmail(),
                "Complaint Status Updated",
                html);
    }

    /* ---------------- COMPLAINT SUBMISSION ---------------- */

    public void sendComplaintSubmissionEmail(Complaint complaint) {

        Context context = new Context();
        context.setVariable("userName", complaint.getRaisedByUser().getFullname());
        context.setVariable("complaintId", complaint.getComplaintId());
        context.setVariable("category", complaint.getCategory().name());

        String html = templateEngine.process("complaint-submitted", context);

        emailService.sendHtmlEmail(
                complaint.getRaisedByUser().getEmail(),
                "Complaint Submitted Successfully",
                html);
    }

    /* ---------------- COMPLAINT ASSIGNMENT ---------------- */

    public void sendComplaintAssignmentEmail(Complaint complaint) {

        Context context = new Context();
        context.setVariable("agentName", complaint.getAssignedToUser().getFullname());
        context.setVariable("complaintId", complaint.getComplaintId());
        context.setVariable("category", complaint.getCategory().name());
        context.setVariable("priority", complaint.getPriority().name());
        context.setVariable("dashboardUrl", "https://cms.app/dashboard");

        String html = templateEngine.process("assign-complaint", context);

        emailService.sendHtmlEmail(
                complaint.getAssignedToUser().getEmail(),
                "New Complaint Assigned",
                html);
    }

    /* ---------------- FORWARD TO DEPARTMENT ---------------- */

    public void sendForwardToDepartmentEmail(Complaint complaint) {

        Context context = new Context();
        context.setVariable("complaintId", complaint.getComplaintId());
        context.setVariable("fromDepartment",
                complaint.getAssignedToUser().getFullname());

        String html = templateEngine.process("forward-to-department", context);

        emailService.sendHtmlEmail(
                complaint.getForwardedToDepartment().getContactEmail(),
                "Complaint Forwarded to Department",
                html);
    }

    /* ---------------- WELCOME EMAIL ---------------- */

    public void sendWelcomeEmail(User user) {

        Context context = new Context();
        context.setVariable("userName", user.getFullname());
        context.setVariable("loginUrl", "https://cms.app/login");

        String html = templateEngine.process("welcome-email", context);

        emailService.sendHtmlEmail(
                user.getEmail(),
                "Welcome to CMS",
                html);
    }

}
