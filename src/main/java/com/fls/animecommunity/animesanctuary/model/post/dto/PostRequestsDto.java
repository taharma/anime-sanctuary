package com.fls.animecommunity.animesanctuary.model.post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter

public class PostRequestsDto {
	
	@NotEmpty
	private String title;
	@NotEmpty
    private String contents;
	@NotBlank
    private String author;
	@NotBlank
    private String password;
}
