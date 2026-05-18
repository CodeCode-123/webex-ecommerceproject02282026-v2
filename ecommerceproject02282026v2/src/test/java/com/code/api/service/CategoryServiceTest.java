package com.code.api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.code.api.Ecommerceproject02282026v2Application;
import com.code.api.entity.Category;
import com.code.api.repository.ICategoryRepository;
import com.code.api.service.impl.CategoryServiceImpl;

@SpringBootTest(classes=Ecommerceproject02282026v2Application.class)
public class CategoryServiceTest {
	@Mock
	private ICategoryRepository iCategoryRepository;
	@InjectMocks
	private CategoryServiceImpl categoryServiceImpl;
	@Autowired
	private Category categoryOneToSave;
	@Autowired
	private Category categoryTwoToSave;
	@Autowired
	private Category categoryOneSaved;
	@Autowired
	private Category categoryTwoSaved;
	@Autowired
	private Category categoryOneUpdated;
	
	public CategoryServiceTest() {
		//This initializes the @Mock and @InjectMocks above
		MockitoAnnotations.openMocks(this);
	}
	
	@BeforeEach
	public void beforeEach() {
		//set categoryOneToSave
		setCategoryOneToSave();
		setCategoryTwoToSave();
		setCategoryOneSaved();
		setCategoryTwoSaved();
	}
	
	@Test
	void testAddCategory() {
		when(iCategoryRepository.save(categoryOneToSave)).thenReturn(categoryOneSaved);
		assertSame(categoryOneSaved, categoryServiceImpl.add(categoryOneToSave));
		ArgumentCaptor<Category> captor = ArgumentCaptor.forClass(Category.class);
		verify(iCategoryRepository, times(1)).save(captor.capture());
	}
	
	@Test
	void testUpdateCategory() {
		setCategoryOneUpdated();
		when(iCategoryRepository.save(categoryOneUpdated)).thenReturn(categoryOneUpdated);
		assertSame(categoryOneUpdated, categoryServiceImpl.update(categoryOneUpdated));
		ArgumentCaptor<Category> captor = ArgumentCaptor.forClass(Category.class);
		verify(iCategoryRepository, times(1)).save(captor.capture());
	}
	
	@Test
	void testDeleteCategory() {
		doNothing().when(iCategoryRepository).delete(categoryOneSaved);
		doNothing().when(iCategoryRepository).delete(categoryTwoSaved);
		categoryServiceImpl.delete(categoryOneSaved);
		categoryServiceImpl.delete(categoryTwoSaved);
		ArgumentCaptor<Category> captor = ArgumentCaptor.forClass(Category.class);
		verify(iCategoryRepository, times(2)).delete(captor.capture());
	}
	
	@Test
	void testDeleteCategoryById() {
		doNothing().when(iCategoryRepository).deleteById(1);
		doNothing().when(iCategoryRepository).deleteById(2);
		categoryServiceImpl.deleteById(1);
		categoryServiceImpl.deleteById(2);
		verify(iCategoryRepository, times(2)).deleteById(anyInt());
	}
	
	@Test
	void testGetAllCategories() {
		when(iCategoryRepository.findAll()).thenReturn(List.of(categoryOneSaved, categoryTwoSaved));
		assertEquals(List.of(categoryOneSaved, categoryTwoSaved), categoryServiceImpl.getAllCategories());
		verify(iCategoryRepository, times(1)).findAll();
	}
	
	@Test
	void testGetCategoryById() {
		when(iCategoryRepository.findById(1)).thenReturn(Optional.of(categoryOneSaved));
		when(iCategoryRepository.findById(2)).thenReturn(Optional.of(categoryTwoSaved));
		assertSame(categoryOneSaved, categoryServiceImpl.getById(1).get());
		assertSame(categoryTwoSaved, categoryServiceImpl.getById(2).get());
		verify(iCategoryRepository, times(2)).findById(anyInt());
	}
	
	@Test
	void testGetCategoryByName() {
		when(iCategoryRepository.findByCategoryName("Pizza")).thenReturn(Optional.of(categoryOneSaved));
		when(iCategoryRepository.findByCategoryName("Burger")).thenReturn(Optional.of(categoryTwoSaved));
		assertSame(categoryOneSaved, categoryServiceImpl.getCategoryByName("Pizza").get());
		assertSame(categoryTwoSaved, categoryServiceImpl.getCategoryByName("Burger").get());
		verify(iCategoryRepository, times(2)).findByCategoryName(anyString());
	}
	
	@Test
	void testSearchCategory() {
		when(iCategoryRepository.findByCategoryNameLike("P")).thenReturn(List.of(categoryOneSaved));
		when(iCategoryRepository.findByCategoryNameLike("B")).thenReturn(List.of(categoryTwoSaved));
		assertEquals(List.of(categoryOneSaved), categoryServiceImpl.search("P"));
		assertEquals(List.of(categoryTwoSaved), categoryServiceImpl.search("B"));
		verify(iCategoryRepository, times(2)).findByCategoryNameLike(anyString());
	}
	
	private Category setCategoryOneToSave() {
		categoryOneToSave.setCategoryName("Pizza");
		categoryOneToSave.setCategoryDesc("Cheese Pizza");
		return categoryOneToSave;
	}
	
	private Category setCategoryTwoToSave() {
		categoryTwoToSave.setCategoryName("Burger");
		categoryTwoToSave.setCategoryDesc("Cheese Burger");
		return categoryTwoToSave;
	}
	
	private Category setCategoryOneSaved() {
		categoryOneSaved.setCategoryId(1);
		categoryOneSaved.setCategoryName("Pizza");
		categoryOneSaved.setCategoryDesc("Cheese Pizza");
		return categoryOneSaved;
	}
	
	private Category setCategoryTwoSaved() {
		categoryTwoSaved.setCategoryId(2);
		categoryTwoSaved.setCategoryName("Burger");
		categoryTwoSaved.setCategoryDesc("Cheese Burger");
		return categoryTwoSaved;
	}
	
	private Category setCategoryOneUpdated() {
		categoryOneUpdated.setCategoryId(1);
		categoryOneUpdated.setCategoryName("Pizza");
		categoryOneUpdated.setCategoryDesc("Yummy Pizza");
		return categoryOneUpdated;
	}
}
