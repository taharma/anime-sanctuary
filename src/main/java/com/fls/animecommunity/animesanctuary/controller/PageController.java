package com.fls.animecommunity.animesanctuary.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestParam;


@Slf4j
@Controller
@RequestMapping("board")
public class PageController {
	
	//list
	@GetMapping("list")
	public String list() {
		return "list";
	}
	
	//read
	@GetMapping("read")
	public String read() {
		return "read";
	}
	
	//write
	@GetMapping("write")
	public String write() {
		return "write";
	}
	
	//update
	@GetMapping("update")
	public String update() {
		return "update";
	}
	
	
	
}
