package com.codingshuttle.linkedin.notification_service.posts_service.event;

import lombok.Builder;
import lombok.Data;

@Data
public class PostCreatedEvent {
    Long creatorId;
    String content;
    Long postId;
}
