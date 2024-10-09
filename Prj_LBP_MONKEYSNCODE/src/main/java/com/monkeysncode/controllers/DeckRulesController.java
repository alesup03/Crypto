package com.monkeysncode.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DeckRulesController {

    @GetMapping("/deckRules")  
    public String mostraDeckRules() {
        return "deckRules"; 
    }
}