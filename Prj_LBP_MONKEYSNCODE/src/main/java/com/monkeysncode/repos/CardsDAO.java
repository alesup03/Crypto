package com.monkeysncode.repos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

import com.monkeysncode.entites.Card;

public interface CardsDAO extends JpaRepository<Card, String> 
{
	List<Card> findAll(Sort sort);
	List<Card> findByName(String name);
	//List<Card> findBySet(String cardSet);
	Optional<Card> findByHp(String hp);
	List<Card> findByRarity (String rarity);
	Optional<Card> findByTypes (String types);
	List<Card> findBySupertypes (String supertypes);
	List<Card> findBySubtypes (String subtypes);

	
}
	

	
