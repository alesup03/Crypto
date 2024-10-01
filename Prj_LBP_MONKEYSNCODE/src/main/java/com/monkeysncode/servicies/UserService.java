package com.monkeysncode.servicies;

import java.util.List;

import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.monkeysncode.entites.User;
import com.monkeysncode.repos.UserDAO;
@Service
public class UserService {
	private final UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void saveOrUpdateUser(OAuth2User oAuth2User) {
        String id = oAuth2User.getName();  
        String name = oAuth2User.getAttribute("name");
        String email = oAuth2User.getAttribute("email");

        com.monkeysncode.entites.User user = new User();
        user.setId(id);
        user.setName(name);
        user.setEmail(email);

        userDAO.save(user);
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
<<<<<<< Updated upstream
=======
    public User findByEmail(String email) {
        return userDAO.findByEmail(email).orElse(null);
    }
    public User findByName(String name) {
        return userDAO.findByEmail(name).orElse(null);
    }
>>>>>>> Stashed changes
}
