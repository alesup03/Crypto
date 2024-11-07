package com.crypto_wallet.entities;

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
