package com.fls.animecommunity.animesanctuary.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fls.animecommunity.animesanctuary.model.category.Category;
import com.fls.animecommunity.animesanctuary.model.category.dto.CategoryRequestsDto;
import com.fls.animecommunity.animesanctuary.model.category.dto.CategoryResponseDto;
import com.fls.animecommunity.animesanctuary.model.category.dto.SuccessResponseDto;
import com.fls.animecommunity.animesanctuary.model.note.dto.NoteResponseDto;
import com.fls.animecommunity.animesanctuary.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{
	
	
	private final CategoryRepository categoryRepository;
	
	@Override
	public List<NoteResponseDto> getNotesByCategory(Long categoryId) {
		return null;
	}

	@Override
	public List<CategoryResponseDto> getCategories() {
		 return null;
	}

	public CategoryResponseDto createCategory(CategoryRequestsDto categoryRequestDto) {
		return null;
	}

	public SuccessResponseDto deleteCategory(Long id, CategoryRequestsDto requestsDto) throws Exception{
		Category category = categoryRepository.findById(id).orElseThrow(
				() -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
		);
		categoryRepository.deleteById(id);
		return new SuccessResponseDto(true);
	}

	public CategoryResponseDto updateCategory(Long id, CategoryRequestsDto requestsDto) {
		return null;
	}
}
