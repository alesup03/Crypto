package com.crypto_wallet.services;

import com.crypto_wallet.entities.User;
//Classe astratta che difinsci metodi per User
public interface UserService {
	//metodo per cercare utente con il nome
	User getUserByName(String name);
	//metodo per creazione del utente nuovi 
	void createNewUser(User user);

}
