package com.monkeysncode.repos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.monkeysncode.entites.Deck;
import com.monkeysncode.entites.DeckCards;

public interface DeckCardDAO extends JpaRepository<DeckCards, Long>{
	public List<DeckCards> findByDeck(Deck deck);
	
	public Optional<DeckCards> findByCardIdAndDeckId(String cardId, Long deckId);
	
}
