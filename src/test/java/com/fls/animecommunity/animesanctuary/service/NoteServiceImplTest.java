package com.fls.animecommunity.animesanctuary.service;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.fls.animecommunity.animesanctuary.model.category.Category;
import com.fls.animecommunity.animesanctuary.model.note.Note;
import com.fls.animecommunity.animesanctuary.model.note.dto.NoteRequestsDto;
import com.fls.animecommunity.animesanctuary.model.note.dto.NoteResponseDto;
import com.fls.animecommunity.animesanctuary.repository.CategoryRepository;
import com.fls.animecommunity.animesanctuary.repository.NoteRepository;
import com.fls.animecommunity.animesanctuary.service.impl.NoteServiceImpl;

class NoteServiceImplTest {

    @Mock
    private NoteRepository noteRepository;

    @Mock
    private CategoryRepository categoryRepository;  // CategoryRepository를 목으로 추가

    @InjectMocks
    private NoteServiceImpl noteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getNote_shouldReturnNoteResponseDto_whenNoteExists() {
        // Note 객체를 생성
        Note note = new Note();
        note.setTitle("Test Note");
        note.setContents("Test Contents");

        // Note 객체를 Optional로 감싸서 mock 리턴
        when(noteRepository.findById(anyLong())).thenReturn(Optional.of(note));

        // 실제로 반환할 NoteResponseDto를 기대
        NoteResponseDto responseDto = noteService.getNote(1L);

        assertNotNull(responseDto);
        assertEquals("Test Note", responseDto.getTitle());
        assertEquals("Test Contents", responseDto.getContents());
    }

    @Test
    void createNote_shouldReturnNoteResponseDto_whenValidRequestIsGiven() {
        NoteRequestsDto requestDto = new NoteRequestsDto();
        requestDto.setTitle("Test Note");
        requestDto.setContents("Test Contents");
        requestDto.setCategoryId(1L);

        // 카테고리 목 객체 생성 및 반환
        Category category = new Category();
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));

        // Note 객체 생성 및 저장 로직 목 설정
        Note savedNote = new Note();
        savedNote.setTitle("Test Note");
        savedNote.setContents("Test Contents");
        savedNote.setCategory(category);

        when(noteRepository.save(any(Note.class))).thenReturn(savedNote);

        // NoteServiceImpl의 createNote 실행 및 결과 검증
        NoteResponseDto responseDto = noteService.createNote(requestDto);

        assertNotNull(responseDto);
        assertEquals("Test Note", responseDto.getTitle());
        verify(noteRepository, times(1)).save(any(Note.class));
    }
}
