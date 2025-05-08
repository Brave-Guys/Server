package com.kijy.strengthhub.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kijy.strengthhub.dto.PostRequestDto;
import com.kijy.strengthhub.dto.PostResponseDto;
import com.kijy.strengthhub.entity.Post;
import com.kijy.strengthhub.entity.User;
import com.kijy.strengthhub.repository.PostRepository;
import com.kijy.strengthhub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public Post create(PostRequestDto dto) {
        User user = userRepository.findById(dto.getWriterId())
                .orElseThrow(() -> new RuntimeException("사용자 없음"));

        // 이미지 JSON 변환
        String imageUrlsJson = "";
        try {
            imageUrlsJson = new ObjectMapper().writeValueAsString(dto.getImageUrls());
        } catch (JsonProcessingException e) {
            throw new RuntimeException("imageUrls 변환 실패", e);
        }

        // Post 생성
        Post post = Post.builder()
                .writerId(user.getId())
                .name(dto.getName())
                .content(dto.getContent())
                .category(dto.getCategory())
                .imageUrls(imageUrlsJson)
                .createDate(new Date())
                .likes(0)
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

    public Page<Post> getPaginatedPosts(int page, int size, String category, Long writerId) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "createDate"));

        if (category != null && writerId != null) {
            return postRepository.findByCategoryAndWriterId(category, writerId, pageable);
        } else if (category != null) {
            return postRepository.findByCategory(category, pageable);
        } else if (writerId != null) {
            return postRepository.findByWriterId(writerId, pageable);
        } else {
            return postRepository.findAll(pageable);
        }
    }

    public List<PostResponseDto> getTop3PopularPosts() {
        Pageable pageable = PageRequest.of(0, 3);
        List<Post> posts = postRepository.findTop3ByLikeCountDescExcludingNotice(pageable);

        return posts.stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    public PostResponseDto toResponseDto(Post post) {
        ObjectMapper mapper = new ObjectMapper();
        List<String> imageUrls = List.of();

        try {
            if (post.getImageUrls() != null) {
                imageUrls = mapper.readValue(post.getImageUrls(), new TypeReference<>() {});
            }
        } catch (Exception e) {
            // 필요시 로깅
        }

        String nickname = userRepository.findById(post.getWriterId())
                .map(User::getName)
                .orElse("알 수 없음");

        return PostResponseDto.builder()
                .id(post.getId())
                .writerId(post.getWriterId())
                .nickname(nickname)
                .name(post.getName())
                .content(post.getContent())
                .category(post.getCategory())
                .imageUrls(imageUrls)
                .likes(post.getLikes())
                .commentCount(post.getCommentCount())
                .createDate(post.getCreateDate())
                .updatedAt(post.getUpdatedAt())
                .build();
    }

}
