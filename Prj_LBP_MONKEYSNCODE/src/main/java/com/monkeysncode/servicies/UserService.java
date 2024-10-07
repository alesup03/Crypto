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
import com.monkeysncode.entites.UserImg;
import com.monkeysncode.repos.UserDAO;
import com.monkeysncode.repos.UserImgDAO;
@Service
public class UserService  implements UserDetailsService{
	private final UserDAO userDAO;
	private final UserImgDAO userImgDAO;
	private final PasswordEncoder passwordEncoder;

    public UserService(UserDAO userDAO,UserImgDAO userImgDAO,PasswordEncoder passwordEncoder) {
        this.userDAO = userDAO;
		this.userImgDAO = userImgDAO;
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
    
    // Restituisce tutte le immagini disponibili
    public List<UserImg> getAllUserImg() {
        return userImgDAO.findAll();
    }
    
    // Seleziona un'immagine del profilo per l'utente
    public void updateProfileImage(String id, Long userImgId) throws Exception {
        Optional<User> optionalUser = userDAO.findByEmail(id);
        Optional<UserImg> optionalImage = userImgDAO.findById(userImgId);

        if (optionalUser.isPresent() && optionalImage.isPresent()) {
            User user = optionalUser.get();
            UserImg userImg = optionalImage.get();

            // Assegna l'immagine del profilo all'utente
            user.setUserImg(userImg);
            userDAO.save(user);  // Salva l'utente con la nuova immagine del profilo
        } else {
            throw new Exception("User or Image not found");
        }
    }

    // Restituisce l'immagine del profilo di un utente
    public String getUserProfileImage(String id) throws Exception {
        Optional<User> optionalUser = userDAO.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getUserImg() != null) {
                return user.getUserImg().getImgPath();
            } else {
                return "Nessuna immagine selezionata";
            }
        } else {
            throw new Exception("User not found");
        }
        }
}
