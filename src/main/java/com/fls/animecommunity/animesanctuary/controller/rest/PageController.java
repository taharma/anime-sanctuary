package com.fls.animecommunity.animesanctuary.controller.rest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.fls.animecommunity.animesanctuary.model.note.dto.NoteResponseDto;
import com.fls.animecommunity.animesanctuary.service.interfaces.NoteService;

@Slf4j
@Controller
@RequestMapping("topic")
@RequiredArgsConstructor
public class PageController {
    
    // 필드명을 소문자로 시작하도록 수정
    private final NoteService noteService;
    
    //list
    @GetMapping("list")
    public String list() {
        return "list";
    }
    
    //read
    @GetMapping("read/{note_id}")
    public String getPost(@PathVariable("note_id") Long id, Model model) {
        NoteResponseDto post = noteService.getNote(id);
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
