package com.monkeysncode.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.monkeysncode.entites.User;
import com.monkeysncode.entites.Card;
import com.monkeysncode.servicies.CardService;
import com.monkeysncode.servicies.UserCardsService;
import com.monkeysncode.servicies.UserService;

@Controller
public class UserCardController {  // Controller who manages the card quantity
	
    @Autowired
    private UserService userService;
    
	@Autowired
	private UserCardsService usercardsService;
	
	@Autowired
	private CardService cardService;
	
	// Endpoint to set the quantity of a card within a user's collection
	@PostMapping("/userCard")
	public String addCardToUser(@AuthenticationPrincipal Object principal, @RequestParam String cardId, @RequestParam int quantity){
		User user = userService.userCheck(principal);
		Optional<Card> card = cardService.getCardById(cardId);
		if(card.isPresent()) {
			usercardsService.addOrUpdateCard(user, card.get(), quantity);
			return "redirect:/card/" + card.get().getId();
		}else {
			return "error";
		}
		
	}
	
	@GetMapping("/users/{userId}/totalCards")
    public Integer getTotalCards(@PathVariable String userId) {
        return usercardsService.getTotalCards(userId);
    }
}
