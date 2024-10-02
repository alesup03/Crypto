package com.monkeysncode.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.monkeysncode.entites.Card;
import com.monkeysncode.servicies.CardService;

import ch.qos.logback.core.model.Model;

import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class CardController {
	
	@Autowired
	private CardService cardService;
	
	@GetMapping("/cards")
	public List<Card> Cards(
			@RequestParam(required = false) String set,
	        @RequestParam(required = false) String types,
	        @RequestParam(required = false) String name,
	        @RequestParam(required = false) String rarity,
	        @RequestParam(required = false) String supertype,
	        @RequestParam(required = false) String subtypes,
	        @RequestParam(required = false) String sort){
		
		/*if (name != null)
			return cardService.findByName(name);
		
		return cardService.findByParam(set,types,name,rarity,supertype,subtypes);*/
		
		return cardService.findByParam(set, types, name, rarity, supertype, subtypes, sort);
		//findByParam ha una serie di parametri opzionali, quando tutti assenti ritorna la lista completa
	}
	

	
}
