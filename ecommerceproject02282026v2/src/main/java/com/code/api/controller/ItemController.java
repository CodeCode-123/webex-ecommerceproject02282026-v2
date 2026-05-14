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

import com.code.api.dto.ItemDto;
import com.code.api.entity.Item;
import com.code.api.exception.ResourceNotFoundException;
import com.code.api.service.IItemService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/item")
public class ItemController {
	@Autowired
	private IItemService iItemService;
	
	@GetMapping("/")
	public List<Item> getAllItems() {
		return iItemService.getAll();
	}
	
	@GetMapping("/{id}")
	public Item getItemById(@PathVariable int id) {
		Optional<Item> dbItem = iItemService.getById(id);
		if (dbItem.isEmpty()) {
			throw new ResourceNotFoundException("Item", "itemId", String.valueOf(id));
		}
		return iItemService.getById(id).get();
	}
	
	@PostMapping("/create")
	public Item createItem(@Valid @RequestBody Item item) {
		return iItemService.add(item);
	}
	
	@PutMapping("/edit")
	public Item editItem(@Valid @RequestBody Item item) {
		return iItemService.update(item);
	}
	
	@PatchMapping("/edit/{id}")
	public Item editItemById(@PathVariable("id") int id, @Valid @RequestBody ItemDto itemDto) {
		Optional<Item> dbItem =iItemService.getById(id);
		if (dbItem.isEmpty()) {
			throw new ResourceNotFoundException("Item", "itemId", String.valueOf(id));
		}
		Item item = dbItem.get();
		if (itemDto.getItemName() != null && !itemDto.getItemName().isBlank()) {
			item.setItemName(itemDto.getItemName());
		}
		if (itemDto.getItemPrice() > 0) {
			item.setItemPrice(itemDto.getItemPrice());
		}
		if (itemDto.getImageData() != null && itemDto.getImageData().length > 0) {
			item.setImageData(itemDto.getImageData());
		}
		if (itemDto.getCategory() != null && itemDto.getCategory().getCategoryId() > 0) {
			item.setCategory(itemDto.getCategory());
		}
		return iItemService.update(item);
	}
	
	@DeleteMapping("/delete/{id}")
	public String deleteItem(@PathVariable("id") int id) {
		Optional<Item> dbItem = iItemService.getById(id);
		if (dbItem.isEmpty()) {
			throw new ResourceNotFoundException("Item", "itemId", String.valueOf(id));
		}
		iItemService.deleteById(id);
		return "Record is deleted successfully";
	}
}
