package com.pavan.hireflow.notification.serivce;

import com.pavan.hireflow.auth.entity.User;
import com.pavan.hireflow.common.exception.NotificationNotFoundException;
import com.pavan.hireflow.notification.dto.NotificationResponse;
import com.pavan.hireflow.notification.entity.Notification;
import com.pavan.hireflow.notification.repository.NotificationRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Transactional
    public void createNotification(
            User user,
            String title,
            String message
    ){
        Notification notification = new Notification();

        notification.setUser(user);
        notification.setTitle(title);
        notification.setMessage(message);
        notification.setRead(false);
        notification.setCreatedAt(LocalDateTime.now());

        notificationRepository.save(notification);
    }

    public List<NotificationResponse> getNotifications(User user){

         return notificationRepository
                 .findByUserIdOrderByCreatedAtDesc(user.getId())
                 .stream()
                 .map(this::mapToResponse)
                 .toList();
    }

    @Transactional
    public NotificationResponse markAsRead(Long notificationId, User user){

        Notification notification = notificationRepository
                .findByIdAndUserId(notificationId, user.getId())
                .orElseThrow(() -> new NotificationNotFoundException("Notification not found"));

        notification.setRead(true);

        return mapToResponse(notificationRepository.save(notification));
    }

    public long getUnreadCount(User user){
        return notificationRepository
                .countByUserIdAndReadFalse(user.getId());
    }

    private NotificationResponse mapToResponse(Notification notification){
        return new NotificationResponse(
                notification.getId(),
                notification.getTitle(),
                notification.getMessage(),
                notification.isRead(),
                notification.getCreatedAt()
        );
    }
}
