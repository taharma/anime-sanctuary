package com.fls.animecommunity.animesanctuary.service;

import java.util.List;

import com.fls.animecommunity.animesanctuary.model.note.dto.NoteRequestsDto;
import com.fls.animecommunity.animesanctuary.model.note.dto.NoteResponseDto;
import com.fls.animecommunity.animesanctuary.model.note.dto.SuccessResponseDto;

public interface NoteService {
	List<NoteResponseDto> getPosts();
    NoteResponseDto getPost(Long id);
    NoteResponseDto createPost(NoteRequestsDto requestsDto);
    NoteResponseDto updatePost(Long id, NoteRequestsDto requestsDto) throws Exception;
    SuccessResponseDto deletePost(Long id, NoteRequestsDto requestsDto) throws Exception;
}
