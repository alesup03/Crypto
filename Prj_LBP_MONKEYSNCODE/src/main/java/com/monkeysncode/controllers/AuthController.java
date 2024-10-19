package com.monkeysncode.controllers;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.monkeysncode.entites.User;
import com.monkeysncode.services.UserService;


@Controller
public class AuthController { // Controller who manages the user authentication 
	
	private static final String REGEX_PASSWORD = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[\\p{Punct}])(?=\\S+$).{8,}$"; //static variable where the regex for the password is set

    @Autowired
	UserService userService;
	
    @GetMapping("/login")
    public String loginPage() {
        return "login";  // Render the login page (with forms and OAuth2 support)
    }
    
    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new User());
        return "register";  // Render the register page
    }
    
    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, Model model) {

    	
    	boolean userExists=userService.exists(user); // Check if the user is already registered
    	if(userExists) {
    		model.addAttribute("exist", "Errore, profilo esiste già");
    		return "home1";
    	}
    	
    	String password = user.getPassword().trim(); // Removes white spaces
    	if(!isValidPassword(password)) //Validation for the regex password
    	{
    		model.addAttribute("passInvalid", "La password deve contenere almeno 8 caratteri, una lettera maiuscola, minuscola, numero e carattere speciale");
    		return "home1";
    	}
    	
        userService.register(user);
        model.addAttribute("regiSuccess", "Registrato con successo");
        return "home1";  // After register, render to the login page
                
        
    }
    
    private boolean isValidPassword(String password) //Method to convalidate the regex password
    {
        Pattern pattern = Pattern.compile(REGEX_PASSWORD);  // compile the regex to create pattern
        Matcher matcher = pattern.matcher(password); // Used to search for the pattern
        return matcher.matches(); // Return a string for a match against a regular expression
    }
    

}

