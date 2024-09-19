package com.fls.animecommunity.animesanctuary.controller.rest;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fls.animecommunity.animesanctuary.model.note.dto.NoteRequestsDto;
import com.fls.animecommunity.animesanctuary.model.note.dto.NoteResponseDto;
import com.fls.animecommunity.animesanctuary.model.note.dto.SuccessResponseDto;
import com.fls.animecommunity.animesanctuary.service.interfaces.NoteService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/*
 * NoteController class : Note의 CRUD기능 , api mapping 
 * DI : 의존성 주입 NoteServiceImpl
 * Method Name : createNote , getNotes , getNote , updateNote , deleteNote
 * parameter = dto: NoteResponseDto, NoteRequestsDto, SuccessResponseDto
 */
@CrossOrigin(origins = {"http://localhost:9000", "http://localhost:5501"}) // 클라이언트의 도메인을 명시
@RestController
@RequiredArgsConstructor
@RequestMapping("api/notes")
@Slf4j
public class NoteController {

    //DI
    private final NoteService noteService;
    
    //create Note
    @PostMapping(consumes = { "multipart/form-data" })
    public ResponseEntity<?> createNote(@RequestPart("note") @Valid NoteRequestsDto requestsDto,
                                        @RequestPart("image") MultipartFile image,
                                        BindingResult result) {
        log.info("Received Note request with title: {} and contents: {}", requestsDto.getTitle(), requestsDto.getContents());

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }

        try {
            NoteResponseDto responseDto = noteService.createNoteWithImage(requestsDto, image);
            return ResponseEntity.ok(responseDto);
        } catch (IOException e) {
            log.error("Error occurred while uploading the image: {}", e.getMessage());
            return ResponseEntity.status(500).body("Image upload failed: " + e.getMessage());
        }
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
    	// 유효성 검사 오류 확인
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }
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
    
    //Search Note
    @GetMapping("/search")
    public ResponseEntity<List<NoteResponseDto>> searchNotes(@RequestParam("keyword") String keyword) {
        List<NoteResponseDto> results = noteService.searchNotes(keyword);
        return ResponseEntity.ok(results);
    }
}
