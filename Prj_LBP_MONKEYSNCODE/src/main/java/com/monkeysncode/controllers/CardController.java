package com.monkeysncode.controllers;

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
	
	/*@GetMapping("/cards")
	public String Cards(
			Model model,
			@RequestParam(required = false) String set,
	        @RequestParam(required = false) String types,
	        @RequestParam(required = false) String name,
	        @RequestParam(required = false) String rarity,
	        @RequestParam(required = false) String supertype,
	        @RequestParam(required = false) String subtypes,
	        @RequestParam(required = false) String sort){
		//findByParam ha una serie di parametri opzionali, quando tutti assenti ritorna la lista completa
		
		List<Card> lista = cardService.findByParam(set, types, name, rarity, supertype, subtypes, sort);
		if (lista.size() > 50)
			lista = lista.subList(0, 50);
		model.addAttribute("cards", lista);
		return "cards";
		
	}*/
	
	 @GetMapping("/cards")
	    public String getCards(
	        Model model,
	        @RequestParam(defaultValue = "0") int page,
	        //@RequestParam(defaultValue = "10") int size,
			@RequestParam(required = false) String set,
	        @RequestParam(required = false) String types,
	        @RequestParam(required = false) String name,
	        @RequestParam(required = false) String rarity,
	        @RequestParam(required = false) String supertype,
	        @RequestParam(required = false) String subtypes,
	        @RequestParam(required = false,defaultValue = "name") String sort,
	        @RequestParam(defaultValue = "false") boolean desc){
		 

		 	List<Card> cards = cardService.findByParam(set, types, name, rarity, supertype, subtypes, sort, desc);

	        // Paginazione
		 	int size = 30;   //questo  il numero di carte visualizzato in una singola pagina
	        int start = page * size;
	        int end = Math.min((page + 1) * size, cards.size());
	        List<Card> paginatedCards = cards.subList(start, end);

	        model.addAttribute("cards", paginatedCards);
	        model.addAttribute("currentPage", page);
	        model.addAttribute("totalPages", (int) Math.ceil((double) cards.size() / size));
	        model.addAttribute("size", size);  
	        
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
