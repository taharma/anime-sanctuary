package com.fls.animecommunity.animesanctuary.service.impl;

import com.fls.animecommunity.animesanctuary.exception.ResourceNotFoundException;
import com.fls.animecommunity.animesanctuary.model.category.Category;
import com.fls.animecommunity.animesanctuary.model.note.Note;
import com.fls.animecommunity.animesanctuary.model.note.dto.NoteRequestsDto;
import com.fls.animecommunity.animesanctuary.model.note.dto.NoteResponseDto;
import com.fls.animecommunity.animesanctuary.model.note.dto.SuccessResponseDto;
import com.fls.animecommunity.animesanctuary.repository.CategoryRepository;
import com.fls.animecommunity.animesanctuary.repository.NoteRepository;
import com.fls.animecommunity.animesanctuary.service.interfaces.NoteService;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class NoteServiceImpl implements NoteService{

    private final NoteRepository noteRepository;
    private final CategoryRepository categoryRepository;

    //list
    @Override
    @Transactional(readOnly = true)
    public List<NoteResponseDto> getNotes() {
    	//    	log.info("getNotes()");
        return noteRepository.findAllByOrderByModifiedAtDesc().stream().map(NoteResponseDto::new).toList();
    }
    
    //find
    @Override
    @Transactional
    public NoteResponseDto getNote(Long id) {
    	//    	log.info("getNote()");
    	//    	log.info("ID: {}", id);
    	
    	return noteRepository.findById(id).map(NoteResponseDto::new).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
    }
    
    //write , create
    @Override
    @Transactional
    public NoteResponseDto createNote(NoteRequestsDto requestsDto) {
    	//    	log.info("createNote()");
    	
    	Category category = categoryRepository.findById(requestsDto.getCategoryId())
    	        .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + requestsDto.getCategoryId()));
    	    
	    Note note = new Note();
	    note.setTitle(requestsDto.getTitle());
	    note.setContents(requestsDto.getContents());
	    note.setCategory(category);
	    
	    Note savedNote = noteRepository.save(note);
	    //      log.info("create success");
    	return new NoteResponseDto(savedNote);
    }
    
    //update
    @Override
    @Transactional
    public NoteResponseDto updateNote(Long id, NoteRequestsDto requestsDto) throws Exception {
    	//    	log.info("updateNote()");
    	//    	log.info("ID: {}", id);
    	
    	Note note = noteRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );

        note.update(requestsDto);
        //        log.info("update success ID: {}", id);
        
        return new NoteResponseDto(note);
    }
    
    //delete
    @Override
    @Transactional
    public SuccessResponseDto deleteNote(Long id, NoteRequestsDto requestsDto) throws Exception{
    	//    	log.info("deleteNote()");
    	//    	log.info("ID: {}", id);
    	
    	Note note = noteRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );

    	noteRepository.deleteById(id);
    	//    	log.info("delete success ID: {}", id);
    	
        return new SuccessResponseDto(true);
    }
}
