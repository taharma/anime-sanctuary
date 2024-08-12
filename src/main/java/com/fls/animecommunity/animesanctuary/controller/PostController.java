package com.fls.animecommunity.animesanctuary.controller;

import com.fls.animecommunity.animesanctuary.model.post.dto.PostRequestsDto;
import com.fls.animecommunity.animesanctuary.model.post.dto.PostResponseDto;
import com.fls.animecommunity.animesanctuary.model.post.dto.SuccessResponseDto;
import com.fls.animecommunity.animesanctuary.service.PostService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    // Create
    @PostMapping
    public ResponseEntity<?> createPost(@Validated @RequestBody PostRequestsDto requestsDto, 
    									BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }
        PostResponseDto responseDto = postService.createPost(requestsDto);
        return ResponseEntity.ok(responseDto);
    }

    // List
    @GetMapping
    public ResponseEntity<List<PostResponseDto>> getPosts() {
        List<PostResponseDto> posts = postService.getPosts();
        return ResponseEntity.ok(posts);
    }

    // Find
    @GetMapping("/{postId}")
    public ResponseEntity<PostResponseDto> getPost(@PathVariable("postId") Long id) {
        PostResponseDto post = postService.getPost(id);
        if (post == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(post);
    }

    // Update
    @PutMapping("/{postId}")
    public ResponseEntity<?> updatePost(@PathVariable("postId") Long id, 
                                        @Validated @RequestBody PostRequestsDto requestsDto, 
                                        BindingResult result) throws Exception {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }
        PostResponseDto updatedPost = postService.updatePost(id, requestsDto);
        return ResponseEntity.ok(updatedPost);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<SuccessResponseDto> deletePost(@PathVariable("postId") Long id, 
                                                         @RequestBody PostRequestsDto requestsDto) throws Exception {
        // Password 검증 로직 수행
        SuccessResponseDto responseDto = postService.deletePost(id, requestsDto);
        return ResponseEntity.ok(responseDto);
    }




}
