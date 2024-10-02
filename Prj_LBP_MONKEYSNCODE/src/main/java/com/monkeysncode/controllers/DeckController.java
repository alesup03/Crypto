package com.monkeysncode.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.monkeysncode.entites.Deck;
import com.monkeysncode.entites.User; // Assicurati che il pacchetto sia corretto
import com.monkeysncode.servicies.DeckService;
import com.monkeysncode.servicies.DeckCardsService;
import com.monkeysncode.servicies.UserService; // Importa il tuo UserService

@RestController
@RequestMapping("/decks") // Mappatura per i mazzi
public class DeckController {

    @Autowired
    private DeckService deckService;

    @Autowired
    private DeckCardsService deckCardsService;

    @Autowired
    private UserService userService; // Inietta il servizio utente

    /*/ Endpoint per creare un mazzo
    @PostMapping
    public ResponseEntity<Deck> createDeck(@RequestBody Deck deck, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByEmail(userDetails.getUsername()); // Trova l'utente attuale
        deck.setUser(user); // Associa l'utente al mazzo

        Deck createdDeck = deckService.save(deck);
        return ResponseEntity.ok(createdDeck);
    }
/*/
    
    // Endpoint per eliminare un mazzo
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        deckService.DeleteDeck(id);
    }

    // Nuovo endpoint per validare un mazzo
    @GetMapping("/validate/{deckId}")
    public ResponseEntity<Boolean> validateDeck(@PathVariable Long deckId) {
        boolean isValid = deckCardsService.validateDeck(deckId);
        return ResponseEntity.ok(isValid);
    }
}
