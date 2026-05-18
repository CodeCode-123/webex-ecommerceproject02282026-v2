package com.code.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

import com.code.api.entity.Category;
import com.code.api.entity.Item;
import com.code.api.entity.ItemOrder;
import com.code.api.entity.ItemOrderDetails;
import com.code.api.entity.Payment;
import com.code.api.entity.Users;
import com.code.api.service.impl.CategoryServiceImpl;
import com.code.api.service.impl.ItemOrderDetailsServiceImpl;
import com.code.api.service.impl.ItemOrderServiceImpl;
import com.code.api.service.impl.ItemServiceImpl;
import com.code.api.service.impl.PaymentServiceImpl;
import com.code.api.service.impl.UsersServiceImpl;

@SpringBootApplication
public class Ecommerceproject02282026v2Application {

	public static void main(String[] args) {
		SpringApplication.run(Ecommerceproject02282026v2Application.class, args);
	}
	
	@Bean(name="usersServiceImpl")
	UsersServiceImpl getUsersServiceImpl() {
		return new UsersServiceImpl();
	}
	
	@Bean(name="categoryServiceImpl")
	CategoryServiceImpl getCategoryServiceImpl() {
		return new CategoryServiceImpl();
	}
	
	@Bean(name="itemOrderDetailsServiceImpl")
	ItemOrderDetailsServiceImpl getItemOrderDetailsServiceImpl() {
		return new ItemOrderDetailsServiceImpl();
	}
	
	@Bean(name="itemOrderServiceImpl")
	ItemOrderServiceImpl getItemOrderServiceImpl() {
		return new ItemOrderServiceImpl();
	}
	
	@Bean(name="itemServiceImpl")
	ItemServiceImpl getItemServiceImpl() {
		return new ItemServiceImpl();
	}
	
	@Bean(name="paymentServiceImpl")
	PaymentServiceImpl getPaymentServiceImpl() {
		return new PaymentServiceImpl();
	}
	
	@Bean(name="users")
	@Scope(value="prototype")
	Users getUsers() {
		return new Users();
	}
	
	@Bean(name="category")
	@Scope(value="prototype")
	Category getCategory() {
		return new Category();
	}
	
	@Bean(name="itemOrderDetails")
	@Scope(value="prototype")
	ItemOrderDetails getItemOrderDetails() {
		return new ItemOrderDetails();
	}
	
	@Bean(name="itemOrder")
	@Scope(value="prototype")
	ItemOrder getItemOrder() {
		return new ItemOrder();
	}
	
	@Bean(name="item")
	@Scope(value="prototype")
	Item getItem() {
		return new Item();
	}
	
	@Bean(name="payment")
	@Scope(value="prototype")
	Payment getPayment() {
		return new Payment();
	}
}
