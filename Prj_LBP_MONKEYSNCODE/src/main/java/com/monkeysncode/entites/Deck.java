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
	
	
	private boolean valid; // Field to keep track of the validity of the deck

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}
	    
	    
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	
	@ManyToOne
    @JoinColumn(name = "deckImg_id")
    private DeckImg deckImg;

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
	
	public DeckImg getDeckImg() {
		return deckImg;
	}
	public void setDeckImg(DeckImg deckImg) {
		this.deckImg = deckImg;
	}
	
	
}

