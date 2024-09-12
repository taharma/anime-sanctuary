package com.fls.animecommunity.animesanctuary.service.interfaces;

import java.util.List;

import com.fls.animecommunity.animesanctuary.dto.categoryDto.CategoryRequestsDto;
import com.fls.animecommunity.animesanctuary.dto.categoryDto.CategoryResponseDto;
import com.fls.animecommunity.animesanctuary.dto.categoryDto.SuccessResponseDto;
import com.fls.animecommunity.animesanctuary.dto.noteDto.NoteResponseDto;

public interface CategoryService {
	
	//카테고리별로 note 가져오기 (어디에 위치해야하는가? note ,category)
    List<NoteResponseDto> getNotesByCategory(Long categoryId);
	
    //카테고리 목록 조회
    List<CategoryResponseDto> getCategories();
    
    //admin , 카테고리 생성
    CategoryResponseDto createCategory(CategoryRequestsDto categoryRequestDto);
    
    //admin , 카테고리 삭제
    SuccessResponseDto deleteCategory(Long id) throws Exception;
    
    //admin , 카테고리 수정
    CategoryResponseDto updateCategory(Long id, CategoryRequestsDto requestsDto) throws Exception;
}
