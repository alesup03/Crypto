package com.monkeysncode.controllers;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.monkeysncode.entites.Deck;
import com.monkeysncode.entites.DeckCards;
import com.monkeysncode.entites.User;
import com.monkeysncode.servicies.CardService;
import com.monkeysncode.servicies.DeckCardsService;
import com.monkeysncode.servicies.DeckService;
import com.monkeysncode.servicies.UserService;

@Controller
@RequestMapping("/decks")
public class DeckController {
	@Autowired
	private  DeckCardsService deckCardsService;
	@Autowired
	private CardService cardService;
	@Autowired
	private UserService userService;
	@Autowired
	private DeckService deckService;
	
	@GetMapping("")
	public String decks(@AuthenticationPrincipal Object principal,Model model) {
		User user=userService.userCheck(principal);
	    List<Deck> decks = user.getDecks();
	    model.addAttribute("decks", decks);
	    return "Decks";
	}
	
	@GetMapping("/create")
	public String create() {
		return "create";
		
		
	}@PostMapping("/create")
	public String createPost(@AuthenticationPrincipal Object principal,@RequestParam String deckName) {
		User user=userService.userCheck(principal);
		deckService.saveOrUpdateDeck(user.getId(), deckName,Optional.empty());
		
		return "redirect:/decks";
		
		
	}
	@GetMapping("/deletedeck/{deckId}")
	public String deleteDeck(@PathVariable("deckId") Long deckId, Model model) {
		model.addAttribute("deckId",deckId);
		model.addAttribute("deck",deckService.getDeckById(deckId).get().getNameDeck());
		return "deleteDeck";
	}
	@PostMapping("/deletedeck/{deckId}")
	public String deleteDeckConfirm(@RequestParam Long deckId,@RequestParam boolean confirm) {
		if(confirm) {
			deckCardsService.deleteCardsFromDeck(deckId);
			deckService.DeleteDeck(deckId);
			
			
		}
		return "redirect:/decks";
	}
	
	
	
	@GetMapping("/yourdeck/{deckId}")
    public String viewDeck(@PathVariable("deckId") Long deckId, Model model) {
       model.addAttribute("deckId", deckId);
       model.addAttribute("cards", cardService.findALL());
       List<DeckCards> originalCards = deckCardsService.getDeckCards(deckId);
       List<DeckCards> displayCards = new ArrayList<>();
       for (DeckCards card : originalCards) {
           for (int i = 0; i < card.getCardQuantity(); i++) {
               displayCards.add(card);
           }
       }
       model.addAttribute("deckCards", displayCards);
        return "deckView";
    }
	@PostMapping("/yourdeck/addCard")
    @ResponseBody
    @Async
    public CompletableFuture<String> addCard(@RequestParam Long deckId, @RequestParam String cardId) {
        return deckCardsService.SetCard(deckId, cardId, 1);
    }
	@PostMapping("/yourdeck/removeCard")
    @ResponseBody
    @Async
    public CompletableFuture<String> removeCard(@RequestParam Long deckId, @RequestParam String cardId) {
        return deckCardsService.RemoveCard(deckId, cardId, 1);
    }
	
}
	


