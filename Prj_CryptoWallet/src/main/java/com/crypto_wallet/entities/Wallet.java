package com.crypto_wallet.entities;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;

import jakarta.persistence.*;

import com.crypto_wallet.entities.Coin;
@Entity
@Table(name="wallet")
public class Wallet {

	@jakarta.persistence.Id
	private int id;
	
	@ManyToOne
	@JoinColumn(name ="id_user")
	private User user;
	@ManyToMany(mappedBy="wallet")
	private Coin coins;
	private int quantity;
	private double prices; //quantity*Coin.prices
	public double getPrices() {
		return prices;
	}
	public void setPrices(double prices) {
		this.prices = prices;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Coin getCoins() {
		return coins;
	}
	public void setCoins(Coin coins) {
		this.coins = coins;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
}
