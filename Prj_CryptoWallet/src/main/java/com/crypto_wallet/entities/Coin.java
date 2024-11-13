package com.crypto_wallet.entities;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;


@Entity
@Table(name="coins") //il nome della table nel db
public class Coin {

	@Id
	private int id;

	private String name;
	private String symbols;   //e.g BTC,ETH,....
	private String img;  //e.g https://.png
	@ManyToMany
	@JoinTable(
	            name = "parti",
	            joinColumns = @JoinColumn(name = "wallet_id"), 
	            inverseJoinColumns = @JoinColumn(name = "coin_id") 
	    )
	private   Set<Wallet> wallet = new HashSet<>();
	private double price;
	public int getId() {
		return id;
	}
	public Set<Wallet> getWallet() {
		return wallet;
	}
	public void setWallet(Set<Wallet> wallet) {
		this.wallet = wallet;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
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
	
	
}

