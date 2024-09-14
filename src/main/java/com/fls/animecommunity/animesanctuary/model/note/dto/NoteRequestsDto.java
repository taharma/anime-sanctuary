package com.fls.animecommunity.animesanctuary.model.note.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/*
 * Note의 Requests 의 DataTransferObject
 */

@Getter
@Setter
public class NoteRequestsDto {
    @NotBlank(message = "title must not be blank")
    private String title;
    
    @NotBlank(message = "contents must not be blank")
    private String contents;
    
    // 카테고리 ID (주의: 카테고리 id = 1 anime 이런 식으로 구동 예정)
    private Long categoryId;

    // 작성자 ID
    private Long memberId;  
}
