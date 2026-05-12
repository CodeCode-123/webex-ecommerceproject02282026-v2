package com.code.api.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.code.api.entity.Category;
import com.code.api.repository.ICategoryRepository;
import com.code.api.service.ICategoryService;


@Service
@Transactional
public class CategoryServiceImpl implements ICategoryService {
	@Autowired
	private ICategoryRepository iCategoryRepository;

	@Override
	public Category add(Category category) {
		return iCategoryRepository.save(category);
	}

	@Override
	public Category update(Category category) {
		return iCategoryRepository.save(category);
	}

	@Override
	public void delete(Category category) {
		iCategoryRepository.delete(category);
	}

	@Override
	public void deleteById(int id) {
		iCategoryRepository.deleteById(id);
	}

	@Override
	public List<Category> getAllCategories() {
		return iCategoryRepository.findAll();
	}

	@Override
	public Optional<Category> getById(int id) {
		return iCategoryRepository.findById(id);
	}

	@Override
	public Optional<Category> getCategoryByName(String catname) {
		return iCategoryRepository.findByCategoryName(catname);
	}

	@Override
	public List<Category> search(String catname) {
		return iCategoryRepository.findByCategoryNameLike(catname);
	}
}
