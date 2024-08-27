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
import java.util.ArrayList;
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
    	List<Note> notes = noteRepository.findAllByOrderByModifiedAtDesc();
        List<NoteResponseDto> noteResponseDtos = new ArrayList<>();

        for (Note note : notes) {
            noteResponseDtos.add(new NoteResponseDto(note));
        }

        return noteResponseDtos;
    }
    
    //find
    @Override
    @Transactional
    public NoteResponseDto getNote(Long id) {
    	//    	log.info("getNote()");
    	//    	log.info("ID: {}", id);
    	
    	Optional<Note> optionalNote = noteRepository.findById(id);

        if (optionalNote.isPresent()) {
            return new NoteResponseDto(optionalNote.get());
        } else {
            throw new IllegalArgumentException("아이디가 존재하지 않습니다.");
        }
    }
    
    //write , create
    @Override
    @Transactional
    public NoteResponseDto createNote(NoteRequestsDto requestsDto) {
    	//    	log.info("createNote()");
    	
    	Optional<Category> optionalCategory = categoryRepository.findById(requestsDto.getCategoryId());

        if (!optionalCategory.isPresent()) {
            throw new ResourceNotFoundException("Category not found with id: " + requestsDto.getCategoryId());
        }

        Category category = optionalCategory.get();
    	    
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
    	
    	 Optional<Note> optionalNote = noteRepository.findById(id);

    	    if (!optionalNote.isPresent()) {
    	        throw new IllegalArgumentException("아이디가 존재하지 않습니다.");
    	    }

    	    Note note = optionalNote.get();

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
    	
    	Optional<Note> optionalNote = noteRepository.findById(id);

        if (!optionalNote.isPresent()) {
            throw new IllegalArgumentException("아이디가 존재하지 않습니다.");
        }

    	noteRepository.deleteById(id);
    	//    	log.info("delete success ID: {}", id);
    	
        return new SuccessResponseDto(true);
    }
}
