package com.monkeysncode.servicies;

import java.util.List;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.monkeysncode.entites.User;
import com.monkeysncode.repos.UserDAO;
@Service
public class UserService {
	private final UserDAO userDAO;
	private final PasswordEncoder passwordEncoder;

    public UserService(UserDAO userDAO,PasswordEncoder passwordEncoder) {
        this.userDAO = userDAO;
        this.passwordEncoder=passwordEncoder;
    }
    

    public void saveOrUpdateUser(OAuth2User oAuth2User) {
        String id = oAuth2User.getName();  
        String name = oAuth2User.getAttribute("name");
        String email = oAuth2User.getAttribute("email");

        User user = userDAO.findById(id).orElse(new User()); 
        user.setId(id);
        user.setName(name);
        user.setEmail(email);

        userDAO.save(user);
    }
    public void register(User user) {
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
    public boolean exists(User user) {
    	String id=user.getEmail();
    	List<User>listaUser=userDAO.findAll();
    	for (User user2 : listaUser) {
			if(user2.getEmail().equals(id))
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
}
