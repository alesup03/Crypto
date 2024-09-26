package com.monkeysncode.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.monkeysncode.entites.Deck;

public interface DeckDAO extends JpaRepository<Deck, Long>{
	public List<Deck> findAll();

}
