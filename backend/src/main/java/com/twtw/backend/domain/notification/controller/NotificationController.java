package com.twtw.backend.domain.notification.controller;

import com.twtw.backend.domain.notification.messagequeue.FcmProducer;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("notifications")
public class NotificationController {

    private final FcmProducer fcmProducer;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Void> sendNotification(@RequestBody final List<UUID> ids) {
        fcmProducer.sendFailedNotification(ids);
        return ResponseEntity.noContent().build();
    }
}
