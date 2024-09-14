package com.fls.animecommunity.animesanctuary.service.interfaces;

import java.util.List;

import com.fls.animecommunity.animesanctuary.model.note.dto.NoteRequestsDto;
import com.fls.animecommunity.animesanctuary.model.note.dto.NoteResponseDto;
import com.fls.animecommunity.animesanctuary.model.note.dto.SuccessResponseDto;

public interface NoteService {
    List<NoteResponseDto> getNotes();
    NoteResponseDto getNote(Long id);
    NoteResponseDto createNote(NoteRequestsDto requestsDto);
    NoteResponseDto updateNote(Long id, NoteRequestsDto requestsDto) throws Exception;
    
    // deleteNote 메소드에서 NoteRequestsDto 대신 memberId 사용
    SuccessResponseDto deleteNote(Long id, Long memberId) throws Exception;
    
    List<NoteResponseDto> searchNotes(String keyword);
}
