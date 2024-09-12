package com.fls.animecommunity.animesanctuary.dto.noteDto;

import java.time.LocalDateTime;

import com.fls.animecommunity.animesanctuary.model.member.Member;
import com.fls.animecommunity.animesanctuary.model.note.Note;

import lombok.Getter;
import lombok.NoArgsConstructor;

/*
 * Note의 Response , 응답객체
 */

@Getter
@NoArgsConstructor
public class NoteResponseDto {
	private Long id;
    private String title;
    private String contents;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private Long categoryId;
    
    public NoteResponseDto(Note entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.contents = entity.getContents();
        this.createdAt = entity.getCreatedAt();
        this.modifiedAt = entity.getModifiedAt();
        // Null 체크 후 categoryId 설정
        if (entity.getCategory() != null) {
            this.categoryId = entity.getCategory().getId();
        } else {
            this.categoryId = null; // 또는 기본값 설정
        }
    }
}

