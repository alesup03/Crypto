package com.monkeysncode.servicies;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.monkeysncode.entites.UserCards;
import com.monkeysncode.repos.UserCardDAO;

@Service
public class UserCardsService {
	@Autowired
	private UserCardDAO dao;
	
	
	//public List<UserCards> find(); 
	
	
	
}
