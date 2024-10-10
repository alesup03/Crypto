package com.monkeysncode.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
	private List<String> getFavouriteStarter()
	{
		List<String> images = new ArrayList<>();
		
	    images.add("/charmender.jpg");
	    images.add("/bulbasaur.jpg");
	    images.add("/piplup.jpg");
	    images.add("/squirtle.jpg");
	    images.add("/popplio.png");
	    images.add("/mudkip.jpg");
		
	    return images;
	}
	
	// Mostra il profilo utente con le immagini disponibili
    @GetMapping("")
    public String showProfile(@AuthenticationPrincipal Object principal, Model model)
    {
    	//Controlla se l'utente è registrato
        User user = userService.userCheck(principal);
        
        List<Deck> userDecks = user.getDecks();
        
        
        model.addAttribute("username", user.getName()); // Aggiunge il nome utente al model
        model.addAttribute("email", user.getEmail()); // Aggiunge l'email dell'utente al model
        model.addAttribute("id", user.getId()); // Aggiunge id dell'utente al model
        model.addAttribute("deck", deck.getNameDeck() ); // Aggiunge il nome del mazzo al model
        

        // Aggiunge la lista delle immagini per la scelta
        model.addAttribute("starterImages", getFavouriteStarter());
        
        //Aggiunge la lista dei deck al model
        model.addAttribute("userDecks", userDecks);
        
        return "userProfile"; // Template per il profilo utente
    }


    // Gestisce la selezione dell'immagine del Pokémon da parte dell'utente
    @PostMapping("")
    public String submitProfile(@RequestParam("selectedImage") String selectedImage,
                                @AuthenticationPrincipal Object principal, Model model) 
    {
        User user = userService.userCheck(principal);
        
        List<Deck> userDecks = user.getDecks();
        
        model.addAttribute("username", user.getName()); 
        model.addAttribute("email", user.getEmail()); 
        model.addAttribute("id", user.getId()); 
        model.addAttribute("deck", deck.getNameDeck());

        model.addAttribute("starterImages", getFavouriteStarter()); // Mantiene la lista delle immagini e aggiungi l'immagine selezionata
        model.addAttribute("selectedStarterImage", selectedImage); // Immagine selezionato
        model.addAttribute("userDecks", userDecks);

        return "userProfile"; // Ricarica il profilo con l'immagine scelta
    }
    
    // Restituisce tutte le immagini del profilo disponibili (link preesistenti)
    @GetMapping("/available-profile-images")
    public List<UserImg> getAvailableProfileImages() {
        return userService.getAllUserImg();
    }

    // Permette a un utente di selezionare un'immagine del profilo
    @PostMapping("/{userId}/selectProfileImage/{UserImgId}")
    public String selectProfileImage(@PathVariable String userId, @PathVariable Long UserImgId) {
        try {
            userService.updateProfileImage(userId, UserImgId);
            return "Immagine del profilo aggiornata con successo!";
        } catch (Exception e) {
            return "Errore: " + e.getMessage();
        }
    }

    // Mostra l'immagine del profilo di un utente
    @GetMapping("/{userId}/profileImage")
    public String getUserProfileImage(@PathVariable String userId) {
        try {
            return userService.getUserProfileImage(userId);
        } catch (Exception e) {
            return "Errore: " + e.getMessage();
        }
    }
    @GetMapping("/delete")
    public String deleteUserView(@AuthenticationPrincipal Object principal, Model model) {
    	User user = userService.userCheck(principal);
    	model.addAttribute("userId",user.getId());
    	return "deleteUser";
    }
    
    @PostMapping("/delete")
    public String deleteUser(@RequestParam String userId) {
            userService.DeleteUser(userId);
            return "redirect:/logout";
        
    }

}
