package com.fls.animecommunity.animesanctuary.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestParam;


@Slf4j
@Controller
public class HomeController {
	
	//메인페이지
	@GetMapping("/")
	public String home() {
		log.info("성공");
		return "index";
	}	
}
