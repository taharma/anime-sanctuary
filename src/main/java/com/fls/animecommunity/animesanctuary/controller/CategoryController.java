package com.fls.animecommunity.animesanctuary.controller;

import org.springframework.web.bind.annotation.RestController;

import com.fls.animecommunity.animesanctuary.model.category.dto.CategoryRequestsDto;
import com.fls.animecommunity.animesanctuary.model.category.dto.CategoryResponseDto;
import com.fls.animecommunity.animesanctuary.model.category.dto.SuccessResponseDto;
import com.fls.animecommunity.animesanctuary.model.note.dto.NoteResponseDto;
import com.fls.animecommunity.animesanctuary.repository.CategoryRepository;
import com.fls.animecommunity.animesanctuary.service.CategoryService;
import com.fls.animecommunity.animesanctuary.service.CategoryServiceImpl;
import com.fls.animecommunity.animesanctuary.service.NoteService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {
	
	private CategoryServiceImpl categoryService;
	
	
	//1.8 카테고리별 노트 목록 조회 API
	@GetMapping("/{category_id}/notes")
    public ResponseEntity<?> getNotesByCategory(@PathVariable("category_id") Long categoryId) {
        List<NoteResponseDto> notes = categoryService.getNotesByCategory(categoryId);

        if (notes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(notes);
    }
	
	//1.9 전체 카테고리 목록 조회 API (유저 접근 가능)
	@GetMapping
    public ResponseEntity<?> getCategories() {
        List<CategoryResponseDto> categories = categoryService.getCategories();

        if (categories.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(categories);
    }
	
	
	
	
}
