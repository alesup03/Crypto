package com.crypto_wallet.entities;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import com.crypto_wallet.entities.Coin;
@Entity
@Table(name="wallet")
public class Wallet {

	@Id
	private int id;
	@ManyToOne()
	@Column(name ="id_user")
	private User user;
	@ManyToMany(mappedBy="wallet")
	private Set<Coin> coins;
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
	public Set<Coin> getCoins() {
		return coins;
	}
	public void setCoins(Set<Coin> coins) {
		this.coins = coins;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
}
