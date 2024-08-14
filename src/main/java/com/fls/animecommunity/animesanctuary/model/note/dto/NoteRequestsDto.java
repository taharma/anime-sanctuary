package com.fls.animecommunity.animesanctuary.model.note.dto;

import com.fls.animecommunity.animesanctuary.model.member.Member;

import lombok.Getter;

@Getter
public class NoteRequestsDto {
	private String title;
    private String contents;
    //
    private Member member;
    private String password;
}
