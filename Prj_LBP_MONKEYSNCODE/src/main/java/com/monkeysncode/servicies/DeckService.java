package com.monkeysncode.servicies;

import java.util.Optional;

import org.springframework.stereotype.Service;
import com.monkeysncode.entites.Deck;

import com.monkeysncode.entites.User;

import com.monkeysncode.repos.DeckDAO;



@Service
public class DeckService {

    private final DeckDAO deckDAO;
    public DeckService(DeckDAO deckDAO) {
        this.deckDAO = deckDAO;
    }

    public void saveOrUpdateDeck(Deck deck, Optional<Long> deckId) {
    	
    	//se mi viene passato il deck_id aggiorno il deck
    	if(deckId.isEmpty()){
    		
    		Deck NewDeck = new Deck();
    		NewDeck.setNameDeck(deck.getNameDeck());
    		deckDAO.save(deck);
    		
    	} else {
    		// se il deck_id non mi viene passato creo il deck
    		Optional<Deck> userDecks = deckDAO.findById(deckId.get());
    		// check if user have the deckId in his collection
    		if(!userDecks.isEmpty()) {
    			userDecks.get().setNameDeck(deck.getNameDeck());
    			deckDAO.save(userDecks.get());
    		}
    		
    	}
    }
    
    
    //cancello il deck dato il suo id

    public boolean DeleteDeck(Long deckId) {
    	Optional<Deck> deck = this.deckDAO.findById(deckId);
    	if(deck.isPresent()) {
        	this.deckDAO.delete(deck.get());
        	return true;
    	}
    	return false;
    }



    public Optional<Deck> getDeckById(Long id) {
        return deckDAO.findById(id);
    }

    public Optional<Deck> getDeckByNameDeck(User user,String nameDeCk) {
        return deckDAO.findByUserAndNameDeck(user,nameDeCk);
    }
}
