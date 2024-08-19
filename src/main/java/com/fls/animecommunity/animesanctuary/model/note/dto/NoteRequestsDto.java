package com.fls.animecommunity.animesanctuary.model.note.dto;

import com.fls.animecommunity.animesanctuary.model.member.Member;

import lombok.Getter;

/*
 * Note의 Requests 의 DataTransferObject
 */

@Getter
public class NoteRequestsDto {
	private String title;
    private String contents;
}
