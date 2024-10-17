package com.monkeysncode.controllers;


import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.regex.Matcher;

import java.util.Random;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.monkeysncode.entites.Deck;
import com.monkeysncode.entites.User;
import com.monkeysncode.entites.UserImg;
import com.monkeysncode.servicies.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/profile")
public class UserController 
{
	@Autowired
	private UserService userService;
	
	// Variabile statica per la validazione 
	private static final String REGEX_CHANGE_PASSWORD = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
	
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
        model.addAttribute("user",user);
        

        // Aggiunge la lista delle immagini per la scelta
        model.addAttribute("starterImages", getFavouriteStarter());
        
        //Aggiunge la lista dei deck al model
        model.addAttribute("userDecks", userDecks);
        
        return "userProfile"; // Template per il profilo utente
    }


    // Gestisce la selezione dell'immagine del Pokémon da parte dell'utente
    @PostMapping("profile-image")
    public String updateProfileImage( @RequestParam("userId") String userId, @RequestParam("id") Long imageId, RedirectAttributes redirectAttributes) {
        try {
            userService.updateProfileImage(userId, imageId);  // Aggiorna l'immagine profilo dell'utente
            redirectAttributes.addFlashAttribute("successMessage", "Immagine del profilo aggiornata con successo!");
            
        
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Errore durante l'aggiornamento dell'immagine del profilo.");
        }
        return "redirect:/profile";  // Reindirizza l'utente alla pagina del profilo
    }

    
    @GetMapping("/profile-image")
    public String setImg(Model model) {
    	List<UserImg> imgList=userService.getAllUserImg();
    	imgList.remove(imgList.size() - 1);
    	Collections.shuffle(imgList, new Random());
    	model.addAttribute("images",imgList.stream().limit(24).collect(Collectors.toList()));
    	return "profileImg";
    }

    //cambio password
    @GetMapping("/change-password")
    public String changePasswordView(@AuthenticationPrincipal Object principal,Model model) {
    	User user=userService.userCheck(principal);
        if(user.getPassword()!=null) {
        	model.addAttribute("passNULL",false);
        }
        else model.addAttribute("passNULL",true);
        
        return "changePassword"; 
    }
    
    @PostMapping("/change-password")
    public String changePassword(@AuthenticationPrincipal Object principal,
                                 @RequestParam Map<String, String> formData, 
                                 RedirectAttributes redirectAttributes,
                                 HttpSession session) {  

        User user = userService.userCheck(principal);

        try {
            String oldPassword = formData.get("oldPassword");
            String newPassword = formData.get("newPassword");
            System.out.println("new password è : "+newPassword);
            
            String confirmPassword = formData.get("confirmPassword");
            System.out.println("new password è : "+confirmPassword);
            
            if (!newPassword.equals(confirmPassword)) {
                redirectAttributes.addFlashAttribute("error", "Le nuove password non corrispondono.");
                return "redirect:/profile/change-password";
            }
            
           


            userService.changePassword(user.getId(), oldPassword, newPassword);

            
            session.invalidate(); 
            

            redirectAttributes.addFlashAttribute("success", "Password cambiata con successo! Accedi di nuovo.");
            return "redirect:/"; // Reindirizza alla pagina di login per richiedere una nuova autenticazione
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/profile/change-password";
        }
    }

    @GetMapping("/delete")
    public String deleteUserView(@AuthenticationPrincipal Object principal,Model model) {
    	User user=userService.userCheck(principal);
    	model.addAttribute("userId",user.getId());
    	return "deleteUser";
    }
    
    @PostMapping("/delete")
    public String deleteUser(@RequestParam String userId) {
            userService.DeleteUser(userId);
            System.out.println("è arrivato al controller");
            return "redirect:/logout";
        
    }
    
    
    
    //Modifica del nickname
    @GetMapping("/profile")
    public String getProfile(Model model, @AuthenticationPrincipal Object principal, HttpServletRequest request) {
        User user = userService.userCheck(principal);
        String nickname = (String) request.getSession().getAttribute("name");

        if (nickname == null) {
            nickname = user.getName();
        }

        model.addAttribute("username", nickname);
        return "profile";
    }
    
    @PostMapping("/update-nickname")
    public ResponseEntity<String> updateNickname(@AuthenticationPrincipal Object principal,
                                                 @RequestBody Map<String, String> requestBody,
                                                 HttpServletRequest request) {
        User user = userService.userCheck(principal);
        String newNickname = requestBody.get("nickname");

        if (newNickname == null || newNickname.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Il nickname non può essere vuoto.");
        }

        try {
            userService.updateNickname(user.getId(), newNickname);
            request.getSession().setAttribute("name", newNickname);

            return ResponseEntity.ok(newNickname);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Errore durante l'aggiornamento del nickname.");
        }
    }

}
