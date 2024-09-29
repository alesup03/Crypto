package com.monkeysncode.repos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.monkeysncode.entites.Card;

public interface CardsDAO extends JpaRepository<Card, String> 
{
	List<Card> findByName(String name);
	List<Card> findBySet(String set);
	Optional<Card> findByHp(String hp);
	List<Card> findByRarity (String rarity);
	Optional<Card> findByTypes (String types);
	List<Card> findBySupertypes (String supertypes);
	List<Card> findBySubtypes (String subtypes);
	
	//filtro per per ogni campo
	@Query("SELECT c FROM Card c WHERE "+
			"c.set LIKE %:set% AND "+
			"c.hp LIKE %:hp% AND "+
			"c.rarity LIKE %:rarity% AND "+
			"c.types LIKE %:types% AND "+
			"c.supertypes LIKE %:supertypes% AND "+
			"c.subtypes LIKE %:subtypes%")
	public List<Card> FilteredQuery(
			@Param("set") String set,
			@Param("hp") String hp,
			@Param("rarity") String rarity,
			@Param("types") String types,
			@Param("supertypes") String supertypes,
			@Param("subtypes") String subtypes);
	
	//filtro specifico per il range di hp
	@Query("SELECT c FROM Card c WHERE "+
			"c.set LIKE %:set% AND "+
			"(CAST(c.hp AS INTEGER) BETWEEN :hpMin AND :hpMax) AND "+
			"c.rarity LIKE %:rarity% AND "+
			"c.types LIKE %:types% AND "+
			"c.supertypes LIKE %:supertypes% AND "+
			"c.subtypes LIKE %:subtypes%")
	public List<Card> FilteredQueryWithRange(
			@Param("set") String set,
			@Param("hpMin") int hpMin,
			@Param("hpMax") int hpMax,
			@Param("rarity") String rarity,
			@Param("types") String types,
			@Param("supertypes") String supertypes,
			@Param("subtypes") String subtypes);
	
}
