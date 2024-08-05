package com.fls.animecommunity.animesanctuary.controller;

import com.fls.animecommunity.animesanctuary.model.Board;
import com.fls.animecommunity.animesanctuary.service.BoardService;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class BoardController {

    
    private final BoardService boardService;

    @GetMapping
    public List<Board> getAllPosts() {
        return boardService.getAllPosts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Board> getPostById(@PathVariable Long id) {
        Board post = boardService.getPostById(id).orElseThrow(() -> new RuntimeException("Post not found"));
        return ResponseEntity.ok(post);
    }

    @PostMapping
    public Board createPost(@RequestBody Board post) {
        return boardService.createPost(post);
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
