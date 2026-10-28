package com.pavan.hireflow.notification.controller;

import com.pavan.hireflow.auth.entity.User;
import com.pavan.hireflow.notification.dto.NotificationResponse;
import com.pavan.hireflow.notification.serivce.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
@PreAuthorize("isAuthenticated()")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping
    public ResponseEntity<List<NotificationResponse>> getNotifications(
            @AuthenticationPrincipal User user
    ){

        return ResponseEntity.ok(notificationService.getNotifications(user));
    }

    @PatchMapping("/{notificationId}/read")
    public ResponseEntity<NotificationResponse> markAsRead(
            @AuthenticationPrincipal User user,
            @PathVariable Long notificationId
    ){
        return  ResponseEntity.ok(notificationService.markAsRead(notificationId, user));
    }

    @GetMapping("/unread-count")
    public ResponseEntity<Long> getUnreadCount(
            @AuthenticationPrincipal User user
    ){
        return ResponseEntity.ok(notificationService.getUnreadCount(user));
    }
}
