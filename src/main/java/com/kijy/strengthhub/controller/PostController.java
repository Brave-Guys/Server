package com.kijy.strengthhub.controller;

import com.kijy.strengthhub.dto.PostRequestDto;
import com.kijy.strengthhub.dto.PostResponseDto;
import com.kijy.strengthhub.entity.Post;
import com.kijy.strengthhub.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<Post> create(@RequestBody PostRequestDto dto) {
        return ResponseEntity.ok(postService.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<PostResponseDto>> getAll() {
        List<PostResponseDto> result = postService.findAll().stream()
                .map(postService::toResponseDto)
                .toList();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> getById(@PathVariable Long id) {
        Post post = postService.findById(id);
        PostResponseDto dto = postService.toResponseDto(post); // 닉네임 포함된 DTO 생성
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Post> update(@PathVariable Long id, @RequestBody PostRequestDto dto) {
        return ResponseEntity.ok(postService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        postService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/paginated/{page}")
    public ResponseEntity<?> getPaginatedPosts(
            @PathVariable int page,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Long writerId
    ) {
        int size = 15;
        Page<Post> postsPage = postService.getPaginatedPosts(page, size, category, writerId);

        List<PostResponseDto> postDtos = postsPage.stream()
                .map(postService::toResponseDto)
                .toList();

        return ResponseEntity.ok(Map.of(
                "posts", postDtos,
                "total", postsPage.getTotalElements()
        ));
    }

    @GetMapping("/popular")
    public ResponseEntity<List<PostResponseDto>> getPopularPosts() {
        List<PostResponseDto> popularPosts = postService.getTop3PopularPosts();
        return ResponseEntity.ok(popularPosts);
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchPosts(@RequestParam("q") String query) {
        if (query == null || query.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("message", "검색어가 필요합니다."));
        }

        List<PostResponseDto> results = postService.searchPosts(query);
        return ResponseEntity.ok(results);
    }

}
