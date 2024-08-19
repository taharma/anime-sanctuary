package com.fls.animecommunity.animesanctuary.controller;

import org.springframework.web.bind.annotation.RestController;

import com.fls.animecommunity.animesanctuary.model.category.dto.CategoryRequestsDto;
import com.fls.animecommunity.animesanctuary.model.category.dto.CategoryResponseDto;
import com.fls.animecommunity.animesanctuary.model.category.dto.SuccessResponseDto;
import com.fls.animecommunity.animesanctuary.service.CategoryService;
import com.fls.animecommunity.animesanctuary.service.CategoryServiceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/*
 * 관리자()
 */

@RestController
@Slf4j
@RequestMapping("admin")
@RequiredArgsConstructor
public class AdminController {
	
	private final CategoryServiceImpl categoryService;
	
	//create category 
	@PostMapping("categories")
    public CategoryResponseDto createCategory(@RequestBody CategoryRequestsDto categoryRequestDto) {
        // 카테고리 생성 로직 호출
        return categoryService.createCategory(categoryRequestDto);
    }
	
	//카테고리 삭제
	@DeleteMapping("{category_id}")
	public SuccessResponseDto deleteNote(@PathVariable("category_id")Long id,
										 @RequestBody CategoryRequestsDto requestsDto) throws Exception{
		return categoryService.deleteCategory(id,requestsDto);
	}
	
	//카테고리 수정?
	@PutMapping("/{category_id}")
	public CategoryResponseDto updateCategory(@PathVariable("{category_id}")Long id,
											  @RequestBody CategoryRequestsDto requestsDto) throws Exception{
		return categoryService.updateCategory(id,requestsDto);
	}
}
