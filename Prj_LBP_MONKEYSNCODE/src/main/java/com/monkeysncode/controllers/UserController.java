package com.monkeysncode.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.monkeysncode.entites.Deck;
import com.monkeysncode.entites.User;
import com.monkeysncode.entites.UserImg;
import com.monkeysncode.servicies.UserService;

@Controller
@RequestMapping("/profile")
public class UserController 
{
	@Autowired
	private UserService userService;
	
	Deck deck = new Deck();
	

	//Crea una lista di immagini per quanto riguarda lo starter 
	private List<UserImg> getFavouriteStarter()
	{
		List<UserImg> images = userService.getAllUserImg();
	    	
	    return images;
	}
	
	// Mostra il profilo utente con le immagini disponibili
    @GetMapping("")
    public String showProfile(@AuthenticationPrincipal Object principal, Model model) throws Exception
    {
    	//Controlla se l'utente è registrato
        User user = userService.userCheck(principal);
        
        List<Deck> userDecks = user.getDecks();
        long img =  userService.getUserProfileImage(user.getId());
        
        
        model.addAttribute("username", user.getName()); // Aggiunge il nome utente al model
        model.addAttribute("email", user.getEmail()); // Aggiunge l'email dell'utente al model
        model.addAttribute("id", user.getId()); // Aggiunge id dell'utente al model
        model.addAttribute("deck", deck.getNameDeck() ); // Aggiunge il nome del mazzo al model
        model.addAttribute("userImg", user.getUserImg()); // Assuming userImg is defined
        model.addAttribute("userImgId", img); // Assuming userImg is defined
        

        // Aggiunge la lista delle immagini per la scelta
        model.addAttribute("starterImages", getFavouriteStarter());
        
        //Aggiunge la lista dei deck al model
        model.addAttribute("userDecks", userDecks);
        
        return "userProfile"; // Template per il profilo utente
    }


    // Gestisce la selezione dell'immagine del Pokémon da parte dell'utente
    @PostMapping("")
    public String submitProfile(@RequestParam("selectedImage") long selectedImage,
                                @AuthenticationPrincipal Object principal, Model model) 
    {
        User user = userService.userCheck(principal);
        
        List<Deck> userDecks = user.getDecks();
        
        Optional<UserImg> imgOptional = userService.getUserImgById(selectedImage);
        UserImg img = imgOptional.orElseThrow(() -> new RuntimeException("Image not found!"));

        
        model.addAttribute("username", user.getName()); 
        model.addAttribute("email", user.getEmail()); 
        model.addAttribute("id", user.getId()); 
        model.addAttribute("deck", deck.getNameDeck());

        model.addAttribute("starterImages", getFavouriteStarter()); // Mantiene la lista delle immagini e aggiungi l'immagine selezionata
        model.addAttribute("userImg",  img); // Immagine selezionato
        model.addAttribute("userDecks", userDecks);
        
        try {
			userService.updateProfileImage(user.getId(), selectedImage);
			return "userProfile"; // Ricarica il profilo con l'immagine scelta
		} catch (Exception e) {
			
			 return "Home" ;
		}
 
    }
    
}
