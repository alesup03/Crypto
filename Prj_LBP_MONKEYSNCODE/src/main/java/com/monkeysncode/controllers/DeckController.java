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
import com.monkeysncode.entites.User;
import com.monkeysncode.servicies.CardService;
import com.monkeysncode.servicies.DeckCardsService;
import com.monkeysncode.servicies.DeckService;
import com.monkeysncode.servicies.UserService;

@Controller
@RequestMapping("/decks")
public class DeckController {
    @Autowired
    private DeckCardsService deckCardsService;
    @Autowired
    private CardService cardService;
    @Autowired
    private UserService userService;
    @Autowired
    private DeckService deckService;
    
    @GetMapping("")
    public String decks(@AuthenticationPrincipal Object principal, Model model) {
        User user = userService.userCheck(principal);
        List<Deck> decks = user.getDecks();
        
        // Validazione di ogni mazzo
        for (Deck deck : decks) {
            String validationResult = deckCardsService.validateDeck(deck.getId());
            
            // Se il mazzo è valido, impostiamo valid a true, altrimenti a false
            if (validationResult.contains("Il mazzo è valido!")) {
                deck.setValid(true); // Mazzo valido
            } else {
                deck.setValid(false); // Mazzo non valido
            }
        }

        model.addAttribute("decks", decks);
        return "Decks"; // Nome del template HTML
    }


    
    @GetMapping("/create")
    public String create() {
        return "create";
    }

    @PostMapping("/create")
    public String createPost(@AuthenticationPrincipal Object principal, @RequestParam String deckName) {
        User user = userService.userCheck(principal);
        deckService.saveOrUpdateDeck(user.getId(), deckName, Optional.empty());
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
        if (confirm) {
            deckCardsService.deleteCardsFromDeck(deckId);
            deckService.DeleteDeck(deckId);
        }
        return "redirect:/decks";
    }
    
    @GetMapping("/yourdeck/{page}/{deckId}")
    public String viewDeck(@AuthenticationPrincipal Object principal,
    		@PathVariable("deckId") Long deckId,
            @PathVariable int page, Model model,
            @RequestParam(required = false) String set,
	        @RequestParam(required = false) String types,
	        @RequestParam(required = false) String name,
	        @RequestParam(required = false) String rarity,
	        @RequestParam(required = false) String supertype,
	        @RequestParam(required = false) String subtypes,
	        @RequestParam(required = false,defaultValue = "name") String sort,
	        @RequestParam(defaultValue = "false") boolean desc) {
    	
    	//List<Card> cards = cardService.filterByParam(param, cardService.findAllSorted(sort,desc));
    	List<Card> cards = cardService.findByParam(set, types, name, rarity, supertype, subtypes, sort, desc);
    	
    	User user = userService.userCheck(principal);
    	List<Card> allCards = cardService.getCardsByPage(cards,page, 131);//cambiare il find all dopo i filtri
    	int totPages=cardService.totPages(allCards, 131);
    	model.addAttribute("deckId", deckId);
        model.addAttribute("cards", allCards);
        model.addAttribute("currentPage", page);
        model.addAttribute("totPages", totPages);
        
        HashMap<String, String> param = new HashMap<String, String>();
	 	param.put("set", set);
	 	param.put("types", types);
	 	param.put("name", name);
	 	param.put("rarity", rarity);
	 	param.put("supertype", supertype);
	 	param.put("subtypes", subtypes);
	 	param.put("sort", sort);
	 	param.put("desc", desc!= true ? "false" : "true");
	 	model.addAttribute("param", param);
        
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
    
    @PostMapping("/validate")
    public @ResponseBody String validate(@RequestParam Long deckIdValidate) {
        // Chiama il metodo di validazione che ora restituisce una stringa già formattata
        String validationResult = deckCardsService.validateDeck(deckIdValidate);
        
        // Restituisci direttamente il risultato della validazione (che è già formattato)
        return validationResult;
    }
    
    

}
