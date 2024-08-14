package com.fls.animecommunity.animesanctuary.service;

import com.fls.animecommunity.animesanctuary.model.note.Note;
import com.fls.animecommunity.animesanctuary.model.note.dto.NoteRequestsDto;
import com.fls.animecommunity.animesanctuary.model.note.dto.NoteResponseDto;
import com.fls.animecommunity.animesanctuary.model.note.dto.SuccessResponseDto;
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
public class NoteService {

    private final PostRepository postRepository;

    //list
    @Transactional(readOnly = true)
    public List<NoteResponseDto> getPosts() {
        return postRepository.findAllByOrderByModifiedAtDesc().stream().map(NoteResponseDto::new).toList();
    }
    
    //find
    @Transactional
    public NoteResponseDto getPost(Long id) {
    	return postRepository.findById(id).map(NoteResponseDto::new).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
    }
    
    //write
    @Transactional
    public NoteResponseDto createPost(NoteRequestsDto requestsDto) {
        Note board = new Note(requestsDto);
        postRepository.save(board);
    	
    	return new NoteResponseDto(board);
    }
    
    //update
    @Transactional
    public NoteResponseDto updatePost(Long id, NoteRequestsDto requestsDto) throws Exception {
        Note board = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        if (!requestsDto.getPassword().equals(board.getPassword()))
            throw new Exception("비밀번호가 일치하지 않습니다.");

        board.update(requestsDto);
        return new NoteResponseDto(board);
    }
    
    //delete
    @Transactional
    public SuccessResponseDto deletePost(Long id, NoteRequestsDto requestsDto) throws Exception{
    	Note board = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );

        if (!requestsDto.getPassword().equals(board.getPassword()))
            throw new Exception("비밀번호가 일치하지 않습니다.");

        postRepository.deleteById(id);
        return new SuccessResponseDto(true);
    }
}
