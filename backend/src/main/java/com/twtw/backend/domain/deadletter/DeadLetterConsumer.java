package com.twtw.backend.domain.deadletter;

import com.twtw.backend.domain.notification.dto.NotificationRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Component
public class DeadLetterConsumer {

    private final WebClient webClient;
    private final String slackUrl;

    public DeadLetterConsumer(@Value("${slack.url}") final String slackUrl) {
        this.webClient = WebClient.create("https://hooks.slack.com/services/");
        this.slackUrl = slackUrl;
    }

    @RabbitListener(queues = "deadletter.queue")
    public void handleDeadLetterMessage(final NotificationRequest message) {
        log.error("Dead letter received: {}", message);
        webClient
                .post()
                .uri(slackUrl)
                .bodyValue("{\"text\": \"Dead letter received: " + message + "\"}")
                .retrieve()
                .bodyToMono(String.class)
                .doOnSuccess(s -> log.info("Slack message sent: {}", s))
                .subscribe();
    }
}
