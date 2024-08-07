package com.fls.animecommunity.animesanctuary.model.board.dto;

import java.time.LocalDateTime;

import com.fls.animecommunity.animesanctuary.model.board.Board;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardResponseDto {//응답값을 모아서 보내는 놈
	private Long id;
    private String title;
    private String contents;
    private String author;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    
    public BoardResponseDto(Board entity) {//따라서 생성자가 필요
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.contents = entity.getContents();
        this.author = entity.getAuthor();
        this.createdAt = entity.getCreatedAt();
        this.modifiedAt = entity.getModifiedAt();
    }
}
