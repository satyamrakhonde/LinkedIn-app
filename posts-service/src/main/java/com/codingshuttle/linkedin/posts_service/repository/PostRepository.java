package com.codingshuttle.linkedin.posts_service.repository;

import com.codingshuttle.linkedin.posts_service.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
