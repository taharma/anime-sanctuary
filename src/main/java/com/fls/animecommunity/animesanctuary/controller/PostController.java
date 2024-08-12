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
    @GetMapping("/api/posts/{postId}")
    public PostResponseDto getPost(@PathVariable("postId") Long id) {
        return postService.getPost(id);
    }
    
    
    //update
    @PutMapping("/api/posts/{postId}")
    public PostResponseDto updatePost(@PathVariable("postId") Long id, @RequestBody PostRequestsDto requestsDto) throws Exception {
        return postService.updatePost(id, requestsDto);
    }
    
    //delete
    @DeleteMapping("/api/posts/{postId}")
    public SuccessResponseDto deletePost(@PathVariable("postId") Long id, 
    									 @RequestBody PostRequestsDto requestsDto) throws Exception {
        return postService.deletePost(id, requestsDto);
    }
}
