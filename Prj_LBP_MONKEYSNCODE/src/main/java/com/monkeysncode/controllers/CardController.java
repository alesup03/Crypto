package com.monkeysncode.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.monkeysncode.entites.Card;
import com.monkeysncode.servicies.CardService;

@RestController
public class CardController {

	@Autowired
	private CardService cardService;
	
	@GetMapping("/Cards")
	public List<Card> Cards(){
		return cardService.findALL().subList(0, 100);
		//ho agiunto sublist per far visualizzare solo i primi 100 elementi, altrimenti si blocca
	}
}
