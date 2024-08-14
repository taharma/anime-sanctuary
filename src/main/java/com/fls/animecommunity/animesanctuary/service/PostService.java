package com.fls.animecommunity.animesanctuary.service;

import com.fls.animecommunity.animesanctuary.model.post.Post;
import com.fls.animecommunity.animesanctuary.model.post.dto.PostRequestsDto;
import com.fls.animecommunity.animesanctuary.model.post.dto.PostResponseDto;
import com.fls.animecommunity.animesanctuary.model.post.dto.SuccessResponseDto;
import com.fls.animecommunity.animesanctuary.repository.PostRepository;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    //list
    @Transactional(readOnly = true)
    public List<PostResponseDto> getPosts() {
        return postRepository.findAllByOrderByModifiedAtDesc().stream().map(PostResponseDto::new).toList();
    }
    
    //find
    @Transactional
    public PostResponseDto getPost(Long id) {
    	return postRepository.findById(id).map(PostResponseDto::new).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
    }
    
    //write
    @Transactional
    public PostResponseDto createPost(PostRequestsDto requestsDto) {
        Post board = new Post(requestsDto);
        postRepository.save(board);
    	
    	return new PostResponseDto(board);
    }
    
    //update
    @Transactional
    public PostResponseDto updatePost(Long id, PostRequestsDto requestsDto) throws Exception {
        Post board = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        if (!requestsDto.getPassword().equals(board.getPassword()))
            throw new Exception("비밀번호가 일치하지 않습니다.");

        board.update(requestsDto);
        return new PostResponseDto(board);
    }
    
    //delete
    @Transactional
    public SuccessResponseDto deletePost(Long id, PostRequestsDto requestsDto) throws Exception{
    	Post board = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );

        if (!requestsDto.getPassword().equals(board.getPassword()))
            throw new Exception("비밀번호가 일치하지 않습니다.");

        postRepository.deleteById(id);
        return new SuccessResponseDto(true);
    }
}
