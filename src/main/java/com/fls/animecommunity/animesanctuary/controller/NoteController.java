package com.fls.animecommunity.animesanctuary.controller;

import com.fls.animecommunity.animesanctuary.model.note.Note;
import com.fls.animecommunity.animesanctuary.model.note.dto.NoteRequestsDto;
import com.fls.animecommunity.animesanctuary.model.note.dto.NoteResponseDto;
import com.fls.animecommunity.animesanctuary.model.note.dto.SuccessResponseDto;
import com.fls.animecommunity.animesanctuary.service.NoteService;
import com.fls.animecommunity.animesanctuary.service.NoteServiceImpl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
 * NoteController class : Note의 CRUD기능 , api mapping 
 * DI : NoteServiceImpl
 * Method Name : createNote , getNotes , getNote , updateNote , deleteNote
 * parameter = dto: NoteResponseDto, NoteRequestsDto, SuccessResponseDto
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("api/notes")
public class NoteController {

    //DI
    private final NoteService noteService;
    
    //create Note
    @PostMapping
    public NoteResponseDto createNote(@Valid @RequestBody NoteRequestsDto requestsDto) {
//		log.info("createNote 실행");
    	return noteService.createNote(requestsDto);
    }
    
    //list Note
    @GetMapping
    public List<NoteResponseDto> getNotes() {
//		log.info("getNotes 실행");
        return noteService.getNotes();
    }
    
    //find Note
    @GetMapping("/{note_id}")
    public NoteResponseDto getNote(@PathVariable("note_id") Long id) {
//		log.info("getNote 실행");
    	return noteService.getNote(id);
    }
    
    
    //update Note
    @PostMapping("/{note_id}")
    public NoteResponseDto updateNote(@Valid @PathVariable("note_id") Long id, 
    								  @RequestBody NoteRequestsDto requestsDto) throws Exception {
//		log.info("updateNote 실행");
    	return noteService.updateNote(id, requestsDto);
    }
    
    //delete Note
    @DeleteMapping("/{note_id}")
    public SuccessResponseDto deleteNote(@PathVariable("note_id") Long id, 
    									 @RequestBody NoteRequestsDto requestsDto) throws Exception {
//		log.info("deleteNote 실행");        
    	return noteService.deleteNote(id, requestsDto);
    }
}
