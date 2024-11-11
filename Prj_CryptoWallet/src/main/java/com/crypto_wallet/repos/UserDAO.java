package com.crypto_wallet.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crypto_wallet.entities.User;

public interface UserDAO extends JpaRepository<User, Integer> {

	
	User findByName(String name);
	boolean  exitsByEmail(String email);
	
}
