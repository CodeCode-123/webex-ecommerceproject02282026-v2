package com.code.api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
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
import com.code.api.entity.Users;
import com.code.api.repository.IUsersRepository;
import com.code.api.service.impl.UsersServiceImpl;

@SpringBootTest(classes=Ecommerceproject02282026v2Application.class)
public class UsersServiceTest {
	@Mock
	private IUsersRepository iUsersRepository;
	@InjectMocks
	private UsersServiceImpl usersServiceImpl;
	@Autowired
	private Users userOneToSave;
	@Autowired
	private Users userOneSaved;
	@Autowired
	private Users userOneUpdated;
	@Autowired
	private Users userTwoToSave;
	@Autowired
	private Users userTwoSaved;
	
	@BeforeEach
	public void beforeEach() {
		setUserOneToSave();
		setUserTwoToSave();
		setUserOneSaved();
		setUserTwoSaved();
	}
	
	public UsersServiceTest() {
		//This initializes the @Mock and @InjectMocks above
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	void testAddUsers() {
		when(iUsersRepository.save(userOneToSave)).thenReturn(userOneSaved);
		assertSame(userOneSaved, usersServiceImpl.add(userOneToSave));
		ArgumentCaptor<Users> captor = ArgumentCaptor.forClass(Users.class);
		verify(iUsersRepository, times(1)).save(captor.capture());
	}
	
	@Test
	void testUpdateUsers() {
		setUserOneUpdated();
		when(iUsersRepository.save(userOneUpdated)).thenReturn(userOneUpdated);
		assertSame(userOneUpdated, usersServiceImpl.update(userOneUpdated));
		ArgumentCaptor<Users> captor = ArgumentCaptor.forClass(Users.class);
		verify(iUsersRepository, times(1)).save(captor.capture());
	}
	
	@Test
	void testDeleteUsers() {
		doNothing().when(iUsersRepository).delete(userOneSaved);
		doNothing().when(iUsersRepository).delete(userTwoSaved);
		usersServiceImpl.delete(userOneSaved);
		usersServiceImpl.delete(userTwoSaved);
		ArgumentCaptor<Users> captor = ArgumentCaptor.forClass(Users.class);
		verify(iUsersRepository, times(2)).delete(captor.capture());
	}
	
	@Test
	void testDeleteById() {
		doNothing().when(iUsersRepository).deleteById(1);
		doNothing().when(iUsersRepository).deleteById(2);
		usersServiceImpl.deleteById(1);
		usersServiceImpl.deleteById(2);
		verify(iUsersRepository, times(2)).deleteById(anyInt());
	}
	
	@Test
	void testGetUserById() {
		when(iUsersRepository.findById(1)).thenReturn(Optional.of(userOneSaved));
		assertSame(userOneSaved, usersServiceImpl.getById(1).get());
		assertEquals(userOneSaved.getEmailId(), usersServiceImpl.getById(1).get().getEmailId());
		verify(iUsersRepository, times(2)).findById(anyInt());
	}
	
	@Test
	void testGetByEmailId() {
		when(iUsersRepository.findByEmailId("admin@abc.com")).thenReturn(Optional.of(userOneSaved));
		assertSame(userOneSaved, usersServiceImpl.getByEmailId("admin@abc.com").get());
		assertEquals(userOneSaved.getFirstName(), usersServiceImpl.getByEmailId("admin@abc.com").get().getFirstName());
		verify(iUsersRepository, times(2)).findByEmailId(anyString());
	}
	
	@Test
	void testGetAllUsers() {
		when(iUsersRepository.findAll()).thenReturn(List.of(userOneSaved, userTwoSaved));
		assertEquals(List.of(userOneSaved, userTwoSaved), usersServiceImpl.getAll());
		verify(iUsersRepository, times(1)).findAll();
	}
	
	private Users setUserOneToSave() {
		//set user one ToSave
		userOneToSave.setEmailId("admin@abc.com");
		userOneToSave.setFirstName("Admin");
		userOneToSave.setLastName("Admin");
		userOneToSave.setPassword("123456");
		return userOneToSave;
	}
	
	private Users setUserTwoToSave() {
		//set user two ToSave
		userTwoToSave.setEmailId("customer@abc.com");
		userTwoToSave.setFirstName("Customer");
		userTwoToSave.setLastName("Customer");
		userTwoToSave.setPassword("123456");
		return userTwoToSave;
	}
	
	private Users setUserOneSaved() {
		//set user one Saved
		userOneSaved.setUsersId(1);
		userOneSaved.setEmailId("admin@abc.com");
		userOneSaved.setFirstName("Admin");
		userOneSaved.setLastName("Admin");
		userOneSaved.setPassword("123456");
		return userOneSaved;
	}
	
	private Users setUserTwoSaved() {
		//set user two Saved
		userTwoSaved.setUsersId(2);
		userTwoSaved.setEmailId("customer@abc.com");
		userTwoSaved.setFirstName("Customer");
		userTwoSaved.setLastName("Customer");
		userTwoSaved.setPassword("123456");
		return userTwoSaved;
	}
	
	private Users setUserOneUpdated() {
		//set user one updated
		userOneUpdated.setUsersId(1);
		userOneUpdated.setEmailId("admin1@abc.com");
		userOneUpdated.setFirstName("Admin1");
		userOneUpdated.setLastName("Admin1");
		userOneUpdated.setPassword("123456");
		return userOneUpdated;
	}
}
