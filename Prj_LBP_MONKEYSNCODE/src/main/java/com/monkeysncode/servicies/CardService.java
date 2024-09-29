package com.monkeysncode.servicies;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.monkeysncode.entites.Card;
import com.monkeysncode.repos.CardsDAO;

@Service
public class CardService {
	@Autowired
	private CardsDAO cardDAO;
	
	public List<Card> findALL(){
		return cardDAO.findAll();
	}
	
	public List<Card> FilteredQuery(String set, String hp, String rarity, String types, String supertypes, String subtypes) {
		return this.cardDAO.FilteredQuery(set, hp, rarity, types, supertypes, subtypes);
	}
}
