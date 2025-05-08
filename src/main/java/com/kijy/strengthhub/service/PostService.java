package com.kijy.strengthhub.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kijy.strengthhub.dto.PostRequestDto;
import com.kijy.strengthhub.dto.PostResponseDto;
import com.kijy.strengthhub.entity.Post;
import com.kijy.strengthhub.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    public Page<Post> getPaginatedPosts(int page, int size, String category, String userId) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "createDate"));

        if (category != null && userId != null) {
            return postRepository.findByCategoryAndWriterId(category, userId, pageable);
        } else if (category != null) {
            return postRepository.findByCategory(category, pageable);
        } else if (userId != null) {
            return postRepository.findByWriterId(userId, pageable);
        } else {
            return postRepository.findAll(pageable);
        }
    }

    public PostResponseDto toResponseDto(Post post) {
        ObjectMapper mapper = new ObjectMapper();
        List<String> imageUrls = List.of();

        try {
            if (post.getImageUrls() != null) {
                imageUrls = mapper.readValue(post.getImageUrls(), new TypeReference<>() {});
            }
        } catch (Exception e) {
            // 로깅만 해도 됨
        }

        return PostResponseDto.builder()
                .id(post.getId())
                .writerId(post.getWriterId())
                .name(post.getName())
                .content(post.getContent())
                .category(post.getCategory())
                .imageUrls(imageUrls)
                .likeCount(post.getLikeCount())
                .commentCount(post.getCommentCount())
                .nickname("닉네임 자리") // 추후 유저 조인 시 갱신
                .build();
    }
}
