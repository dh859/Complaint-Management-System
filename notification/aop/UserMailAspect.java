package com.cms.cmsapp.notification.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.cms.cmsapp.common.Enums.EmailType;
import com.cms.cmsapp.notification.service.NotificationService;
import com.cms.cmsapp.user.entity.User;

import lombok.RequiredArgsConstructor;

@Aspect
@Component
@RequiredArgsConstructor
public class UserMailAspect {
    private final NotificationService notificationService;

    @AfterReturning(pointcut = "@annotation(sendMail)", returning = "user")
    public void handleMail(JoinPoint joinPoint, SendMail sendMail, User user){
        EmailType type = sendMail.type();
        switch (type) {
            // case PASSWORD_RESET ->
            // notificationService.sendPasswordResetEmail(
            
            // );

            // case ACCOUNT_VERIFICATION ->
            // notificationService.sendAccountVerificationEmail(
            
            // );

            case WELCOME_EMAIL ->
            notificationService.sendWelcomeEmail(
            user
            );

            default -> {

            }
        }
    }

}
