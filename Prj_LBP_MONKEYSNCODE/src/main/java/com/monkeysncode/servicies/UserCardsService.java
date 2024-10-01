package com.monkeysncode.servicies;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.monkeysncode.entites.Card;
import com.monkeysncode.entites.User;
import com.monkeysncode.entites.UserCards;
import com.monkeysncode.repos.UserCardDAO;

@Service
public class UserCardsService {
	@Service
	public class UserOwnedCardsService {
		@Autowired
		private UserCardDAO dao;
		
		public List<UserCards> getUserCollection(String userId){
			return dao.findByUserId(userId);
		}
		public void addOrUpdateCard(User user,Card card,int quantity) {
			UserCards collection = dao
					.findByUserAndCard(user, card)
					.orElse(new UserCards());
			collection.setUser(user);
			collection.setCard(card);
			collection.setCardQuantity(quantity);
			
			dao.save(collection);
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
}