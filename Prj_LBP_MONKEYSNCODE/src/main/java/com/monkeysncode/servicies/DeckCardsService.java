package com.monkeysncode.servicies;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.monkeysncode.entites.Card;
import com.monkeysncode.entites.Deck;
import com.monkeysncode.entites.DeckCards;
import com.monkeysncode.repos.CardsDAO;
import com.monkeysncode.repos.DeckCardDAO;
import com.monkeysncode.repos.DeckDAO;
import java.util.Map;

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
    @Async
    public CompletableFuture<String> SetCard(Long deckId, String cardId, int quantity) {
    	
    	Optional<Deck> deck = deckDAO.findById(deckId);
    	
    	if(deck.isPresent()) {
    		//controllo che la carta esista
    		Optional<Card> card = cardDAO.findById(cardId);

    		if(card.isPresent()) {
    			
    			//controllo se esiste già una associazione tra il deck e la carta
    			Optional<DeckCards> existingRelation = deckCardDAO.findByCardIdAndDeckId(cardId, deckId);
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
        		return CompletableFuture.completedFuture("Carta aggiunta con successo!");
    		}
    	}
    	
    	return CompletableFuture.completedFuture("Errore Mazzo!");
    }
    @Async
    public CompletableFuture<String> RemoveCard(Long deckId, String cardId, int quantity) {
    	
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
    		return CompletableFuture.completedFuture("Carta rimossa con successo!");
    	}
    	return CompletableFuture.completedFuture("Errore Mazzo!");
    	//bisognerà gestire l'errore per mancanza deck
    }
    
    public boolean validateDeck(Long deckId) {
        List<DeckCards> deckCards = getDeckCards(deckId);
        
        // Check total number of cards
        if (deckCards.size() > 60) {
            return false; // Exceeds maximum card limit
        }

        // Initialize counters
        int basicPokemonCount = 0;
        int energyCardCount = 0;
        int trainerCardCount = 0;
        Map<String, Integer> cardCountMap = new HashMap<>();

        for (DeckCards deckCard : deckCards) {
            Card card = deckCard.getCard();
            String cardType = card.getTypes(); // Assuming Card has a getType() method
            
            // Count different card types
            if (cardType.equals("Basic Pokemon")) {
                basicPokemonCount++;
            } else if (cardType.equals("Energy")) {
                energyCardCount++;
            } else if (cardType.equals("Trainer")) {
                trainerCardCount++;
            }
            
            // Count duplicates
            String cardName = card.getName();
            cardCountMap.put(cardName, cardCountMap.getOrDefault(cardName, 0) + deckCard.getCardQuantity());
        }

        // Validate rules
        boolean hasBasicPokemon = basicPokemonCount > 0;
        boolean sufficientEnergy = energyCardCount >= 12 && energyCardCount <= 15; // Adjust based on your requirements
        boolean sufficientTrainers = trainerCardCount >= 20 && trainerCardCount <= 25; // Adjust based on your requirements
        boolean noExcessCopies = cardCountMap.values().stream().allMatch(count -> count <= 4);

        return hasBasicPokemon && sufficientEnergy && sufficientTrainers && noExcessCopies;
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
