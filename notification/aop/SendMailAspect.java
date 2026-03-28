package com.cms.cmsapp.notification.aop;

import com.cms.cmsapp.common.Enums.EmailType;
import com.cms.cmsapp.complaint.entity.Complaint;
import com.cms.cmsapp.notification.service.NotificationService;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class SendMailAspect {

    private final NotificationService notificationService;

    @AfterReturning(pointcut = "@annotation(sendMail)", returning = "complaint")
    public void handleMail(SendMail sendMail, Complaint complaint) {

        EmailType type = sendMail.type();

        if (complaint == null)
            return;

        switch (type) {

            case STATUS_UPDATE ->
                notificationService.sendComplaintStatusUpdate(
                        complaint);

            case COMPLAINT_SUBMISSION ->
                notificationService.sendComplaintSubmissionEmail(
                        complaint);

            case COMPLAINT_ASSIGNMENT ->
                notificationService.sendComplaintAssignmentEmail(
                        complaint);

            case FORWARD_TO_DEPARTMENT ->
                notificationService.sendForwardToDepartmentEmail(
                        complaint);

            default -> {

            }
        }
    }
}
