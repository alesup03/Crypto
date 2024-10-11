package com.monkeysncode.servicies;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.monkeysncode.entites.UserImg;
import com.monkeysncode.repos.UserImgDAO;

@Service
public class UserImgService {
	@Autowired
	UserImgDAO imgDAO;
	
	public List<UserImg> getAll() {
		return imgDAO.findAll();
	}
}
