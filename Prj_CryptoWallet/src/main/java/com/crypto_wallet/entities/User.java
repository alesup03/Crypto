package com.crypto_wallet.entities;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	//nullable = false si assicura che il campo della tabella non sia vuoto
	@Column(name = "name" , nullable = false )
	private String name;
	
	@Column(name = "lastName" , nullable = false )
	private String lastName;
	
	//unique = true si assicura che non siano presenti altre mail nel db , perciò deve essere unica la mail 
	@Column(name = "email" , nullable = false, unique = true  )
	private String email;
	
	@Column(name = "password" , nullable = false)
	private String password;

	@Column(name = "dateBirth" , nullable = false)
	@Temporal(TemporalType.DATE)
	private Date dateBirth;
	
	
	//Getters and Setters
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public Date getDateBirth() {
		return dateBirth;
	}
	
	public void setDateBirth(Date dateBirth) {
		this.dateBirth = dateBirth;
	}
	
	public String getEmail(){
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
}
