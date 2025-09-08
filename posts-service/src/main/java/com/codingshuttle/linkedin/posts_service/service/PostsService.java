package com.codingshuttle.linkedin.posts_service.service;

import com.codingshuttle.linkedin.posts_service.auth.UserContextHolder;
import com.codingshuttle.linkedin.posts_service.clients.ConnectionsClient;
import com.codingshuttle.linkedin.posts_service.dto.PersonDto;
import com.codingshuttle.linkedin.posts_service.dto.PostCreateRequestDto;
import com.codingshuttle.linkedin.posts_service.dto.PostDto;
import com.codingshuttle.linkedin.posts_service.entity.Post;
import com.codingshuttle.linkedin.posts_service.exception.ResourceNotFoundException;
import com.codingshuttle.linkedin.posts_service.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostsService {

    private final PostRepository postsRepository;
    private final ModelMapper modelMapper;
    private final ConnectionsClient connectionsClient;

    public PostDto createPost(PostCreateRequestDto postDto, Long userId) {
        Post post = modelMapper.map(postDto, Post.class);
        post.setUserId(userId);

        Post savedPost = postsRepository.save(post);
        return modelMapper.map(savedPost, PostDto.class);
    }

    public PostDto getPostById(Long postId) {
        log.debug("Retrieving post with ID: {}", postId);

        Long userId = UserContextHolder.getCurrentUserId();
        List<PersonDto> firstConnections = connectionsClient.getFirstConnections(userId);

        Post post = postsRepository.findById(postId).orElseThrow(() ->
            new ResourceNotFoundException("Post not found with id:" +postId));

        return modelMapper.map(post , PostDto.class);
    }

    public List<PostDto> getAllPostsOfuser(Long userId) {
        List<Post> posts = postsRepository.findByUserId(userId);

        return posts.stream()
                .map((element) -> modelMapper.map(element, PostDto.class))
                .collect(Collectors.toList());
    }
}
