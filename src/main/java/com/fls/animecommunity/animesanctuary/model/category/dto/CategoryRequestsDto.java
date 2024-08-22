package com.fls.animecommunity.animesanctuary.model.category.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/*
 * Category의 Requests 의 DataTransferObject
 */

@Data
public class CategoryRequestsDto {
	@NotBlank(message = "Category name must not be blank")
	private String name;
}
