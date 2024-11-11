package com.crypto_wallet.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crypto_wallet.entities.User;
import com.crypto_wallet.repos.UserDAO;
@Service
public class UserServiceImp implements UserService {

	@Autowired
	private UserDAO dao;
	@Override
	public User getUserByName(String name) {
		// TODO Auto-generated method stub
		return dao.findByName(name);
	}

	@Override
	public void createNewUser(User user) {
		// TODO Auto-generated method stub
		boolean check = dao.exitsByEmail(user.getEmail());
		if(check) {
			 new RuntimeException("email gia esiste");
		}
		dao.save(user);
	}

}
