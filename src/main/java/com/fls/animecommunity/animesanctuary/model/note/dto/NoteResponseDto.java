package com.fls.animecommunity.animesanctuary.model.note.dto;

import java.time.LocalDateTime;

import com.fls.animecommunity.animesanctuary.model.member.Member;
import com.fls.animecommunity.animesanctuary.model.note.Note;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NoteResponseDto {//응답값을 모아서 보내는 놈
	private Long id;
    private String title;
    private String contents;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    
    public NoteResponseDto(Note entity) {//따라서 생성자가 필요
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.contents = entity.getContents();
        this.createdAt = entity.getCreatedAt();
        this.modifiedAt = entity.getModifiedAt();
    }
}
