package com.code.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.code.api.dto.CategoryDto;
import com.code.api.entity.Category;
import com.code.api.exception.ResourceNotFoundException;
import com.code.api.service.ICategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
	//add the dependency
	@Autowired
	private ICategoryService iCategoryService;
	
	@GetMapping("/")
	public List<Category> getAllCategories() {
		return iCategoryService.getAllCategories();
	}
	
	@GetMapping("/{id}")
	public Category getCategoryById(@PathVariable("id") int id) throws Exception {
		Optional<Category> dbCategory = iCategoryService.getById(id);
		if (dbCategory.isEmpty()) {
			throw new ResourceNotFoundException("Category", "categoryId", String.valueOf(id));
		}
		return dbCategory.get();
	}
	
	@GetMapping("/search/{catname}")
	public List<Category> search(@PathVariable("catname") String catname) {
		List<Category> dbCategories = iCategoryService.search(catname);
		if (dbCategories.size() == 0) {
			throw new ResourceNotFoundException("Category", "categoryName", catname);
		}
		return dbCategories;
	}
	
	@PostMapping("/create")
	public Category createCategory(@Valid @RequestBody Category category) {
		return iCategoryService.add(category);
	}
	
	@PutMapping("/edit")
	public Category editCategory(@Valid @RequestBody Category category) {
		return iCategoryService.update(category);
	}
	
	@PatchMapping("/edit/{id}")
	public Category editCategory(@PathVariable("id") int id, @Valid @RequestBody CategoryDto categoryDto) {
		Optional<Category> dbCategory=iCategoryService.getById(id);
		if (dbCategory.isEmpty()) {
			throw new ResourceNotFoundException("Category", "categoryId", String.valueOf(id));
		}
		Category category = dbCategory.get();
		if (categoryDto.getCategoryName() != null && !categoryDto.getCategoryName().isBlank()) {
			category.setCategoryName(categoryDto.getCategoryName());
		}
		if (categoryDto.getCategoryDesc() != null && !categoryDto.getCategoryDesc().isBlank()) {
			category.setCategoryDesc(categoryDto.getCategoryDesc());
		}
		return iCategoryService.update(category);
	}
	
	@DeleteMapping("/delete/{id}")
	public String deleteCategory(@PathVariable("id") int id) {
		Optional<Category> dbCategory = iCategoryService.getById(id);
		if (dbCategory.isEmpty()) {
			throw new ResourceNotFoundException("Category", "categoryId", String.valueOf(id));
		}
		iCategoryService.deleteById(id);
		return "Record is deleted successfully";
	}
}
