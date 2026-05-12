package com.code.api.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.code.api.entity.Users;

@Repository
public interface IUsersRepository extends JpaRepository<Users, Integer>{
	Optional<Users> findByEmailId(String emailId);
}