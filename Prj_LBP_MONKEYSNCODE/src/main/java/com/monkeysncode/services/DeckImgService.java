package com.monkeysncode.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.monkeysncode.entites.DeckImg;
import com.monkeysncode.repos.DeckImgDAO;

@Service
public class DeckImgService {
	@Autowired
	DeckImgDAO imgDAO;
	
	public List<DeckImg> getAll() {
		return imgDAO.findAll();
	}
	
	public Optional<DeckImg> getDeckImgById(Long id) {
	    return imgDAO.findById(id);
	}
}
