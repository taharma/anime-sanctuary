package com.fls.animecommunity.animesanctuary.service;

import com.fls.animecommunity.animesanctuary.model.note.Note;
import com.fls.animecommunity.animesanctuary.model.note.dto.NoteRequestsDto;
import com.fls.animecommunity.animesanctuary.model.note.dto.NoteResponseDto;
import com.fls.animecommunity.animesanctuary.model.note.dto.SuccessResponseDto;
import com.fls.animecommunity.animesanctuary.repository.NoteRepository;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService{

    private final NoteRepository noteRepository;

    //list
    @Override
    @Transactional(readOnly = true)
    public List<NoteResponseDto> getNotes() {
        return noteRepository.findAllByOrderByModifiedAtDesc().stream().map(NoteResponseDto::new).toList();
    }
    
    //find
    @Override
    @Transactional
    public NoteResponseDto getNote(Long id) {
    	return noteRepository.findById(id).map(NoteResponseDto::new).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
    }
    
    //write
    @Override
    @Transactional
    public NoteResponseDto createNote(NoteRequestsDto requestsDto) {
        Note board = new Note(requestsDto);
        noteRepository.save(board);
    	
    	return new NoteResponseDto(board);
    }
    
    //update
    @Override
    @Transactional
    public NoteResponseDto updateNote(Long id, NoteRequestsDto requestsDto) throws Exception {
        Note board = noteRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );

        board.update(requestsDto);
        return new NoteResponseDto(board);
    }
    
    //delete
    @Override
    @Transactional
    public SuccessResponseDto deleteNote(Long id, NoteRequestsDto requestsDto) throws Exception{
    	Note board = noteRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );

    	noteRepository.deleteById(id);
        return new SuccessResponseDto(true);
    }
}
