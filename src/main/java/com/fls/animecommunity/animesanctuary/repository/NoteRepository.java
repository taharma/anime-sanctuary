package com.fls.animecommunity.animesanctuary.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fls.animecommunity.animesanctuary.model.note.Note;
import com.fls.animecommunity.animesanctuary.model.note.dto.NoteResponseDto;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

	List<Note> findAllByOrderByModifiedAtDesc();
	List<Note> findByCategoryIdOrderByModifiedAtDesc(Long categoryId);
	List<Note> findByCategoryId(Long categoryId);
}
