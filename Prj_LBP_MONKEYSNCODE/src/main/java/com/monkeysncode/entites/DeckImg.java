package com.monkeysncode.entites;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class DeckImg {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String imgDeckPath;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getImgDeckPath() {
		return imgDeckPath;
	}

	public void setImgDeckPath(String imgDeckPath) {
		this.imgDeckPath = imgDeckPath;
	}
}
