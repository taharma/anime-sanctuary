package com.fls.animecommunity.animesanctuary.controller;

import com.fls.animecommunity.animesanctuary.model.board.Board;
import com.fls.animecommunity.animesanctuary.model.board.dto.BoardRequestsDto;
import com.fls.animecommunity.animesanctuary.model.board.dto.BoardResponseDto;
import com.fls.animecommunity.animesanctuary.service.BoardService;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

@RequiredArgsConstructor
public class BoardController {

    
    private final BoardService boardService;
    
    //전체 목록을 가져온다.
    @GetMapping("/api/posts")
    public List<BoardResponseDto> getPosts() {
        return boardService.getPosts();
    }
    
    //선택한 게시글 조회
    @GetMapping("/api/post/{id}")
    public BoardResponseDto getPost(@PathVariable Long id) {
        return boardService.getPost(id);
    }
    
    //게시글작성
    @PostMapping("/api/post")
    public BoardResponseDto createPost(@RequestBody BoardRequestsDto requestsDto) {
        return boardService.createPost(requestsDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Board> updatePost(@PathVariable Long id, @RequestBody Board postDetails) {
        Board updatedPost = boardService.updatePost(id, postDetails);
        return ResponseEntity.ok(updatedPost);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
    	boardService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
}
