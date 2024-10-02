package com.monkeysncode.entites;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="decks")
public class Deck {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nameDeck;
	//tolta la stringa deckCard perchè è stata creata la relazione tra deck e card
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNameDeck() {
		return nameDeck;
	}

	public void setNameDeck(String nameDeck) {
		this.nameDeck = nameDeck;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	} 
	
}

