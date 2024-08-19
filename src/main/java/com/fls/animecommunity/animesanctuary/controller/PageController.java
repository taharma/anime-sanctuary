package com.fls.animecommunity.animesanctuary.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestParam;

import com.fls.animecommunity.animesanctuary.model.note.dto.NoteResponseDto;
import com.fls.animecommunity.animesanctuary.service.NoteServiceImpl;

/*
 * note.com을 benchmarking 일단 그대로 만듬
 */

@Slf4j
@Controller
@RequestMapping("topic")
@RequiredArgsConstructor
public class PageController {
	
	private final NoteServiceImpl NoteService;
	
	
	//list
	@GetMapping("list")
	public String list() {
		return "list";
	}
	
	//read
	@GetMapping("read/{note_id}")
	public String getPost(@PathVariable("note_id") Long id, Model model) {
	    NoteResponseDto post = NoteService.getNote(id);
	    model.addAttribute("post", post);
	    return "read"; // "read.html" 템플릿 반환
	}

	
	//write
	@GetMapping("write")
	public String write() {
		return "write";
	}
	
	//update
	@GetMapping("update/{note_id}")
	public String update(@PathVariable("note_id") Long id) {
		return "update";
	}
	
	
	
}
