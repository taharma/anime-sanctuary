package com.fls.animecommunity.animesanctuary.model.note.dto;

import lombok.Getter;

@Getter
public class SuccessResponseDto {
	private boolean success;
	
	public SuccessResponseDto(boolean success) {
        this.success = success;
    }
}
