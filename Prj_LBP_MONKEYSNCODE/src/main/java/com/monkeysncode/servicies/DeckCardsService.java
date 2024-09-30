package com.monkeysncode.servicies;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.monkeysncode.entites.Card;
import com.monkeysncode.entites.Deck;
import com.monkeysncode.entites.DeckCards;
import com.monkeysncode.repos.CardsDAO;
import com.monkeysncode.repos.DeckCardDAO;
import com.monkeysncode.repos.DeckDAO;

@Service
public class DeckCardsService {
	private final DeckDAO deckDAO;
    private final CardsDAO cardDAO;
    private final DeckCardDAO deckCardDAO;
    public DeckCardsService(DeckDAO deckDAO, CardsDAO cardsDAO, DeckCardDAO deckCardsDAO) {
        this.deckDAO = deckDAO;
        this.cardDAO = cardsDAO;
        this.deckCardDAO = deckCardsDAO;
    }
    
    //metodo per aggiungere o aggiornare una nuova relazione tra deck e carte
    public void SetCard(Long deckId, String cardId, int quantity) {
    	
    	Optional<Deck> deck = deckDAO.findById(deckId);
    	
    	if(deck.isPresent()) {
    		//controllo che la carta esista
    		Optional<Card> card = cardDAO.findById(cardId);

    		if(card.isPresent()) {
    			
    			//controllo se esiste già una associazione tra il deck e la carta
    			Optional<DeckCards> existingRelation = this.deckCardDAO.findByCardIdAndDeckId(cardId, deckId);
    			DeckCards deckCard = new DeckCards();
    			if(existingRelation.isPresent()) {
    				//se esiste aggiorno la nuova quantity
    				deckCard = existingRelation.get();
    				int newQuantity = deckCard.getCardQuantity() + quantity;
    				deckCard.setCardQuantity(newQuantity);
    			}else {
    				//se non esiste creo una nuova relazione
            		deckCard.setCard(card.get());
            		deckCard.setDeck(deck.get());
            		deckCard.setCardQuantity(quantity);
    			}

        		this.deckCardDAO.save(deckCard);
    		}
    	}
    	//bisognerà gestire l'errore per mancanza deck
    }
    
    public void RemoveCard(Long deckId, String cardId, int quantity) {
    	
    	Optional<Deck> deck = deckDAO.findById(deckId);
    	
    	if(deck.isPresent()) {
    		//controllo che la carta esista
    		Optional<Card> card = cardDAO.findById(cardId);

    		if(card.isPresent()) {
    			
    			//controllo se esiste già una associazione tra il deck e la carta
    			Optional<DeckCards> existingRelation = this.deckCardDAO.findByCardIdAndDeckId(cardId, deckId);
    			
    			if(existingRelation.isPresent()) {
    				//se esiste aggiorno la nuova quantity
    				DeckCards deckCard = existingRelation.get();
    				int newQuantity = deckCard.getCardQuantity() - quantity;
    				if(newQuantity <= 0) {
    					deckCardDAO.delete(deckCard);
    				}else {
    					deckCard.setCardQuantity(newQuantity);
    					this.deckCardDAO.save(deckCard);
    				}
    			}
    		}
    	}
    	//bisognerà gestire l'errore per mancanza deck
    }
    
    //recupero la lista di carte dato il deck ID
    public List<DeckCards> getDeckCards(Long deckId){
    	Optional<Deck> deck= this.deckDAO.findById(deckId);
    	
    	if(deck.isPresent()) {
    		return this.deckCardDAO.findByDeck(deck.get());
    	}else {
    		return new ArrayList<DeckCards>();
    	}
    	
    }
}
