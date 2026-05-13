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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.code.api.dto.UsersDto;
import com.code.api.entity.Users;
import com.code.api.exception.ResourceNotFoundException;
import com.code.api.service.IUsersService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UsersController {
	@Autowired
	private IUsersService iUsersService;
	
	@GetMapping("/")
	public List<Users> getAllUsers() {
		//retrieve from the database
		return iUsersService.getAll();
	}
	
	@GetMapping("/{id}")
	public Users getUsersById(@PathVariable int id) {
		//retrieve from the database, if not found throw exception
		Optional<Users> dbUsers = iUsersService.getById(id);
		if (dbUsers.isEmpty()) {
			throw new ResourceNotFoundException("Users", "usersId", String.valueOf(id));
		}
		return dbUsers.get();
	}
	
	@GetMapping("/search/{emaiId}")
	public Users getUsersByEmailId(@PathVariable String emailId) {
		//retrieve from the database, if not found, throw exception
		Optional<Users> dbUsers = iUsersService.getByEmailId(emailId);
		if (dbUsers.isEmpty()) {
			throw new ResourceNotFoundException("Users", "emailId", emailId);
		}
		return dbUsers.get();
	}
	
	@PostMapping("/login")
	public Users usersLogin(@RequestParam("emailId") String emailId, 
			@RequestParam("password") String password) throws Exception {
		//retrieve users from the database by emailId
		Optional<Users> dbUsers = iUsersService.getByEmailId(emailId);
		//if not found, throw exception
		if (dbUsers.isEmpty()) {
			throw new ResourceNotFoundException("Users", "emailId", emailId);
		}
		//retrieve from the database by emailId and password
	    Users users = iUsersService.getByEmailId(emailId, password);
	    if (users == null) {
	    	throw new Exception("Password does not match");
	    }
	    return users;
	}
	
	@PostMapping("/create")
	public Users createUsers(@RequestBody Users users) {
		//create the users and save to the database
		return iUsersService.add(users);
	}
	
	@PutMapping("/edit")
	public Users editUsers(@RequestBody Users users) {
		//retrieve from the database by id
		Optional<Users> dbUsers = iUsersService.getById(users.getUsersId());
		//if not found, throw exception
		if (dbUsers.isEmpty()) {
			throw new ResourceNotFoundException("Users", "usersId", String.valueOf(users.getUsersId()));
		}
		//if found, update the users and save to the database
		iUsersService.update(users);
		return users;
	}
	
	@PatchMapping("/edit/{id}")
	public Users editUsersById(@PathVariable("id") int id, @Valid @RequestBody UsersDto usersDto) {
		//retrieve from the database
		Optional<Users> dbUsers = iUsersService.getById(id);
		if (dbUsers.isEmpty()) {
			throw new ResourceNotFoundException("Users", "usersId", String.valueOf(id));
		}
		//create new users, and set each field if the field got from dto is not empty
		Users users = new Users();
		if (!usersDto.getFirstName().isBlank()) {
			users.setFirstName(usersDto.getFirstName());
		}
		if (!usersDto.getLastName().isBlank()) {
			users.setLastName(usersDto.getLastName());
		}
		if (!usersDto.getGender().isBlank()) {
			users.setGender(usersDto.getGender());
		}
		if (!usersDto.getCountry().isBlank()) {
			users.setCountry(usersDto.getCountry());
		}
		if (usersDto.getLanguages() != null && usersDto.getLanguages().length > 0) {
			users.setLanguages(usersDto.getLanguages());
		}
		if (usersDto.getImageData() != null && usersDto.getImageData().length > 0) {
			users.setImageData(usersDto.getImageData());
		}
		//update the users;
		return iUsersService.update(users);
	}
	
	@DeleteMapping("/delete/{id}")
	public String deleteUsers(@PathVariable("id") int id) {
		Optional<Users> dbUsers = iUsersService.getById(id);
		if (dbUsers.isEmpty()) {
			throw new ResourceNotFoundException("Users", "usersId", String.valueOf(id));
		}
		iUsersService.deleteById(id);
		return "Record is deleted successfully";
	}
}
