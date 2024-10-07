package com.monkeysncode.entites;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="users")
public class User {

    @Id
    private String id;  
    private String name;
    private String email;
    private String password;
    
    @OneToMany(mappedBy = "user")
    private List<Deck> decks;
    
    @ManyToOne
    @JoinColumn(name = "userImg_id")
    private UserImg userImg;
    
	public UserImg getUserImg() {
		return userImg;
	}
	public void setUserImg(UserImg userImg) {
		this.userImg = userImg;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public List<Deck> getDecks() {
		return decks;
	}
	public void setDecks(List<Deck> decks) {
		this.decks = decks;
	}

    
    
}