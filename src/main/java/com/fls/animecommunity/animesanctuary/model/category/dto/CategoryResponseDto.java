package com.fls.animecommunity.animesanctuary.model.category.dto;

import java.time.LocalDateTime;

import com.fls.animecommunity.animesanctuary.model.note.Note;

import lombok.Getter;
import lombok.NoArgsConstructor;

/*
 * Category의 Response , 응답객체
 */

@Getter
@NoArgsConstructor
public class CategoryResponseDto {
	private Long id;
    private String title;
    private String contents;
    
    public CategoryResponseDto(Note entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.contents = entity.getContents();
    }
}
