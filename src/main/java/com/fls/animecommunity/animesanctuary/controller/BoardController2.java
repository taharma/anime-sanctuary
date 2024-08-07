package com.fls.animecommunity.animesanctuary.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fls.animecommunity.animesanctuary.service.BoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Slf4j
@Controller
@RequestMapping("board")
@RequiredArgsConstructor
public class BoardController2 {
	
	private final BoardService boardService;
	
	@GetMapping("list")
	public String list() {
		return "board/list";
	}
	
	@GetMapping("write")
	public String write() {
		return "board/write";
	}
	
	
	
	
	
}
