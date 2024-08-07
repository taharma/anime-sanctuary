package com.fls.animecommunity.animesanctuary.controller;

import com.fls.animecommunity.animesanctuary.model.post.Post;
import com.fls.animecommunity.animesanctuary.model.post.dto.BoardRequestsDto;
import com.fls.animecommunity.animesanctuary.model.post.dto.BoardResponseDto;
import com.fls.animecommunity.animesanctuary.model.post.dto.SuccessResponseDto;
import com.fls.animecommunity.animesanctuary.service.BoardService;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boards/{board_id}")
public class PostController {

    //의존성 주입
    private final BoardService boardService;
    
    //전체 목록
    @GetMapping("/api/posts")
    public List<BoardResponseDto> getPosts() {
        return boardService.getPosts();
    }
    
    //선택한 게시글 조회
    @GetMapping("/api/posts/{id}")
    public BoardResponseDto getPost(@PathVariable("id") Long id) {
        return boardService.getPost(id);
    }
    
    //게시글 작성
    @PostMapping("/api/posts")
    public BoardResponseDto createPost(@RequestBody BoardRequestsDto requestsDto) {
        return boardService.createPost(requestsDto);
    }
    //게시글 수정
    @PutMapping("/api/posts/{id}")
    public BoardResponseDto updatePost(@PathVariable("id") Long id, @RequestBody BoardRequestsDto requestsDto) throws Exception {
        return boardService.updatePost(id, requestsDto);
    }
    //게시글 삭제
    @DeleteMapping("/api/posts/{id}")
    public SuccessResponseDto deletePost(@PathVariable("id") Long id, 
    									 @RequestBody BoardRequestsDto requestsDto) throws Exception {
        return boardService.deletePost(id, requestsDto);
    }
}
