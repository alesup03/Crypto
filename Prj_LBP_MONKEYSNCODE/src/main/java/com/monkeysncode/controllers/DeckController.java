package com.monkeysncode.controllers;

import java.util.ArrayList;
import java.util.HashMap;
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

import com.monkeysncode.entites.Card;
import com.monkeysncode.entites.Deck;
import com.monkeysncode.entites.DeckCards;
import com.monkeysncode.entites.DeckImg;
import com.monkeysncode.entites.User;
import com.monkeysncode.services.CardService;
import com.monkeysncode.services.DeckCardsService;
import com.monkeysncode.services.DeckImgService;
import com.monkeysncode.services.DeckService;
import com.monkeysncode.services.UserCardsService;
import com.monkeysncode.services.UserService;

@Controller
@RequestMapping("/decks")
public class DeckController { // Controller who manages the user deck
    @Autowired
    private DeckCardsService deckCardsService;
    
    @Autowired
    private UserCardsService usercardService;
    
    @Autowired
    private CardService cardService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private DeckService deckService;
    
    @Autowired
    private DeckImgService deckImgService;
    
    @GetMapping("")
    public String decks(@AuthenticationPrincipal Object principal, Model model) {
    	
        User user = userService.userCheck(principal);
        List<Deck> decks = user.getDecks();
        
        // Validation for each deck
        for (Deck deck : decks) {
            String validationResult = deckCardsService.validateDeck(deck.getId(), user);
            
            // If the deck is valid, we set valid to true, otherwise to false
            if (validationResult.contains("Il mazzo è valido!")) {
                deck.setValid(true); // Validate deck
            } else {
                deck.setValid(false); // Invalidate deck
            }
        }

        model.addAttribute("decks", decks);
        return "Decks"; 
    }

    @GetMapping("/create")
    public String create(Model model) {
    	List<DeckImg> images = deckImgService.getAll();
    	model.addAttribute("images", images);
        return "create";
    }

    @PostMapping("/create")
    public String createPost(@AuthenticationPrincipal Object principal, @RequestParam String deckName, @RequestParam Long deckImgId) {
    	
        User user = userService.userCheck(principal);
        
        // Retrieve the selected image by its ID from the DAO
        Optional<DeckImg> selectedImg = deckImgService.getDeckImgById(deckImgId);
        
        //Create user deck based on image and name, required and deck id optional
        deckService.saveOrUpdateDeck(user.getId(), deckName, Optional.empty(), selectedImg);
        
        return "redirect:/decks";
    }

    @GetMapping("/deletedeck/{deckId}")
    public String deleteDeck(@PathVariable("deckId") Long deckId, Model model) {
    	model.addAttribute("deckId", deckId);
        model.addAttribute("deck", deckService.getDeckById(deckId).get().getNameDeck());
        return "deleteDeck";
    }

    @PostMapping("/deletedeck/{deckId}")
    public String deleteDeckConfirm(@RequestParam Long deckId, @RequestParam boolean confirm) {
    	
    	//If you press the confirm button, the deck and the cards inside are eliminated
        if (confirm) {
            deckCardsService.deleteCardsFromDeck(deckId);
            deckService.DeleteDeck(deckId);
        }
        return "redirect:/decks";
    }
    
    @GetMapping("/yourdeck/{page}/{deckId}")
    public String viewDeck(
    		@AuthenticationPrincipal Object principal,
    		Model model,
    		@PathVariable("deckId") Long deckId,
            @PathVariable int page,
            @RequestParam(defaultValue = "false") boolean owned,
            @RequestParam(required = false) String set,
	        @RequestParam(required = false) String types,
	        @RequestParam(required = false) String name,
	        @RequestParam(required = false) String rarity,
	        @RequestParam(required = false) String supertype,
	        @RequestParam(required = false) String subtypes,
	        @RequestParam(required = false,defaultValue = "name") String sort,
	        @RequestParam(defaultValue = "false") boolean desc,
	        @RequestParam(defaultValue = "1") int blocco) {
    	
    	if(blocco < 1) {
	    	blocco = 1;
	    }
    	
    	User user = userService.userCheck(principal);
    	
    	HashMap<String, String> param = new HashMap<String, String>();
	 	param.put("set", set); // Associate the value with the map key
	 	param.put("types", types);
	 	param.put("name", name);
	 	param.put("rarity", rarity);
	 	param.put("supertype", supertype);
	 	param.put("subtypes", subtypes);
	 	
		List<Card> cards = new ArrayList<Card>();
		
		// Based on the owned parameter it takes the list from two different services
	 	if(owned == true)
	 		cards = cardService.filterByParam(param, usercardService.getSortedCollection(user.getId(),sort,desc));
	 	else cards = cardService.filterByParam(param, cardService.findAllSorted(sort,desc));
    	
    	List<Card> allCards = cardService.getCardsByPage(cards, page, 132);

    	int totPages=(int) Math.ceil((double) cards.size() / 132);
    	
    	// Page block management (15 pages per block)
	    int bloccoDimensione = 5;
	    int inizioPagina = (blocco - 1) * bloccoDimensione +1;
	    int finePagina = Math.min(blocco * bloccoDimensione, totPages);
	    int ultimoBlocco = (int) Math.ceil((double) totPages / bloccoDimensione);
	    
	    model.addAttribute("bloccoDimensione", bloccoDimensione);
	    model.addAttribute("inizioPagina", inizioPagina);
	    model.addAttribute("finePagina", finePagina);
	    model.addAttribute("bloccoCorrente", blocco);
	    model.addAttribute("ultimoBlocco", ultimoBlocco); 
	    
	    model.addAttribute("deckId", deckId);
    	model.addAttribute("deckName",deckService.getDeckById(deckId).get().getNameDeck());
        model.addAttribute("cards", allCards);
        model.addAttribute("currentPage", page);
        model.addAttribute("totPages", totPages);
        
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
    public CompletableFuture<String> addCard(@RequestParam Long deckId, @RequestParam String cardId) { // Allows you to add cards asynchronously
        return deckCardsService.SetCard(deckId, cardId, 1);
    }

    @PostMapping("/yourdeck/removeCard")
    @ResponseBody
    @Async
    public CompletableFuture<String> removeCard(@RequestParam Long deckId, @RequestParam String cardId) { // Allows you to remove cards asynchronously
        return deckCardsService.RemoveCard(deckId, cardId, 1);
    }
    
    @PostMapping("/validate")
    public @ResponseBody String validate(@RequestParam Long deckIdValidate, @AuthenticationPrincipal Object principal) {
    	
    	// Obtains the current user
        User user = userService.userCheck(principal);
        
        // Call the validation method which now returns an already formatted string
        String validationResult = deckCardsService.validateDeck(deckIdValidate, user);
        
        // Returns the validation result directly (which is already formatted)
        return validationResult;
    }
    
    

}
