package com.fls.animecommunity.animesanctuary.controller;

import com.fls.animecommunity.animesanctuary.model.note.Note;
import com.fls.animecommunity.animesanctuary.model.note.dto.NoteRequestsDto;
import com.fls.animecommunity.animesanctuary.model.note.dto.NoteResponseDto;
import com.fls.animecommunity.animesanctuary.model.note.dto.SuccessResponseDto;
import com.fls.animecommunity.animesanctuary.service.NoteServiceImpl;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api")
public class NoteController {

    
    private final NoteServiceImpl noteService;
    
    //create
    @PostMapping("notes")
    public NoteResponseDto createNote(@RequestBody NoteRequestsDto requestsDto) {
    	return noteService.createNote(requestsDto);
    }
    
    //list
    @GetMapping("notes")
    public List<NoteResponseDto> getNotes() {
        return noteService.getNotes();
    }
    
    //find
    @GetMapping("notes/{note_id}")
    public NoteResponseDto getNote(@PathVariable("note_id") Long id) {
        return noteService.getNote(id);
    }
    
    
    //update
    @PutMapping("notes/{note_id}")
    public NoteResponseDto updateNote(@PathVariable("note_id") Long id, 
    								  @RequestBody NoteRequestsDto requestsDto) throws Exception {
        return noteService.updateNote(id, requestsDto);
    }
    
    //delete
    @DeleteMapping("notes/{note_id}")
    public SuccessResponseDto deleteNote(@PathVariable("note_id") Long id, 
    									 @RequestBody NoteRequestsDto requestsDto) throws Exception {
        return noteService.deleteNote(id, requestsDto);
    }
}
