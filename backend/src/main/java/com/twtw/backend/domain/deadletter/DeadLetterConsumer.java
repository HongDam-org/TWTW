package com.twtw.backend.domain.deadletter;

import com.twtw.backend.domain.notification.dto.NotificationRequest;

import lombok.extern.slf4j.Slf4j;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Component
public class DeadLetterConsumer {

    private static final String ALERT_URL = "https://hooks.slack.com/services/";
    private static final String ALERT_MESSAGE = "{\"text\": \"Dead letter received: %s\"}";
    private final WebClient webClient;
    private final String slackUrl;

    public DeadLetterConsumer(@Value("${slack.url}") final String slackUrl) {
        this.webClient = WebClient.create(ALERT_URL);
        this.slackUrl = slackUrl;
    }

    @RabbitListener(queues = "deadletter.queue")
    public void handleDeadLetterMessage(final NotificationRequest message) {
        log.error("Dead letter received: {}", message);
        webClient
                .post()
                .uri(slackUrl)
                .bodyValue(String.format(ALERT_MESSAGE, message))
                .retrieve()
                .onStatus(HttpStatusCode::isError, ClientResponse::createException);
    }
}
