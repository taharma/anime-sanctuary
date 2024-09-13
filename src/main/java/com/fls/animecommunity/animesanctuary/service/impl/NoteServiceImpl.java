package com.fls.animecommunity.animesanctuary.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fls.animecommunity.animesanctuary.exception.ResourceNotFoundException;
import com.fls.animecommunity.animesanctuary.model.category.Category;
import com.fls.animecommunity.animesanctuary.model.note.Note;
import com.fls.animecommunity.animesanctuary.model.note.dto.NoteRequestsDto;
import com.fls.animecommunity.animesanctuary.model.note.dto.NoteResponseDto;
import com.fls.animecommunity.animesanctuary.model.note.dto.SuccessResponseDto;
import com.fls.animecommunity.animesanctuary.repository.CategoryRepository;
import com.fls.animecommunity.animesanctuary.repository.NoteRepository;
import com.fls.animecommunity.animesanctuary.service.interfaces.NoteService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;
    private final CategoryRepository categoryRepository;

    // List all notes
    @Override
    @Transactional(readOnly = true)
    public List<NoteResponseDto> getNotes() {
        log.info("Fetching all notes ordered by modified date.");
        List<Note> notes = noteRepository.findAllByOrderByModifiedAtDesc();
        return notes.stream()
                    .map(NoteResponseDto::new)
                    .collect(Collectors.toList());
    }

    // Find a specific note by id
    @Override
    @Transactional(readOnly = true)
    public NoteResponseDto getNote(Long id) {
        log.info("Fetching note with ID: {}", id);
        Note note = noteRepository.findById(id)
                                  .orElseThrow(() -> new ResourceNotFoundException("아이디가 존재하지 않습니다.: " + id));
        return new NoteResponseDto(note);
    }

    // Create a new note
    @Override
    @Transactional
    public NoteResponseDto createNote(NoteRequestsDto requestsDto) {
        log.info("Creating a new note with title: {}", requestsDto.getTitle());

        Category category = categoryRepository.findById(requestsDto.getCategoryId())
                                              .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + requestsDto.getCategoryId()));

        Note note = new Note();
        note.setTitle(requestsDto.getTitle());
        note.setContents(requestsDto.getContents());
        note.setCategory(category);

        Note savedNote = noteRepository.save(note);
        log.info("Note successfully created with ID: {}", savedNote.getId());

        return new NoteResponseDto(savedNote);
    }

    // Update an existing note
    @Override
    @Transactional
    public NoteResponseDto updateNote(Long id, NoteRequestsDto requestsDto) {
        log.info("Updating note with ID: {}", id);
        
        Note note = noteRepository.findById(id)
                                  .orElseThrow(() -> new ResourceNotFoundException("아이디가 존재하지 않습니다.: " + id));

        note.update(requestsDto);
        log.info("Note successfully updated with ID: {}", id);
        
        return new NoteResponseDto(note);
    }

    // Delete a note
    @Override
    @Transactional
    public SuccessResponseDto deleteNote(Long id, NoteRequestsDto requestsDto) {
        log.info("Deleting note with ID: {}", id);
        
        Note note = noteRepository.findById(id)
                                  .orElseThrow(() -> new ResourceNotFoundException("아이디가 존재하지 않습니다.: " + id));

        noteRepository.deleteById(id);
        log.info("Note successfully deleted with ID: {}", id);
        
        return new SuccessResponseDto(true);
    }

    // Search notes by keyword
    @Override
    @Transactional(readOnly = true)
    public List<NoteResponseDto> searchNotes(String keyword) {
        log.info("Searching notes by keyword: {}", keyword);
        
        List<Note> notes = noteRepository.findByTitleContainingOrContentsContaining(keyword, keyword);
        return notes.stream()
                    .map(NoteResponseDto::new)
                    .collect(Collectors.toList());
    }
}
