package com.monkeysncode.repos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.monkeysncode.entites.Card;

@Repository
public interface CardsDAO extends JpaRepository<Card, String>
{
	List <Card> findByName (String name);
	List <Card> findByRarity (String rarity);
	List <Card> findBySet (String set);
	List <Card> findByGeneration (String generation);
	Optional<Card> findByTypes (String types);
	List <Card> findBySupertype (String supertype);
	List <Card> findBySubtypes (String subtypes);
}