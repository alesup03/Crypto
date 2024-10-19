package com.monkeysncode.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DeckRulesController { // Controller who manages the deck rules

    @GetMapping("/deckRules")  
    public String mostraDeckRules() {
        return "deckRules"; 
    }
}