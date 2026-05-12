package com.code.api.service;

import java.util.List;
import java.util.Optional;
import com.code.api.entity.Users;

public interface IUsersService {
	Users add(Users users);
	Users update(Users users);
	void delete(Users users);
	void deleteById(int userId);
	Optional<Users> getById(int userId);
	Optional<Users> getByEmailId(String emailId);	
	Users getByEmailId(String emailId, String password);
	List<Users> getAll();
}
