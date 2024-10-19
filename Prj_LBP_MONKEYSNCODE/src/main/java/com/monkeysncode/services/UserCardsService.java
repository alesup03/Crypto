package com.monkeysncode.servicies;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.monkeysncode.entites.Card;
import com.monkeysncode.entites.User;
import com.monkeysncode.entites.UserCards;
import com.monkeysncode.repos.CardsDAO;
import com.monkeysncode.repos.UserCardDAO;

@Service
public class UserCardsService {
		@Autowired
		private UserCardDAO dao;
		
		public int getTotalCards(String userId) {
		    List<UserCards> userCards = dao.findByUserId(userId);

		    if (userCards == null || userCards.isEmpty()) {
		        return 0; 
		    }

		    return userCards.stream().mapToInt(UserCards::getCardQuantity).sum();
		}
		
		public List<UserCards> getUserCollection(String userId){
			return dao.findByUserId(userId);
		}
	
		//ritorna una lista di id delle carte possedute dall'utente
		public List<String> getCollectionById(String userId){
			List<UserCards> userCards = dao.findByUserId(userId);
			List<String> ids = new ArrayList<String>();
			for (UserCards userCard : userCards) {
				ids.add(userCard.getCard().getId());
			}
			return ids;
		}
		//ritorna la lista delle carte possedute, ordinata per un certo parametro
		public List<Card> getSortedCollection(String userId, String sort, boolean desc){
			List<UserCards> userCards = dao.findByUserId(userId);
			List<Card> cards = new ArrayList<Card>();
			for (UserCards userCard : userCards) {
				cards.add(userCard.getCard());
			}
			cards = sortByParam(cards,sort,desc);
			return cards;
			
		}
		
		public List<Card> sortByParam(List<Card> cards, String sortBy, boolean desc) {
	        Comparator<Card> comparator;

	        // Seleziona il comparatore in base all'attributo sortBy
	        switch (sortBy) {
	            case "name":
	                comparator = Comparator.comparing(Card::getName);
	                break;
	            case "level":
	            	comparator = Comparator.comparingInt(card -> {
	                    String level = card.getLevel();
	                    // If the level is empty or invalid, use a default value (e.g., 0)
                    	if(level.isEmpty() && (card.getSupertypes().equals("Trainer") || card.getSupertypes().equals("Energy") ))
                    		return 0;
                    	else {
                    		if(level.isEmpty())
                    			return 1;
                    		if(level.equalsIgnoreCase("X"))
                    			return 1000;
                    	}
                        return Integer.parseInt(level);
	                    }); 
	            	comparator = comparator.reversed();
	                break;
	            case "nationalPokedexNumbers":
	                comparator = Comparator.comparingInt(card -> {
	                    String nationalPokedexNumbers = card.getNationalPokedexNumbers();
	                    // If the level is empty or invalid, use a default value (e.g., 0)
                    	if(nationalPokedexNumbers.isEmpty() && (card.getSupertypes().equals("Trainer") || card.getSupertypes().equals("Energy") ))
                    		return 1000;
                        return Integer.parseInt(nationalPokedexNumbers);
	                    }); 
	                break;
	            case "rarity":
	                comparator = Comparator.comparing(Card::getRarity);
	                break;
	            default:
	                throw new IllegalArgumentException("Invalid sort attribute: " + sortBy);
	        }

	        // Ordina in base al flag desc
	        if (desc) {
	            comparator = comparator.reversed();
	        }

	        Collections.sort(cards, comparator);
	        return cards;
	    }
	
		
		public void addOrUpdateCard(User user,Card card,int quantity) {
			UserCards collection = dao
					.findByUserAndCard(user, card)
					.orElse(new UserCards());
			collection.setUser(user);
			collection.setCard(card);
			collection.setCardQuantity(quantity);
			
			dao.save(collection);
			cleanDb(user.getId());
		}
		
		public void addOrRemoveCard(User user,Card card,int quantity) {
			UserCards collection = dao
					.findByUserAndCard(user, card)
					.orElse(new UserCards());
			int prevQuantity = collection.getCardQuantity();
			collection.setUser(user);
			collection.setCard(card);
			collection.setCardQuantity(prevQuantity + quantity);
			System.out.println("ok");
			dao.save(collection);
			cleanDb(user.getId());
		}
		
		public void cleanDb(String userId) {
			List<UserCards> lista=dao.findByUserId(userId);
			for (UserCards userCards : lista) {
				if(userCards.getCardQuantity()<=0) {
					dao.delete(userCards);
				}
			}
		}
		public int getQuantityByCardUser(User user, Card card) {
		    Optional<UserCards> userCards = dao.findByUserAndCard(user, card);
		    if (userCards.isPresent()) {
		        return userCards.get().getCardQuantity();
		    } else {
		        return 0;
		    }
		}
	
	

	}
