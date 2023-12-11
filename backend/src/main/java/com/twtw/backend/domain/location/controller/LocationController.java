package com.twtw.backend.domain.location.controller;

import com.twtw.backend.domain.location.dto.request.LocationRequest;
import com.twtw.backend.domain.location.service.LocationService;

import lombok.RequiredArgsConstructor;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class LocationController {

    private static final String EXCHANGE_NAME = "map";
    private static final String ROUTING_KEY = "plan.";
    private final RabbitTemplate rabbitTemplate;
    private final LocationService locationService;

    @MessageMapping("map.share.{planId}")
    public void share(
            @DestinationVariable final UUID planId,
            @Payload final LocationRequest locationRequest) {
        rabbitTemplate.convertAndSend(
                EXCHANGE_NAME, ROUTING_KEY + planId, locationService.addInfo(planId, locationRequest));
    }
}
