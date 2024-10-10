package com.monkeysncode.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.monkeysncode.entites.Card;
import com.monkeysncode.entites.User;
import com.monkeysncode.entites.UserCards;
import com.monkeysncode.servicies.CardService;
import com.monkeysncode.servicies.UserCardsService;
import com.monkeysncode.servicies.UserService;

import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class CardController {
	
	@Autowired
	private CardService cardService;
    @Autowired
    private UserService userService;
	@Autowired
	private UserCardsService usercardService;
	
	@GetMapping("/cards")
	public String getCards(
		@AuthenticationPrincipal Object principal,
        Model model,
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "false") boolean owned,
		@RequestParam(required = false) String set,
        @RequestParam(required = false) String types,
        @RequestParam(required = false) String name,
        @RequestParam(required = false) String rarity,
        @RequestParam(required = false) String supertype,
        @RequestParam(required = false) String subtypes,
        @RequestParam(required = false,defaultValue = "name") String sort,
        @RequestParam(defaultValue = "false") boolean desc,
        @RequestParam(defaultValue = "1") int blocco){
		
	    
	    if(blocco < 1) {
	    	blocco = 1;
	    }
	    
		User user = userService.userCheck(principal);
		
		HashMap<String, String> param = new HashMap<String, String>();
	 	param.put("set", set);
	 	param.put("types", types);
	 	param.put("name", name);
	 	param.put("rarity", rarity);
	 	param.put("supertype", supertype);
	 	param.put("subtypes", subtypes);
	 	
		
		List<Card> cards = new ArrayList<Card>();
		
		//in base al parametro owned prende la lista da due service diversi
	 	if(owned == true)
	 		cards = cardService.filterByParam(param, usercardService.getCollection(user.getId()));
	 	else cards = cardService.filterByParam(param, cardService.findAllSorted(sort,desc));
	 	
	 	// Paginazione
	    //int start = page * size;
	    //int end = Math.min((page + 1) * size, cards.size());
	    //List<Card> paginatedCards = cards.subList(start, end);
	 	
	 	
	 	int size = 30;  // numero di carte per pagina
	 	List<Card> allCards = cardService.getCardsByPage(cards,page, size);
	 	
	    int totalPages = (int) Math.ceil((double) cards.size() / size);

	    // Gestione dei blocchi di pagine (15 pagine per blocco)
	    int bloccoDimensione = 15;
	    int inizioPagina = (blocco - 1) * bloccoDimensione;
	    int finePagina = Math.min(blocco * bloccoDimensione, totalPages);
	    int ultimoBlocco = (int) Math.ceil((double) totalPages / bloccoDimensione);

	    model.addAttribute("bloccoDimensione", bloccoDimensione);
	    model.addAttribute("totalPages", totalPages);
	    model.addAttribute("cards", allCards);
	    model.addAttribute("currentPage", page);
	    model.addAttribute("inizioPagina", inizioPagina);
	    model.addAttribute("finePagina", finePagina);
	    model.addAttribute("bloccoCorrente", blocco);
	    model.addAttribute("ultimoBlocco", ultimoBlocco); 
        
        param.put("sort", sort);
	 	param.put("desc", desc!= true ? "false" : "true");
	 	param.put("owned", owned!= true ? "false" : "true");
	 	model.addAttribute("param", param);

        return "cards";
        
	 }
	
	 @GetMapping("/card/{cardId}")
	 public String viewCard(@AuthenticationPrincipal Object principal, @PathVariable("cardId") String cardId, Model model) {

	     Optional<Card> cardOptional = cardService.getCardById(cardId);
	     User user = userService.userCheck(principal);
	     
	     if (cardOptional.isPresent()) {
	         Card card = cardOptional.get();
	         model.addAttribute("card", card);
	         int quantity = usercardService.getQuantityByCardUser(user, card);
	         model.addAttribute("inCollection", quantity);
	         return "cardView"; 
	     } else {
	         model.addAttribute("errorMessage", "La carta con ID " + cardId + " non esiste.");
	         return "error";
	     }
	 }

	
}
