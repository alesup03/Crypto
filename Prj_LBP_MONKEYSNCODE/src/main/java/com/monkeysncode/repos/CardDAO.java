package com.monkeysncode.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.monkeysncode.entites.Card;

public interface CardDAO extends JpaRepository<Card, String>{
	public List<Card> findAll();

}
