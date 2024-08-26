package com.fls.animecommunity.animesanctuary.controller.rest;

import org.springframework.web.bind.annotation.RestController;

import com.fls.animecommunity.animesanctuary.model.category.dto.CategoryRequestsDto;
import com.fls.animecommunity.animesanctuary.model.category.dto.CategoryResponseDto;
import com.fls.animecommunity.animesanctuary.model.category.dto.SuccessResponseDto;
import com.fls.animecommunity.animesanctuary.model.note.dto.NoteResponseDto;
import com.fls.animecommunity.animesanctuary.repository.CategoryRepository;
import com.fls.animecommunity.animesanctuary.service.impl.CategoryServiceImpl;
import com.fls.animecommunity.animesanctuary.service.interfaces.CategoryService;
import com.fls.animecommunity.animesanctuary.service.interfaces.NoteService;

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

/*
 * Category와 관련된 CRUD 
 * 그러나 생성,조회,삭제는 AdminController로 뺐음.
 * 1.Category의 조회
 * 2.1개의 Category에 해당하는 Notes조회
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("api/categories")
public class CategoryController {
	
	private final CategoryService categoryService;
	
	
	//Category 에 해당하는 Notes조회
	@GetMapping("/{categoryId}/notes")
    public ResponseEntity<?> getNotesByCategory(@PathVariable("categoryId") Long categoryId) {
        
//		log.info("getNotesByCategory 실행");
		List<NoteResponseDto> notes = categoryService.getNotesByCategory(categoryId);

        if (notes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(notes);
    }
	
	//Category list
	@GetMapping
    public ResponseEntity<?> getCategories() {
        
//		log.info("getCategories 실행");
		List<CategoryResponseDto> categories = categoryService.getCategories();

        if (categories.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(categories);
    }
	
	
	
	
}
