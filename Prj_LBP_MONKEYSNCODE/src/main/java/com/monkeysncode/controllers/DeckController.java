package com.monkeysncode.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.monkeysncode.servicies.DeckService;
@RestController
public class DeckController {
	
	@Autowired
    private DeckService deckService;
	  @DeleteMapping("/{Id}")
	    public void deleteUser(@PathVariable Long id) {
	        deckService.DeleteDeck(id);
	    }
