package com.fls.animecommunity.animesanctuary.model.note.dto;

import com.fls.animecommunity.animesanctuary.model.member.Member;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

/*
 * Note의 Requests 의 DataTransferObject
 */

@Getter
public class NoteRequestsDto {
	@NotBlank(message = "title must not be blank")
	private String title;
	@NotBlank(message = "contents must not be blank")
    private String contents;
	
	private Long categoryId;
}
