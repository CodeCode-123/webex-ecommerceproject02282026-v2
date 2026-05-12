package com.code.api.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDto {
	@Size(min=1, message="Category name should be at least 1 character")
	private String categoryName;
	private String categoryDesc;
}
