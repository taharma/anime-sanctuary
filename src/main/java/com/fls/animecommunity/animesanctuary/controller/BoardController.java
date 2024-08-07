package com.fls.animecommunity.animesanctuary.controller;

import org.springframework.web.bind.annotation.RestController;

import com.fls.animecommunity.animesanctuary.repository.PostRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class BoardController {
	
	private PostRepository postRepository;
}
