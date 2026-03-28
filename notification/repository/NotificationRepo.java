package com.cms.cmsapp.notification.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cms.cmsapp.notification.entity.Notification;
import com.cms.cmsapp.user.entity.User;

import java.util.List;
import java.util.Optional;


@Repository
public interface NotificationRepo extends JpaRepository<Notification,Long>{
    
    public Optional<Notification> findByNotificationId(Long notificationId);

    public Optional<List<Notification>> findByCreatedByUserAndRecipient(User createdByUser, User recipient);

    public Optional<List<Notification>> findByCreatedByUserAndRecipientAndIsReadFalse(User createdByUser, User recipient);

    public Optional<List<Notification>> findByCreatedByUserAndIsReadFalse(User createdByUser);

    public Optional<List<Notification>> findByRecipientAndNotificationId(User user, Long notificationId);

    public Optional<List<Notification>> findByRecipient(User user);

    public Optional<List<Notification>> findByRecipientAndIsReadFalse(User user);

    public void deleteByNotificationId(Long notificationId);

    public Optional<List<Notification>> findByCreatedByUser(User user);

}
