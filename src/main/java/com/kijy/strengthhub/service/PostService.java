package com.kijy.strengthhub.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kijy.strengthhub.dto.PostRequestDto;
import com.kijy.strengthhub.entity.Post;
import com.kijy.strengthhub.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public Post create(PostRequestDto dto) {
        ObjectMapper objectMapper = new ObjectMapper();
        String imageUrlsJson = null;

        try {
            imageUrlsJson = objectMapper.writeValueAsString(dto.getImageUrls()); // List → JSON 문자열
        } catch (JsonProcessingException e) {
            throw new RuntimeException("imageUrls 변환 실패", e);
        }

        Post post = Post.builder()
                .writerId(dto.getWriterId())
                .name(dto.getName())
                .content(dto.getContent())
                .category(dto.getCategory())
                .imageUrls(imageUrlsJson)
                .createDate(new Date())
                .likeCount(0)
                .commentCount(0)
                .build();

        return postRepository.save(post);
    }

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public Post findById(Long id) {
        return postRepository.findById(id).orElseThrow();
    }

    public void delete(Long id) {
        postRepository.deleteById(id);
    }

    public Post update(Long id, PostRequestDto dto) {
        Post post = postRepository.findById(id).orElseThrow();

        ObjectMapper objectMapper = new ObjectMapper();
        String imageUrlsJson = null;
        try {
            imageUrlsJson = objectMapper.writeValueAsString(dto.getImageUrls());
        } catch (JsonProcessingException e) {
            throw new RuntimeException("imageUrls 변환 실패", e);
        }

        post.setName(dto.getName());
        post.setContent(dto.getContent());
        post.setCategory(dto.getCategory());
        post.setImageUrls(imageUrlsJson);
        post.setUpdatedAt(new Date());

        return postRepository.save(post);
    }
}
