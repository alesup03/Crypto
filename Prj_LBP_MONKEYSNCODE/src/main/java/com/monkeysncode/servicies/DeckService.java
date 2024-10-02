package com.monkeysncode.servicies;

import java.util.Optional;
import org.springframework.stereotype.Service;
import com.monkeysncode.entites.Deck;
import com.monkeysncode.entites.User;
import com.monkeysncode.repos.DeckDAO;
import com.monkeysncode.repos.UserDAO;

@Service
public class DeckService {
	
	private final UserDAO userDAO;
    private final DeckDAO deckDAO;
    public DeckService(UserDAO userDAO, DeckDAO deckDAO) {
        this.deckDAO = deckDAO;
        this.userDAO = userDAO;
    }

    public void saveOrUpdateDeck(String userId, String nameDeck, Optional<Long> deckId) {
    	
    	// Recupera l'utente dal database
        User user = userDAO.findById(userId).orElseThrow(() -> new RuntimeException("User non trovato"));

        if (deckId.isEmpty()) {
            // Se non viene fornito un ID del deck, crea un nuovo deck
            Deck newDeck = new Deck();
            newDeck.setNameDeck(nameDeck);
            newDeck.setUser(user); 
            deckDAO.save(newDeck);
        } else {
            // Se viene fornito un ID del deck, aggiorna il deck esistente
            Deck existingDeck = deckDAO.findById(deckId.get())
                .orElseThrow(() -> new RuntimeException("Deck non trovato"));
            
            existingDeck.setNameDeck(nameDeck); 
            deckDAO.save(existingDeck);
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
}
