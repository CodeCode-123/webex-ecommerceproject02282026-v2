package com.code.api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.anyInt;
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
import com.code.api.entity.Item;
import com.code.api.entity.ItemOrderDetails;
import com.code.api.repository.IItemOrderDetailsRepository;
import com.code.api.service.impl.ItemOrderDetailsServiceImpl;

@SpringBootTest(classes=Ecommerceproject02282026v2Application.class)
public class ItemOrderDetailsServiceTest {
	@Mock
	private IItemOrderDetailsRepository iItemOrderDetailsRepository;
	@InjectMocks
	private ItemOrderDetailsServiceImpl itemOrderDetailsServiceImpl;
	@Autowired
	private Category categoryOne;
	@Autowired
	private Category categoryTwo;
	@Autowired
	private Item itemOne;
	@Autowired
	private Item itemTwo;
	@Autowired
	private ItemOrderDetails itemOrderDetailsOneToSave;
	@Autowired
	private ItemOrderDetails itemOrderDetailsTwoToSave;
	@Autowired
	private ItemOrderDetails itemOrderDetailsOneSaved;
	@Autowired
	private ItemOrderDetails itemOrderDetailsTwoSaved;
	@Autowired
	private ItemOrderDetails itemOrderDetailsOneUpdated;
	
	public ItemOrderDetailsServiceTest() {
		MockitoAnnotations.openMocks(this);
	}
	
	@BeforeEach
	public void beforeEach() {
		setCategoryOne();
		setCategoryTwo();
		setItemOne();
		setItemTwo();
		setItemOrderDetailsOneToSave();
		setItemOrderDetailsTwoToSave();
		setItemOrderDetailsOneSaved();
		setItemOrderDetailsTwoSaved();
	}
	
	@Test
	void testAddItemOrderDetails() {
		when(iItemOrderDetailsRepository.save(itemOrderDetailsOneToSave)).thenReturn(itemOrderDetailsOneSaved);
		when(iItemOrderDetailsRepository.save(itemOrderDetailsTwoToSave)).thenReturn(itemOrderDetailsTwoSaved);
		assertSame(itemOrderDetailsOneSaved, itemOrderDetailsServiceImpl.add(itemOrderDetailsOneToSave));
		assertSame(itemOrderDetailsTwoSaved, itemOrderDetailsServiceImpl.add(itemOrderDetailsTwoToSave));
		ArgumentCaptor<ItemOrderDetails> captor = ArgumentCaptor.forClass(ItemOrderDetails.class);
		verify(iItemOrderDetailsRepository, times(2)).save(captor.capture());
	}
	
	@Test
	void testUpdateItemOrderDetails() {
		setItemOrderDetailsOneUpdated();
		when(iItemOrderDetailsRepository.save(itemOrderDetailsOneUpdated)).thenReturn(itemOrderDetailsOneUpdated);
		assertSame(itemOrderDetailsOneUpdated, itemOrderDetailsServiceImpl.update(itemOrderDetailsOneUpdated));
		ArgumentCaptor<ItemOrderDetails> captor = ArgumentCaptor.forClass(ItemOrderDetails.class);
		verify(iItemOrderDetailsRepository, times(1)).save(captor.capture());
	}

	@Test
	void testDeleteItemOrderDetails() {
		doNothing().when(iItemOrderDetailsRepository).delete(itemOrderDetailsOneSaved);
		doNothing().when(iItemOrderDetailsRepository).delete(itemOrderDetailsTwoSaved);
		itemOrderDetailsServiceImpl.delete(itemOrderDetailsOneSaved);
		itemOrderDetailsServiceImpl.delete(itemOrderDetailsTwoSaved);
		ArgumentCaptor<ItemOrderDetails> captor = ArgumentCaptor.forClass(ItemOrderDetails.class);
		verify(iItemOrderDetailsRepository, times(2)).delete(captor.capture());
	}
	
	@Test
	void testDeleteItemOrderDetailsById() {
		doNothing().when(iItemOrderDetailsRepository).deleteById(1);
		doNothing().when(iItemOrderDetailsRepository).deleteById(2);
		itemOrderDetailsServiceImpl.deleteById(1);
		itemOrderDetailsServiceImpl.deleteById(2);
		verify(iItemOrderDetailsRepository, times(2)).deleteById(anyInt());
	}
	
	@Test
	void testGetAllItemOrderDetails() {
		when(iItemOrderDetailsRepository.findAll()).thenReturn(List.of(itemOrderDetailsOneSaved, itemOrderDetailsTwoSaved));
		assertEquals(List.of(itemOrderDetailsOneSaved, itemOrderDetailsTwoSaved), itemOrderDetailsServiceImpl.getAll());
		verify(iItemOrderDetailsRepository, times(1)).findAll();
	}
	
