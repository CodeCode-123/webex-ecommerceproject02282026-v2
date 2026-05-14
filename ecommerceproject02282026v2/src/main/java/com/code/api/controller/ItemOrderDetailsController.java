package com.code.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.code.api.dto.ItemOrderDetailsDto;
import com.code.api.entity.Item;
import com.code.api.entity.ItemOrder;
import com.code.api.entity.ItemOrderDetails;
import com.code.api.exception.ResourceNotFoundException;
import com.code.api.service.IItemOrderDetailsService;
import com.code.api.service.IItemOrderService;
import com.code.api.service.IItemService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/orderdetails")
public class ItemOrderDetailsController {
	@Autowired
	private IItemService iItemService;
	@Autowired
	private IItemOrderDetailsService iItemOrderDetailsService;
	@Autowired
	private IItemOrderService iItemOrderService;
	
	@GetMapping("/")
	public List<ItemOrderDetails> getAllItemOrderDetails() {
		return iItemOrderDetailsService.getAll();
	}
	
	@GetMapping("/{id}")
	public Optional<ItemOrderDetails> getItemOrderDetailsById(@PathVariable("id") int id) {
		if (iItemOrderDetailsService.getById(id).isEmpty()) {
			throw new ResourceNotFoundException("ItemOrderDetails", "itemOrderDetailId", String.valueOf(id));
		}
		return iItemOrderDetailsService.getById(id);
	}
	
	@PostMapping("/create")
	public ResponseEntity<ItemOrderDetails> createOrderDetails(@Valid @RequestBody ItemOrderDetailsDto itemOrderDetailsDto) {
		//generate the itemOrderDetails using itemOrderDetailsDto
		ItemOrderDetails itemOrderDetails = generateOrderDetails(itemOrderDetailsDto);
		//save the itemOrderDetails to the database and return
		iItemOrderDetailsService.add(itemOrderDetails);
		return ResponseEntity.status(HttpStatus.CREATED).body(itemOrderDetails);
	}
	
	@PutMapping("/edit")
	public ResponseEntity<ItemOrderDetails> editOrderDetails(@Valid @RequestBody ItemOrderDetailsDto itemOrderDetailsDto) {
		//if itemOrderDetails is not found in the database by id, throw exception
		int itemOrderDetailsId = itemOrderDetailsDto.getItemOrderDetailsId();
		if (iItemOrderDetailsService.getById(itemOrderDetailsId).isEmpty()) {
			throw new ResourceNotFoundException("ItemOrderDetails", "itemOrderDetailsId", String.valueOf(itemOrderDetailsId));
		}
		//if found in the database, generate the itemOrderDetails using the dto
		ItemOrderDetails itemOrderDetails = generateOrderDetails(itemOrderDetailsDto);
		itemOrderDetails.setItemOrderDetailsId(itemOrderDetailsId);
		//update the itemOrderDetails to the database and return
		iItemOrderDetailsService.update(itemOrderDetails);
		return ResponseEntity.ok(itemOrderDetails);
	}
	
	@PatchMapping("/edit/{id}")
	public ResponseEntity<ItemOrderDetails> editOrderDetailsById(@PathVariable("id") int id, 
			@Valid @RequestBody ItemOrderDetailsDto itemOrderDetailsDto) {
		//retrieve from the database, if not found, throw exception
		Optional<ItemOrderDetails> dbItemOrderDetails = iItemOrderDetailsService.getById(id);
		if (dbItemOrderDetails.isEmpty()) {
			throw new ResourceNotFoundException("ItemOrderDetails", "itemOrderDetailsId", String.valueOf(id));
		}
		//create a new itemOrderDetails, retrieve that from the database by id, if found, set the object
		ItemOrderDetails itemOrderDetails = dbItemOrderDetails.get();
		if (itemOrderDetailsDto.getItemOrder() != null && itemOrderDetailsDto.getItemOrder().getItemOrderId() > 0) {
			int itemOrderId = itemOrderDetailsDto.getItemOrder().getItemOrderId();
			Optional<ItemOrder> dbItemOrder = iItemOrderService.getById(itemOrderId);
			if (dbItemOrder.isEmpty()) {
				throw new ResourceNotFoundException("ItemOrder", "itemOrderId", String.valueOf(itemOrderId));
			}
			itemOrderDetails.setItemOrder(dbItemOrder.get());
		}
		//set the default price equals the current itemOrderDetails item price
		double price = itemOrderDetails.getItem().getItemPrice();
		//retrieve item from database by dto itemId, if found, set the object and set the price
		//if the dto changed the item or itemId, get a new price of the item from the dto
		if (itemOrderDetailsDto.getItem() != null && itemOrderDetailsDto.getItem().getItemId() > 0) {
			int itemId = itemOrderDetailsDto.getItem().getItemId();
			Optional<Item> dbItem = iItemService.getById(itemId);
			if (dbItem.isEmpty()) {
				throw new ResourceNotFoundException("Item", "itemId", String.valueOf(itemId));
			}
			itemOrderDetails.setItem(dbItem.get());
			price = dbItem.get().getItemPrice();
		}
		//set the default qty equals the current itemOrderDetails qty
		int qty = itemOrderDetails.getQty();
		//if the dto qty > 0, set the qty equals the dto qty
		if (itemOrderDetailsDto.getQty() > 0) {
			qty = itemOrderDetailsDto.getQty();
			itemOrderDetails.setQty(qty);
		}
		//calculate the itemValue
		if (qty * price > 0) {
			itemOrderDetails.setItemValue(qty * price);
		}
		//update the item and save to the database
		iItemOrderDetailsService.update(itemOrderDetails);
		return ResponseEntity.ok(itemOrderDetails);
	}
	
	@DeleteMapping("/delete/{id}")
	public String deleteOrderDetails(@PathVariable("id") int id) {
		//retrieve from the database, if not found, throw exception
		if (iItemOrderDetailsService.getById(id).isEmpty()) {
			throw new ResourceNotFoundException("ItemOrderDetails", "itemOrderDetailsId", String.valueOf(id));
		}
		//if found, delete by id
		iItemOrderDetailsService.deleteById(id);
		return "Record is deleted successfully";
	}
	
	private ItemOrderDetails generateOrderDetails(ItemOrderDetailsDto itemOrderDetailsDto) {
		//if no item in the dto, or the itemId <= 0, throw exception
		if (itemOrderDetailsDto.getItem() == null || itemOrderDetailsDto.getItem().getItemId() <= 0) {
			throw new ResourceNotFoundException("ItemOrderDetails", "item", itemOrderDetailsDto.getItem().toString());
		}
		//get itemId from dto
		int itemId = itemOrderDetailsDto.getItem().getItemId();
		//if item not found in the database by id, throw exception
		if (iItemService.getById(itemId).isEmpty()) {
			throw new ResourceNotFoundException("Item", "itemId", String.valueOf(itemId));
		}
		//if item found in the database, the details set item, qty, and value
		ItemOrderDetails itemOrderDetails = new ItemOrderDetails();
		Optional<Item> dbItem = iItemService.getById(itemId);
		itemOrderDetails.setItem(dbItem.get());
		double price = dbItem.get().getItemPrice();
		int qty = itemOrderDetailsDto.getQty();
		itemOrderDetails.setQty(qty);
		itemOrderDetails.setItemValue(qty * price);
		return itemOrderDetails;
	}
}
