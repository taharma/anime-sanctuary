package com.fls.animecommunity.animesanctuary.controller;

import org.springframework.web.bind.annotation.RestController;

import com.fls.animecommunity.animesanctuary.model.category.dto.CategoryRequestsDto;
import com.fls.animecommunity.animesanctuary.model.category.dto.CategoryResponseDto;
import com.fls.animecommunity.animesanctuary.model.category.dto.SuccessResponseDto;
import com.fls.animecommunity.animesanctuary.service.CategoryService;
import com.fls.animecommunity.animesanctuary.service.CategoryServiceImpl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/*
 * 관리자(Admin) 기능을 가지고 할수있는 작업
 * 1.카테고리의 생성,삭제,조회
 * 2.
 */

@RestController
@Slf4j
@RequestMapping("api/admin")
@RequiredArgsConstructor
public class AdminController {
	
	private final CategoryService categoryService;
	
	//create category 
	@PostMapping("/categories")
    public ResponseEntity<CategoryResponseDto>  createCategory(@Valid @RequestBody CategoryRequestsDto requestsDto) {
        // 카테고리 생성 로직 호출
        CategoryResponseDto responseDto = categoryService.createCategory(requestsDto);
//		log.info("create category 실행");
		return ResponseEntity.ok(responseDto);
    }
	
	//카테고리 삭제
	@DeleteMapping("/categories/{categoryId}")
	public ResponseEntity<SuccessResponseDto> deleteNote(@PathVariable("categoryId")Long id) throws Exception{
//		log.info("delete category 실행");
		SuccessResponseDto responseDto  = categoryService.deleteCategory(id);
		return ResponseEntity.ok(responseDto);
	}
	
	//카테고리 수정
	@PostMapping("/categories/{categoryId}")
	public ResponseEntity<CategoryResponseDto> updateCategory(@Valid @PathVariable("categoryId")Long id,
											  @RequestBody CategoryRequestsDto requestsDto) throws Exception{
//		log.info("update category 실행");
		CategoryResponseDto responseDto = categoryService.updateCategory(id,requestsDto);
		return ResponseEntity.ok(responseDto);
	}
}
