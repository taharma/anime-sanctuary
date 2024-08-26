package com.fls.animecommunity.animesanctuary.controller;

import org.springframework.web.bind.annotation.RestController;

import com.fls.animecommunity.animesanctuary.model.note.dto.NoteResponseDto;
import com.fls.animecommunity.animesanctuary.service.interfaces.NoteService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@Slf4j
@RequiredArgsConstructor
public class PopularController {
	
	private final NoteService noteService;
	
	
//	@GetMapping("/api/posts/popular")
//	public ResponseEntity<?> getMethodName(@RequestParam String param) {
//		List<NoteResponseDto> popularNotes = noteService.getPopularNotes();
//		
//		if (popularNotes.isEmpty()) {
//			return ResponseEntity.noContent().build();
//		}
//		
//		return ResponseEntity.ok(popularNotes);
//	}
	
}
