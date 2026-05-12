package com.code.api.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.code.api.entity.Users;
import com.code.api.repository.IUsersRepository;
import com.code.api.service.IUsersService;

@Service
@Transactional
public class UsersServiceImpl implements IUsersService {
	@Autowired
	private IUsersRepository iUsersRepository;

	@Override
	public Users add(Users users) {
		return iUsersRepository.save(users);
	}

	@Override
	public Users update(Users users) {
		return iUsersRepository.save(users);
	}

	@Override
	public void delete(Users users) {
		iUsersRepository.delete(users);
	}

	@Override
	public void deleteById(int userId) {
		iUsersRepository.deleteById(userId);
	}

	@Override
	public Optional<Users> getById(int userId) {
		return iUsersRepository.findById(userId);
	}

	@Override
	public Optional<Users> getByEmailId(String emailId) {
		return iUsersRepository.findByEmailId(emailId);
	}

	@Override
	public Users getByEmailId(String emailId, String password) {
		Optional<Users> users = iUsersRepository.findByEmailId(emailId);
		if (!users.isEmpty() && users.get().getPassword().equals(password)) {
			return users.get();
		}
		return null;
	}

	@Override
	public List<Users> getAll() {
		return iUsersRepository.findAll();
	}
}
