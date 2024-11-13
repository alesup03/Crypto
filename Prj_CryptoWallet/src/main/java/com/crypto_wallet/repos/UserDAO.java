package com.crypto_wallet.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crypto_wallet.entities.User;

public interface UserDAO extends JpaRepository<User, Integer> {

  //metodo per cercare utente con il nome	
	User findByName(String name);
	User findById(int id);
	boolean  exitsByEmail(int user_id); // ricerca se l'emil gia esiste o no
	
}
