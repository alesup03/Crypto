package com.monkeysncode.repos;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.monkeysncode.entites.Card;

public interface CardsDAO extends JpaRepository<Card, String> 
{
	List<Card> findAll(Sort sort);
	
}
	

	
