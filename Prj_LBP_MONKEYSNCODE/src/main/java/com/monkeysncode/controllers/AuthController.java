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
import com.monkeysncode.servicies.UserService;


@Controller
public class AuthController {
	
	private static final String REGEX_PASSWORD = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
	
	@Autowired
	UserService userService;
    @GetMapping("/login")
    public String loginPage() {
        return "login";  // Renderizza la pagina di login (con supporto per form e OAuth2)
    }
    
    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new User());
        return "register";  // Renderizza la pagina di registrazione
    }
    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user,Model model) {

    	
    	boolean userExists=userService.exists(user);
    	if(userExists) {
    		model.addAttribute("exist", "Errore,Profilo esiste già");
    		return "home1";
    	}
    	
    	String password = user.getPassword().trim(); //rimuove spazi bianchi
    	//Validazione della password con la regex
    	if(!isValidPassword(user.getPassword()))
    	{
    		model.addAttribute("exist", "La password deve contenere almeno 8 caratteri, una lettera maiuscola, minuscola, numero e carattere speciale");
    		return "home1";
    	}
    	
        
        userService.register(user);
        model.addAttribute("exist", "Registrato con successo");
        return "home1";  // Dopo la registrazione, reindirizza alla pagina di login
                
        
    }
    
    //Metodo per convalidare la password con la regex
    private boolean isValidPassword(String password) 
    {
        Pattern pattern = Pattern.compile(REGEX_PASSWORD);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
    

}

