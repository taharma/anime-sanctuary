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
public class PostController {

    //IoC
    private final NoteServiceImpl noteService;
    
    //create
    @PostMapping("notes")
    public NoteResponseDto createPost(@RequestBody NoteRequestsDto requestsDto) {
    	return noteService.createPost(requestsDto);
    }
    
    //list
    @GetMapping("notes")
    public List<NoteResponseDto> getPosts() {
        return noteService.getPosts();
    }
    
    //find
    @GetMapping("notes/{note_id}")
    public NoteResponseDto getPost(@PathVariable("note_id") Long id) {
        return noteService.getPost(id);
    }
    
    
    //update
    @PutMapping("notes/{note_id}")
    public NoteResponseDto updatePost(@PathVariable("note_id") Long id, @RequestBody NoteRequestsDto requestsDto) throws Exception {
        return noteService.updatePost(id, requestsDto);
    }
    
    //delete
    @DeleteMapping("notes/{note_id}")
    public SuccessResponseDto deletePost(@PathVariable("note_id") Long id, 
    									 @RequestBody NoteRequestsDto requestsDto) throws Exception {
        return noteService.deletePost(id, requestsDto);
    }
}
