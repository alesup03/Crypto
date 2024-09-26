package com.monkeysncode.entites;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="user")
public class User {

    @Id
    private String id;  
    private String name;
    private String email;
    private String deck1;
    
    

	public String getDeck1() {
		return deck1;
	}
	public void setDeck1(String deck1) {
		this.deck1 = deck1;
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

    
    
}