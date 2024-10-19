package com.monkeysncode.entites;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
    
    //relationship between user and decks
    @OneToMany(mappedBy = "user")
    private List<Deck> decks;
    
    //relationship between user and user imagine
    @ManyToOne
    @JoinColumn(name = "userImg_id")
    private UserImg userImg;
    
    //relationship between user and roles
    @ManyToMany
    @JoinTable(
        name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles; // a user can have multiple roles

    @ManyToMany
    @JoinTable(
        name = "follows",
        joinColumns = @JoinColumn(name = "follower_id"),
        inverseJoinColumns = @JoinColumn(name = "following_id")
    )
    private Set<User> following = new HashSet<>();

    // Lista di utenti che ti seguono (followers)
    @ManyToMany(mappedBy = "following")
    private Set<User> followers = new HashSet<>();
    
    private boolean online;

    public boolean isOnline() {
		return online;
	}

	public void setOnline(boolean online) {
		this.online = online;
	}

	public Set<User> getFollowing() {
		return following;
	}

	public void setFollowing(Set<User> following) {
		this.following = following;
	}

	public Set<User> getFollowers() {
		return followers;
	}

	public void setFollowers(Set<User> followers) {
		this.followers = followers;
	}

	public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        if (roles.size() < 1 || roles.size() > 2) {
            throw new IllegalArgumentException("User must have at least 1 role and a maximum of 2 roles.");
        }
        this.roles = roles;
    }
    
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