package com.fls.animecommunity.animesanctuary.controller;

import com.fls.animecommunity.animesanctuary.model.post.Post;
import com.fls.animecommunity.animesanctuary.model.post.dto.PostRequestsDto;
import com.fls.animecommunity.animesanctuary.model.post.dto.PostResponseDto;
import com.fls.animecommunity.animesanctuary.model.post.dto.SuccessResponseDto;
import com.fls.animecommunity.animesanctuary.service.PostService;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {

    //IoC
    private final PostService postService;
    
    //create
    @PostMapping("/api/posts")
    public PostResponseDto createPost(@RequestBody PostRequestsDto requestsDto) {
        return postService.createPost(requestsDto);
    }
    
    //list
    @GetMapping("/api/posts")
    public List<PostResponseDto> getPosts() {
        return postService.getPosts();
    }
    
    //find
    @GetMapping("/api/posts/{id}")
    public PostResponseDto getPost(@PathVariable("id") Long id) {
        return postService.getPost(id);
    }
    
    
    //update
    @PutMapping("/api/posts/{id}")
    public PostResponseDto updatePost(@PathVariable("id") Long id, @RequestBody PostRequestsDto requestsDto) throws Exception {
        return postService.updatePost(id, requestsDto);
    }
    
    //delete
    @DeleteMapping("/api/posts/{id}")
    public SuccessResponseDto deletePost(@PathVariable("id") Long id, 
    									 @RequestBody PostRequestsDto requestsDto) throws Exception {
        return postService.deletePost(id, requestsDto);
    }
}
