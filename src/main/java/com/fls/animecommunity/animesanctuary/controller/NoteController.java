package com.fls.animecommunity.animesanctuary.controller;

import com.fls.animecommunity.animesanctuary.model.note.Note;
import com.fls.animecommunity.animesanctuary.model.note.dto.NoteRequestsDto;
import com.fls.animecommunity.animesanctuary.model.note.dto.NoteResponseDto;
import com.fls.animecommunity.animesanctuary.model.note.dto.SuccessResponseDto;
import com.fls.animecommunity.animesanctuary.service.impl.NoteServiceImpl;
import com.fls.animecommunity.animesanctuary.service.interfaces.NoteService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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
    public ResponseEntity<?> createNote(@Valid @RequestBody NoteRequestsDto requestsDto
    								    ,BindingResult result) {
    	//log.info("createNote 실행");
    	
    	if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }
    	NoteResponseDto responseDto = noteService.createNote(requestsDto);
    	return ResponseEntity.ok(responseDto);
    }
    
    //list Note
    @GetMapping
    public ResponseEntity<List<NoteResponseDto>> getNotes() {
    	//log.info("getNotes 실행");
    	List<NoteResponseDto> list = noteService.getNotes();
        return ResponseEntity.ok(list);
    }
    
    //find Note
    @GetMapping("/{noteId}")
    public ResponseEntity<NoteResponseDto> getNote(@PathVariable("noteId") Long id) {
    	//log.info("getNote 실행");
    	NoteResponseDto note = noteService.getNote(id);
    	return ResponseEntity.ok(note);
    }
    
    
    //update Note
    @PostMapping("/{noteId}")
    public ResponseEntity<?> updateNote(@Valid @PathVariable("noteId") Long id, 
    								    @RequestBody NoteRequestsDto requestsDto
    								    ,BindingResult result) throws Exception {
    	//log.info("updateNote 실행");
    	NoteResponseDto updateNote = noteService.updateNote(id, requestsDto);
    	return ResponseEntity.ok(updateNote);
    }
    
    //delete Note
    @DeleteMapping("/{noteId}")
    public ResponseEntity<SuccessResponseDto> deleteNote(@PathVariable("noteId") Long id, 
    									 				 @RequestBody NoteRequestsDto requestsDto) throws Exception {
    	//log.info("deleteNote 실행");
    	SuccessResponseDto responseDto = noteService.deleteNote(id, requestsDto);
    	return ResponseEntity.ok(responseDto);
    }
}
