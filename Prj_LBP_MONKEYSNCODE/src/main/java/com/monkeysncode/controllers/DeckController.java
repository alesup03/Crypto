package com.monkeysncode.controllers;


import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.hibernate.Length;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.monkeysncode.entites.User;
import com.monkeysncode.servicies.CardService;
import com.monkeysncode.servicies.DeckCardsService;

@Controller
@RequestMapping("/deck")
public class DeckController {
	@Autowired
	private  DeckCardsService deckCardsService;
	@Autowired
	private CardService cardService;
	
	@GetMapping("/")
	public String decks(@AuthenticationPrincipal User user,Model model) {
		List<Deck> decks=user.getDecks();
		for(int i=0;i<decks.size();i++) {
			model.addAttribute("decks"+i,decks);//aggiungere anche l'immagine da mandare nel caso ci sia il deck
		}
		return "decks";
	}
	
	@PostMapping("/create")
	public String createDeck(@AuthenticationPrincipal User user,@RequestParam String deckName) {
		
		
	}
	
	@GetMapping("/{deckId}")
    public String viewDeck(@PathVariable("deckId") Long deckId, Model model) {
        model.addAttribute("deckId", deckId);
        model.addAttribute("cards", cardService.findALL());
        model.addAttribute("deckCards", deckCardsService.getDeckCards(deckId)); // Carte attualmente nel mazzo
        return "deckView"; // Nome del template Thymeleaf
    }
	@PostMapping("/addCard")
    @ResponseBody
    public CompletableFuture<String> addCard(@RequestParam Long deckId, @RequestParam String cardId) {
        return deckCardsService.SetCard(deckId, cardId, 1); // Aggiunge 1 carta al mazzo
    }
	 @PostMapping("/removeCard")
	    @ResponseBody
	    public CompletableFuture<String> removeCard(@RequestParam Long deckId, @RequestParam String cardId) {
	        return deckCardsService.RemoveCard(deckId, cardId, 1); // Rimuove 1 carta dal mazzo
	    }
	
}
	


