package com.monkeysncode.entites;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity 
@Table(name = "cards")
public class Card 
{
	@Id 
	private String id;
	
	private String cardSet;
	
	private String series;
	
	private String publisher;
	
	private String generation;
	
	private String release_date;
	
	private String artist;
	
	private String name;
	
	private String set_num;
	
	private String types;
	
	@Column(name="supertype")
	private String supertypes;
	
	private String subtypes;
	
	private String level;
	
	private String hp;
	
	private String evolvesFrom;
	
	private String evolvesTo;
	
	private String abilities; /*vedere e convertire in HashMap*/
	
	private String attacks; /*vedere e convertire in HashMap*/
	
	private String weakness; /*vedere e convertire in HashMap*/
	
	private String retreatCost; /*vedere e convertire in HashMap*/
	
	private String converted; 
	
	private String rarity; 
	
	private String flavorText; 

	@Column(name="nationalPokedexNumbers")
	private String nationalPokedexNumbers; 
	
	private String legalities; /*vedere e convertire in HashMap*/
	
	private String resistances; /*vedere e convertire in HashMap*/
	
	private String rules;
	
	private String regulationMark; 
	
	private String ancientTrait;  /*vedere e convertire in HashMap*/
	
	private String img;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSet() {
		return cardSet;
	}

	public void setSet(String set) {
		this.cardSet = set;
	}

	public String getSeries() {
		return series;
	}

	public void setSeries(String series) {
		this.series = series;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getGeneration() {
		return generation;
	}

	public void setGeneration(String generation) {
		this.generation = generation;
	}

	public String getRelease_date() {
		return release_date;
	}

	public void setRelease_date(String release_date) {
		this.release_date = release_date;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSet_num() {
		return set_num;
	}

	public void setSet_num(String set_num) {
		this.set_num = set_num;
	}

	public String getTypes() {
		return types;
	}

	public void setTypes(String types) {
		this.types = types;
	}

	public String getSupertypes() {
		return supertypes;
	}

	public void setSupertypes(String supertypes) {
		this.supertypes = supertypes;
	}

	public String getSubtypes() {
		return subtypes;
	}

	public void setSubtypes(String subtypes) {
		this.subtypes = subtypes;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getHp() {
		return hp;
	}

	public void setHp(String hp) {
		this.hp = hp;
	}

	public String getEvolvesFrom() {
		return evolvesFrom;
	}

	public void setEvolvesFrom(String evolvesFrom) {
		this.evolvesFrom = evolvesFrom;
	}

	public String getEvolvesTo() {
		return evolvesTo;
	}

	public void setEvolvesTo(String evolvesTo) {
		this.evolvesTo = evolvesTo;
	}

	public String getAbilities() {
		return abilities;
	}

	public void setAbilities(String abilities) {
		this.abilities = abilities;
	}

	public String getAttacks() {
		return attacks;
	}

	public void setAttacks(String attacks) {
		this.attacks = attacks;
	}

	public String getWeakness() {
		return weakness;
	}

	public void setWeakness(String weakness) {
		this.weakness = weakness;
	}

	public String getRetreatCost() {
		return retreatCost;
	}

	public void setRetreatCost(String retreatCost) {
		this.retreatCost = retreatCost;
	}

	public String getConverted() {
		return converted;
	}

	public void setConverted(String converted) {
		this.converted = converted;
	}

	public String getRarity() {
		return rarity;
	}

	public void setRarity(String rarity) {
		this.rarity = rarity;
	}

	public String getFlavorText() {
		return flavorText;
	}

	public void setFlavorText(String flavorText) {
		this.flavorText = flavorText;
	}

	public String getNationalPokedexNumbers() {
		return nationalPokedexNumbers;
	}

	public void setNationalPokedexNumbers(String nationalPokedexNumbers) {
		this.nationalPokedexNumbers = nationalPokedexNumbers;
	}

	public String getLegalities() {
		return legalities;
	}

	public void setLegalities(String legalities) {
		this.legalities = legalities;
	}

	public String getResistances() {
		return resistances;
	}

	public void setResistances(String resistances) {
		this.resistances = resistances;
	}

	public String getRules() {
		return rules;
	}

	public void setRules(String rules) {
		this.rules = rules;
	}

	public String getRegulationMark() {
		return regulationMark;
	}

	public void setRegulationMark(String regulationMark) {
		this.regulationMark = regulationMark;
	}

	public String getAncientTrait() {
		return ancientTrait;
	}

	public void setAncientTrait(String ancientTrait) {
		this.ancientTrait = ancientTrait;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}
	
	
	
}
