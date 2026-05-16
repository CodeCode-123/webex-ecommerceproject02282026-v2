package com.code.api.service.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.code.api.dto.LoginDto;
import com.code.api.dto.RegisterDto;
import com.code.api.entity.Users;
import com.code.api.repository.IUsersRepository;

@Service
@Transactional
public class AuthenticationService {
	private final IUsersRepository iUsersRepository;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	
	public AuthenticationService(
			IUsersRepository iUsersRepository, 
			PasswordEncoder passwordEncoder,
			AuthenticationManager authenticationManager) {
		this.iUsersRepository = iUsersRepository;
		this.passwordEncoder = passwordEncoder;
		this.authenticationManager = authenticationManager;
	}
	
	public Users signup(RegisterDto input) {
		Users customer = new Users();
		customer.setFirstName(input.getFirstName());
		customer.setLastName(input.getLastName());
		customer.setEmailId(input.getEmail());
		customer.setPassword(passwordEncoder.encode(input.getPassword()));
		return iUsersRepository.save(customer);
	}
	
	public Users authenticate(LoginDto input) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						input.getEmail(),
						input.getPassword()));
		return iUsersRepository.findByEmailId(input.getEmail())
				.orElseThrow();
	}
}
