package com.fls.animecommunity.animesanctuary.service;

import com.fls.animecommunity.animesanctuary.model.Board;
import com.fls.animecommunity.animesanctuary.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BoardService {

    @Autowired
    private BoardRepository postRepository;

    //Read post list
    public List<Board> getAllPosts() {
        return postRepository.findAll();
    }
    
    //Read post 하나 찾기
    public Optional<Board> getPostById(Long id) {
        return postRepository.findById(id);
    }
    
    //create 
    public Board createPost(Board post) {
        return postRepository.save(post);
    }
    
    //update
    public Board updatePost(Long id, Board postDetails) {
        Board post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));
        post.setTitle(postDetails.getTitle());
        post.setContents(postDetails.getContents());
        return postRepository.save(post);
    }
    
    //delete
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}
