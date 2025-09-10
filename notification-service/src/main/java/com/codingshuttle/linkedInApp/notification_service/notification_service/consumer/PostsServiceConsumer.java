package com.codingshuttle.linkedInApp.notification_service.notification_service.consumer;

import com.codingshuttle.linkedInApp.notification_service.notification_service.clients.ConnectionsClient;
import com.codingshuttle.linkedInApp.notification_service.notification_service.dto.PersonDto;
import com.codingshuttle.linkedInApp.notification_service.posts_service.event.PostCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostsServiceConsumer {

    private final ConnectionsClient connectionsClient;

    @KafkaListener(topics = "post-created-topic")
    public void handlePostCreated(PostCreatedEvent postCreatedEvent) {
        List<PersonDto> connections = connectionsClient.getFirstConnections();
    }
}
