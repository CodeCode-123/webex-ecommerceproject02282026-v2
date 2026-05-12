package com.code.api.dto;

import com.code.api.entity.Category;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemDto {
	@Size(min=1, message="Item name should be at least 1 character")
	private String itemName;
	@Min(value=0, message="Price should not be negative")
	private double itemPrice;
	private byte[] imageData;
	private Category category;
}
