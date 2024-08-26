package com.fls.animecommunity.animesanctuary.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fls.animecommunity.animesanctuary.exception.ResourceNotFoundException;
import com.fls.animecommunity.animesanctuary.model.category.Category;
import com.fls.animecommunity.animesanctuary.model.category.dto.CategoryRequestsDto;
import com.fls.animecommunity.animesanctuary.model.category.dto.CategoryResponseDto;
import com.fls.animecommunity.animesanctuary.model.category.dto.SuccessResponseDto;
import com.fls.animecommunity.animesanctuary.model.note.Note;
import com.fls.animecommunity.animesanctuary.model.note.dto.NoteResponseDto;
import com.fls.animecommunity.animesanctuary.repository.CategoryRepository;
import com.fls.animecommunity.animesanctuary.repository.NoteRepository;
import com.fls.animecommunity.animesanctuary.service.interfaces.CategoryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService{
	
	
	private final CategoryRepository categoryRepository;
	private final NoteRepository noteRepository;
	
	//getNotesByCategory 특정 category별 Notes 목록 조회
	@Override
	@Transactional(readOnly = true)
	public List<NoteResponseDto> getNotesByCategory(Long categoryId ) {
		
//		log.info("getNotesByCategory 실행");
//		log.info("categoryId :{}",categoryId );
		
		List<Note> notes = noteRepository.findByCategoryId(categoryId );
//		log.debug("Found {} notes for category ID: {}", notes.size(), categoryId );
		
		return notes.stream()
                .map(NoteResponseDto::new)
                .collect(Collectors.toList());
	}
	
	//find All categoies 모든 카테고리를 가져옴
	@Override
	@Transactional(readOnly = true)
	public List<CategoryResponseDto> getCategories() {
//		log.info("getCategories() 실행");
		
		List<CategoryResponseDto> categories = categoryRepository.findAll().stream()
                .map(CategoryResponseDto::new)
                .collect(Collectors.toList());
        
//        log.debug("Found {} categories", categories.size());
        return categories;
	}
	
	//write , create
	@Override
	@Transactional
	public CategoryResponseDto createCategory(CategoryRequestsDto requestsDto) {
//		log.info("createCategory() 실행");
		
		Category category = new Category();
		category.setName(requestsDto.getName());
		
		Category savedCategory = categoryRepository.save(category);
//		log.info("Category created successfully with ID: {}", savedCategory.getId());
		
		return new CategoryResponseDto(savedCategory);
	}
	
	//delete
	@Override
	@Transactional
	public SuccessResponseDto deleteCategory(Long id) throws Exception{
//		log.info("deleteCategory() 실행");
//		log.info("Deleting category with ID: {}", id);
		
		Category category = categoryRepository.findById(id).orElseThrow(
				() -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
		);
		
		categoryRepository.deleteById(id);
//		log.info("Category deleted successfully with ID: {}", id);
		
		return new SuccessResponseDto(true);
	}
	
	//update
	@Override
	@Transactional
	public CategoryResponseDto updateCategory(Long id, CategoryRequestsDto requestsDto) {
//		log.info("updateCategory() 실행");
//		log.info("Updating category with ID: {}", id);
		
		Category category = categoryRepository.findById(id)
	            .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
	    category.setName(requestsDto.getName());
	    Category updatedCategory = categoryRepository.save(category);
//	    log.info("Category updated successfully with ID: {}", updatedCategory.getId());
	    
	    return new CategoryResponseDto(updatedCategory);
	}
}
