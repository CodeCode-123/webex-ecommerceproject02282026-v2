package com.code.api.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.code.api.dto.ItemOrderRequestDto;
import com.code.api.entity.ItemOrder;
import com.code.api.entity.ItemOrderDetails;
import com.code.api.entity.Users;
import com.code.api.exception.ResourceNotFoundException;
import com.code.api.service.IItemOrderDetailsService;
import com.code.api.service.IItemOrderService;
import com.code.api.service.IUsersService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/orders")
public class ItemOrderController {
	@Autowired
	private IItemOrderService iItemOrderService;
	@Autowired
	private IUsersService iUsersService;
	@Autowired
	private IItemOrderDetailsService iItemOrderDetailsService;
	
	@GetMapping("/")
	public List<ItemOrder> getAllItemOrders() {
		return iItemOrderService.getAll();
	}
	
	@GetMapping("/{id}")
	public ItemOrder getItemOrderById(@PathVariable("id") int id) {
		Optional<ItemOrder> dbItemOrder = iItemOrderService.getById(id);
		if (dbItemOrder.isEmpty()) {
			throw new ResourceNotFoundException("ItemOrder", "itemOrderId", String.valueOf(id));
		}
		return dbItemOrder.get();
	}
	
	@PostMapping("/placeorder")
	public ResponseEntity<ItemOrder> placeOrder(@Valid @RequestBody ItemOrderRequestDto itemOrderRequestDto) {
		Users users = null;
		//if dto users is not null, retrieve the users from the database by id
		if (itemOrderRequestDto.getUsers() != null && itemOrderRequestDto.getUsers().getUsersId() > 0) {
			int usersId = itemOrderRequestDto.getUsers().getUsersId();
			Optional<Users> dbUsers = iUsersService.getById(usersId);
			if (!dbUsers.isEmpty()) {
				users = dbUsers.get();
			}
		}
		//if users is null, return empty order
		ItemOrder itemOrder = new ItemOrder();
		if (users == null) {
			return ResponseEntity.ok(itemOrder);
		}
		//if users is not null, order set users, set date time
		itemOrder.setUsers(users);
		LocalDateTime nowTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		String formatterDate = nowTime.format(formatter);
		itemOrder.setItemOrderDate(formatterDate);
		//save order to the database without itemOrderDetailsList, and the totalAmount is default 0
		iItemOrderService.add(itemOrder);
		//if cartItems of dto is not null, get the information and add the objects to the list
		if (itemOrderRequestDto.getCartItems() != null && 
				itemOrderRequestDto.getCartItems().getItemOrderDetailsList() != null &&
				!itemOrderRequestDto.getCartItems().getItemOrderDetailsList().isEmpty()) {
			//create a new empty itemOrderDetailsList
			List<ItemOrderDetails> itemOrderDetailsList = new ArrayList<>();
			int itemOrderDetailsId = 0;
			Optional<ItemOrderDetails> dbItemOrderDetails;
			int qty = 0;
			double price = 0;
			double totalAmount = 0;
			//iterate the itemOrderDetailsList of the dto
			for (ItemOrderDetails details: itemOrderRequestDto.getCartItems().getItemOrderDetailsList()) {
				//get the id, and retrieve the object from the database
				itemOrderDetailsId = details.getItemOrderDetailsId();
				if (itemOrderDetailsId == 0) {
					throw new ResourceNotFoundException("ItemOrderDetails", "itemOrderDetailsId", String.valueOf(itemOrderDetailsId));
				}
				dbItemOrderDetails = iItemOrderDetailsService.getById(itemOrderDetailsId);
				//if found in the database, add to the list, calculate the totalAmount by qty and price
				if (!dbItemOrderDetails.isEmpty()) {
					ItemOrderDetails tempDetails = dbItemOrderDetails.get();
					tempDetails.setItemOrder(itemOrder);
					itemOrderDetailsList.add(tempDetails);
					qty = tempDetails.getQty();
					price = tempDetails.getItem().getItemPrice();
					totalAmount += qty * price;
				}
			}
			//itemOrder set itemOrderDetailsList, totalAmount, and save the order to the database
			itemOrder.setItemOrderDetailsList(itemOrderDetailsList);
			itemOrder.setTotalAmount(totalAmount);
			iItemOrderService.update(itemOrder);
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(itemOrder);
	}
	
	@DeleteMapping("/delete/{id}")
	public String deleteOrder(@PathVariable("id") int id) {
		//retrieve the itemOrder from the database, if not found, throw exception
		if (iItemOrderService.getById(id).isEmpty()) {
			throw new ResourceNotFoundException("ItemOrder", "itemOrderId", String.valueOf(id));
		}
		iItemOrderService.deleteById(id);
		return "Record is deleted successfully";
	}
}
