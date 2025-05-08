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
    public ResponseEntity<List<Post>> getAll() {
        return ResponseEntity.ok(postService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getById(@PathVariable Long id) {
        return ResponseEntity.ok(postService.findById(id));
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
            @RequestParam(required = false) String userId
    ) {
        int size = 10; // 페이지당 10개
        Page<Post> postsPage = postService.getPaginatedPosts(page, size, category, userId);

        List<PostResponseDto> postDtos = postsPage.stream()
                .map(postService::toResponseDto)
                .toList();

        return ResponseEntity.ok(Map.of(
                "posts", postDtos,
                "total", postsPage.getTotalElements()
        ));
    }

}