	@Test
	void testGetItemOrderDetailsById() {
		when(iItemOrderDetailsRepository.findById(1)).thenReturn(Optional.of(itemOrderDetailsOneSaved));
		when(iItemOrderDetailsRepository.findById(2)).thenReturn(Optional.of(itemOrderDetailsTwoSaved));
		assertSame(itemOrderDetailsOneSaved, itemOrderDetailsServiceImpl.getById(1).get());
		assertSame(itemOrderDetailsTwoSaved, itemOrderDetailsServiceImpl.getById(2).get());
		verify(iItemOrderDetailsRepository, times(2)).findById(anyInt());
	}
	
	@Test
	void testGetItemOrderDetailsAndItemById() {
		when(iItemOrderDetailsRepository.findItemOrderDetailsAndItemById(1)).thenReturn(Optional.of(itemOrderDetailsOneSaved));
		when(iItemOrderDetailsRepository.findItemOrderDetailsAndItemById(2)).thenReturn(Optional.of(itemOrderDetailsTwoSaved));
		assertSame(itemOrderDetailsOneSaved, itemOrderDetailsServiceImpl.getItemOrderDetailsAndItemById(1).get());
		assertSame(itemOrderDetailsTwoSaved, itemOrderDetailsServiceImpl.getItemOrderDetailsAndItemById(2).get());
		verify(iItemOrderDetailsRepository, times(2)).findItemOrderDetailsAndItemById(anyInt());
	}
	
	private Category setCategoryOne() {
		// set category one
		categoryOne.setCategoryId(1);
		categoryOne.setCategoryName("Pizza");
		categoryOne.setCategoryDesc("Any Pizza, any toppings");
		return categoryOne;
	}
	
	private Category setCategoryTwo() {
		// set category two
		categoryTwo.setCategoryId(2);
		categoryTwo.setCategoryName("Burger");
		categoryTwo.setCategoryDesc("Best Price");
		return categoryTwo;
	}
	
	private Item setItemOne() {
		// set item one
		itemOne.setItemId(1);
		itemOne.setCategory(categoryOne);
		itemOne.setItemName("Cheese Pizza");
		itemOne.setItemPrice(10);
		return itemOne;
	}
	
	private Item setItemTwo() {
		// set item two
		itemTwo.setItemId(2);
		itemTwo.setCategory(categoryTwo);
		itemTwo.setItemName("Big Mac");
		itemTwo.setItemPrice(8);
		return itemTwo;
	}
	
	private ItemOrderDetails setItemOrderDetailsOneToSave() {
		// set item order details one ToSave
		int qty = 2;
		itemOrderDetailsOneToSave.setItem(itemOne);
		itemOrderDetailsOneToSave.setQty(qty);
		itemOrderDetailsOneToSave.setItemValue(itemOne.getItemPrice() * qty);
		return itemOrderDetailsOneToSave;
	}
	
	private ItemOrderDetails setItemOrderDetailsTwoToSave() {
		// set item order details two ToSave
		int qty = 5;
		itemOrderDetailsTwoToSave.setItem(itemTwo);
		itemOrderDetailsTwoToSave.setQty(qty);
		itemOrderDetailsTwoToSave.setItemValue(itemTwo.getItemPrice() * qty);
		return itemOrderDetailsTwoToSave;
	}
	
	private ItemOrderDetails setItemOrderDetailsOneSaved() {
		// set item order details one Saved
		int qty = 2;
		itemOrderDetailsOneSaved.setItemOrderDetailsId(1);
		itemOrderDetailsOneSaved.setItem(itemOne);
		itemOrderDetailsOneSaved.setQty(qty);
		itemOrderDetailsOneSaved.setItemValue(itemOne.getItemPrice() * qty);
		return itemOrderDetailsOneSaved;
	}
	
	private ItemOrderDetails setItemOrderDetailsTwoSaved() {
		// set item order details two ToSave
		int qty = 5;
		itemOrderDetailsTwoSaved.setItemOrderDetailsId(2);
		itemOrderDetailsTwoSaved.setItem(itemTwo);
		itemOrderDetailsTwoSaved.setQty(qty);
		itemOrderDetailsTwoSaved.setItemValue(itemTwo.getItemPrice() * qty);		
		return itemOrderDetailsTwoSaved;
	}
	
	private ItemOrderDetails setItemOrderDetailsOneUpdated() {
		int qty = 3;
		itemOrderDetailsOneUpdated.setItemOrderDetailsId(1);
		itemOrderDetailsOneUpdated.setItem(itemOne);
		itemOrderDetailsOneUpdated.setQty(qty);
		itemOrderDetailsOneUpdated.setItemValue(itemOne.getItemPrice() * qty);
		return itemOrderDetailsOneUpdated;
	}
}
