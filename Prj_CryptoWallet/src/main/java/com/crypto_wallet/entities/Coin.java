package com.crypto_wallet.entities;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;


@Entity
@Table(name="coins") // Nome della tabella nel database
public class Coin {

	@Id
	private int id;

	private String name;
	private String symbols;   // e.g. BTC, ETH, ...
	private String img;       // e.g. URL dell'immagine (https://.png)

	@ManyToMany
	@JoinTable(
	    name = "parti",
	    joinColumns = @JoinColumn(name = "wallet_id"), 
	    inverseJoinColumns = @JoinColumn(name = "coin_id") 
	)
	private Set<Wallet> wallet = new HashSet<>();
	
	private double price;

	// Getters e Setters per id, name, symbols, img, price

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSymbols() {
		return symbols;
	}

	public void setSymbols(String symbols) {
		this.symbols = symbols;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	// Getter e Setter per il campo 'price'
	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
}
