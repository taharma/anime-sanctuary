package com.fls.animecommunity.animesanctuary.service;

import com.fls.animecommunity.animesanctuary.model.board.Board;
import com.fls.animecommunity.animesanctuary.model.board.dto.BoardRequestsDto;
import com.fls.animecommunity.animesanctuary.model.board.dto.BoardResponseDto;
import com.fls.animecommunity.animesanctuary.repository.BoardRepository;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    //list
    @Transactional(readOnly = true)
    public List<BoardResponseDto> getPosts() {
        return boardRepository.findAllByOrderByModifiedAtDesc().stream().map(BoardResponseDto::new).toList();
    }
    
    //게시글의 id를 가진 데이터를 boardRepository에서 찾아서 BoardResponseDto 객체로 만들어 반환한다.
    public BoardResponseDto getPost(Long id) {
    	return boardRepository.findById(id).map(BoardResponseDto::new).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
    }
    
    //write
    @Transactional
    public BoardResponseDto createPost(BoardRequestsDto requestsDto) {
        Board board = new Board(requestsDto);
        boardRepository.save(board);
    	
    	return new BoardResponseDto(board);
    }
    
    //update
    public Board updatePost(Long id, Board postDetails) {
        Board post = boardRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));
        post.setTitle(postDetails.getTitle());
        post.setContents(postDetails.getContents());
        return boardRepository.save(post);
    }
    
    //delete
    public void deletePost(Long id) {
    	boardRepository.deleteById(id);
    }
}
