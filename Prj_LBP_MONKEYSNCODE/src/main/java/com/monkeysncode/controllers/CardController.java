package com.monkeysncode.controllers;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.monkeysncode.entites.Card;
import com.monkeysncode.servicies.CardService;


import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class CardController {
	
	@Autowired
	private CardService cardService;
	
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

	        return "Cards";
	        
	 }
	

	
}
