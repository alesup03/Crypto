package com.monkeysncode.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.monkeysncode.entites.UserImg;

public interface UserImgDAO extends JpaRepository<UserImg, Long>{
	
	public List<UserImg> findAll();
}
