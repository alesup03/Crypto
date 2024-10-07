package com.monkeysncode.servicies;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.monkeysncode.entites.Deck;
import com.monkeysncode.entites.User;
import com.monkeysncode.repos.UserDAO;
@Service
public class UserService  implements UserDetailsService{
	private final UserDAO userDAO;
	private final PasswordEncoder passwordEncoder;

    public UserService(UserDAO userDAO,PasswordEncoder passwordEncoder) {
        this.userDAO = userDAO;
        this.passwordEncoder=passwordEncoder;
    }
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userDAO.findByEmail(email).orElseThrow(() ->
            new UsernameNotFoundException("User not found"));

        return org.springframework.security.core.userdetails.User.builder()
            .username(user.getEmail())
            .password(user.getPassword())
            //.roles("USER")//da implementare per i ruoli
            .build();
    }
    

    public void saveOrUpdateUser(OAuth2User oAuth2User) {
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");
        String oauthProviderId = oAuth2User.getName();

        Optional<User> existingUser = userDAO.findByEmail(email);

        User user;
        if (existingUser.isPresent()) {
            user = existingUser.get();
            if (user.getId() == null) {
                user.setId(oauthProviderId);
            }
        } else {
            // Se non esiste, crea un nuovo utente
            user = new User();
            user.setId(oauthProviderId);  // Imposta l'ID del provider OAuth2
            user.setEmail(email);
            user.setName(name);
        }

        // Salva l'utente nel database
        userDAO.save(user);
    }
    public void register(User user) {//registra i dati mandati dall'utente nel db criptando anche la pass
    	String id = UUID.randomUUID().toString();
        String name = user.getName();
        String email = user.getEmail();
        String password=passwordEncoder.encode(user.getPassword());
        user.setId(id);
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        userDAO.save(user);
    }
    public boolean exists(User user) {//check se esiste la mail nel db durante la registrazione
    	String id=user.getEmail();
    	List<User>listaUser=userDAO.findAll();
    	for (User user2 : listaUser) {
			if(user2.getEmail().toLowerCase().equals(id.toLowerCase()))
				return true;
		}
    	return false;
    	}
    public User getUserById(String id) {

    	List<User>userList= userDAO.findAll();
    	for (User user : userList) {
			if(user.getId().equals(id)) {
				return user;
			}
		}
    	return null;
    }

    public User findByEmail(String email) {
        return userDAO.findByEmail(email).orElse(null);
    }
    public User findByName(String name) {
        return userDAO.findByEmail(name).orElse(null);
    }
    public void DeleteUser(String id)throws UsernameNotFoundException {
    	 if (!userDAO.existsById(id)) {
             throw new UsernameNotFoundException("Utente non trovato");
         }

         userDAO.deleteById(id);
    	 
    }
    public User userCheck(Object principal) {
        if (principal instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) principal;
            return findByEmail(userDetails.getUsername());
            // Handle form login logic
        } else{
            OAuth2User oAuth2User = (OAuth2User) principal;
            return findByEmail(oAuth2User.getAttribute("email"));
        }
        
    }
}
