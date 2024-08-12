package com.fls.animecommunity.animesanctuary.model.post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostRequestsDto {
	
	@NotBlank
	private String title;
	@NotBlank
	@Size(min=1 , max=500)
    private String contents;
	@NotBlank
    private String author;
	@NotBlank
    private String password;
}
