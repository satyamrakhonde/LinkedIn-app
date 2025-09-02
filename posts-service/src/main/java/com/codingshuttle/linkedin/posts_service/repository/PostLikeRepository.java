package com.codingshuttle.linkedin.posts_service.repository;

import com.codingshuttle.linkedin.posts_service.entity.PostLike;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    @Transactional
    void deleteByUserIdAndPostId(Long userId, Long postId);

    boolean existsByUserIdAndPostId(Long userId, Long postId);
}
