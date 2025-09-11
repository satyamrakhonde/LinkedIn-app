package com.codingshuttle.linkedin.notification_service.notification_service.consumer;

import com.codingshuttle.linkedin.notification_service.notification_service.clients.ConnectionsClient;
import com.codingshuttle.linkedin.notification_service.notification_service.dto.PersonDto;
import com.codingshuttle.linkedin.notification_service.notification_service.entity.Notification;
import com.codingshuttle.linkedin.notification_service.notification_service.repository.NotificationRepository;
import com.codingshuttle.linkedin.notification_service.posts_service.event.PostCreatedEvent;
import com.codingshuttle.linkedin.notification_service.posts_service.event.PostLikedEvent;
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
    private final NotificationRepository notificationRepository;

    @KafkaListener(topics = "post-created-topic")
    public void handlePostCreated(PostCreatedEvent postCreatedEvent) {

        log.info("SEnding notifications: handlePostCreated: {}", postCreatedEvent);
        List<PersonDto> connections = connectionsClient.getFirstConnections(postCreatedEvent.getCreatorId());

        for(PersonDto connection : connections) {
            sendNotification(connection.getUserId(), "connection"+postCreatedEvent.getCreatorId()+" has " +
                    "created a post, Check it out");
        }
    }

    @KafkaListener(topics = "post-liked-topic")
    public void handlePostLiked(PostLikedEvent postLikedEvent) {
        log.info("SEnding notification: handlePostliked: {}", postLikedEvent);
        String message = String.format("Your post, %d has been liked by %d", postLikedEvent.getPostId(),
                postLikedEvent.getLikedByUserId());

        sendNotification(postLikedEvent.getCreatorId(), message);
    }

    public void sendNotification(Long userId, String message) {
        Notification notification = new Notification();
        notification.setMessage(message);
        notification.setUserId(userId);

        notificationRepository.save(notification);
    }
}
