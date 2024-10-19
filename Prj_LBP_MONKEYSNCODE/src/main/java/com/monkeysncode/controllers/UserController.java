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
import com.monkeysncode.entites.UserCards;
import com.monkeysncode.entites.UserImg;
import com.monkeysncode.repos.UserCardDAO;
import com.monkeysncode.servicies.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/profile")
public class UserController // Controller who manages the user profile
{
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserCardDAO userCardDAO;
	
	private static final String REGEX_CHANGE_PASSWORD = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=])(?=\\S+$).{8,}$"; //static variable where the regex for the password is set
	
	Deck deck = new Deck();
	
	// Create a list of images regarding the starter
	private List<UserImg> getFavouriteStarter()
	{
		List<UserImg> images = userService.getAllUserImg();
	    	
	    return images;
	}
	
	// Show user profile with available images
    @GetMapping("")
    public String showProfile(@AuthenticationPrincipal Object principal, Model model) throws Exception
    {
    	// Checks if user is register
        User user = userService.userCheck(principal);
        
        List<Deck> userDecks = user.getDecks();
        long img =  userService.getUserProfileImage(user.getId());
        
        model.addAttribute("username", user.getName()); // Adds the username in the model
        model.addAttribute("email", user.getEmail()); 
        model.addAttribute("id", user.getId()); 
        model.addAttribute("deck", deck.getNameDeck() ); 
        model.addAttribute("userImg", user.getUserImg()); // Assuming userImg is defined
        model.addAttribute("user",user);
        
        // Adds image list for choice
        model.addAttribute("starterImages", getFavouriteStarter());
        
        // Add the deck list to the model
        model.addAttribute("userDecks", userDecks);
        
        return "userProfile";
    }

    // Manages the user's selection of the Pokémon image    
    @PostMapping("profile-image")
    public String updateProfileImage( @RequestParam("userId") String userId, @RequestParam("id") Long imageId, RedirectAttributes redirectAttributes) {
        try {
            userService.updateProfileImage(userId, imageId); // Update the user's profile picture
            redirectAttributes.addFlashAttribute("successMessage", "Immagine del profilo aggiornata con successo!");
            } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Errore durante l'aggiornamento dell'immagine del profilo.");
        }
        return "redirect:/profile"; // Redirect the user to the profile page
    }

    @GetMapping("/profile-image")
    public String setImg(Model model) {
    	List<UserImg> imgList = userService.getAllUserImg();
    	imgList.remove(imgList.size() - 1);
    	Collections.shuffle(imgList, new Random()); // Randomize the list of images
    	model.addAttribute("images",imgList.stream().limit(24).collect(Collectors.toList()));
    	return "profileImg";
    }

    // Method to change password
    @GetMapping("/change-password")
    public String changePasswordView(@AuthenticationPrincipal Object principal, Model model) {
    	User user = userService.userCheck(principal);
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
            
            String confirmPassword = formData.get("confirmPassword");
            
            // Check if the new password you entered is correct
            if (!newPassword.equals(confirmPassword)) {
                redirectAttributes.addFlashAttribute("error", "Le nuove password non corrispondono.");
                return "redirect:/profile/change-password";
            }
            
            userService.changePassword(user.getId(), oldPassword, newPassword);

            session.invalidate(); 
            
            redirectAttributes.addFlashAttribute("success", "Password cambiata con successo! Accedi di nuovo.");
            return "redirect:/"; // Redirect to the login page to request re-authentication
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/profile/change-password";
        }
    }

    // Confirmation method to delete your account
    @GetMapping("/delete")
    public String deleteUserView(@AuthenticationPrincipal Object principal, Model model) { 
    	User user = userService.userCheck(principal);
        List<Deck> decks = user.getDecks();
        List<UserCards> userCards = userCardDAO.findByUserId(user.getId());
        
        String userId = user.getId(); // Assicurati che getId() restituisca il tipo corretto
        model.addAttribute("userId", userId);
        model.addAttribute("userName", user.getName());
        model.addAttribute("userEmail", user.getEmail());
        model.addAttribute("decks", decks);
        model.addAttribute("userCards", userCards);
        Integer totalCards = userCardDAO.countTotalCardsByUserId(userId);
        
        model.addAttribute("totalCards", totalCards);
    	return "deleteUser";
    }
    
    // Method to delete your account
    @PostMapping("/delete")
    public String deleteUser(@RequestParam String userId) { 
            userService.DeleteUser(userId);
            return "redirect:/logout";
        }
    
    
    
    // Edit nickname
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
    
    // Update nickname
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
