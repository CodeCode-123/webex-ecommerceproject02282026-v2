package com.code.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

import com.code.api.entity.Users;
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
	
	@Bean(name="users")
	@Scope(value="prototype")
	Users getUsers() {
		return new Users();
	}

}
