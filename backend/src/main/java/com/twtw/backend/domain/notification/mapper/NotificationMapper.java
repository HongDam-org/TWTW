package com.twtw.backend.domain.notification.mapper;

import com.twtw.backend.domain.notification.dto.NotificationRequest;
import com.twtw.backend.domain.notification.entity.Notification;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;

@Mapper(componentModel = ComponentModel.SPRING)
public interface NotificationMapper {

    @Mapping(target = "notificationId", source = "notification.id")
    @Mapping(target = "deviceToken", source = "notification.notificationType")
    @Mapping(target = "title", source = "notification.title")
    @Mapping(target = "body", source = "notification.body")
    @Mapping(target = "id", source = "notification.idInfo")
    NotificationRequest toNotificationRequest(final Notification notification);
}
