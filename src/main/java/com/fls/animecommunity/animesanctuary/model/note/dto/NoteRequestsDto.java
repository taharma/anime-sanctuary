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
    
    private Long categoryId;
}
