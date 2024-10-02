package com.monkeysncode.servicies;

import java.util.ArrayList;
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
	
	public List<Card> findByParam(String set, String types, String name, String rarity, String supertype, String subtypes){
		ArrayList<Card> lista = (ArrayList<Card>) cardDAO.findAll();
		ArrayList<Card> lista2 = new ArrayList<>();
		
		if (checkNull(set,types,name,rarity,supertype,subtypes)==0)
			lista2=lista;
		
		if(set != null) {
			for (Card carta : lista){
				if(carta.getSet().contains(set))
					lista2.add(carta);
			}
			lista = lista2;
			lista2 = new ArrayList<>();
		}
		
		if(types != null) {
			for (Card carta : lista){
				if(carta.getTypes().contains(types))
					lista2.add(carta);
			}
			lista = lista2;
			lista2 = new ArrayList<>();
		}
		
		if(name != null) {
			for (Card carta : lista){
				if(carta.getName().contains(name))
					lista2.add(carta);
			}
			lista = lista2;
			lista2 = new ArrayList<>();
		}
		
		if(rarity != null) {
			for (Card carta : lista){
				if(carta.getRarity().equals(rarity))
					lista2.add(carta);
			}
			lista = lista2;
			lista2 = new ArrayList<>();
		}
		
		if(supertype != null) {
			for (Card carta : lista){
				if(carta.getSupertypes().equals(supertype))
					lista2.add(carta);
			}
			lista = lista2;
			lista2 = new ArrayList<>();
		}
		
		if(subtypes != null) {
			for (Card carta : lista){
				if(carta.getSubtypes().equals(subtypes))
					lista2.add(carta);
			}
			lista = lista2;
			lista2 = new ArrayList<>();
		}
		
		//secondo me il filtro sugli hp non serve
		/*if(hp != null) {
		for (Card carta : lista){
		//hp nel db è scritto come un float(es:60.0), ho provato a risolvere con il parse
			if(carta.getHp() != "") {
				if(Double.parseDouble(carta.getHp()) == Double.parseDouble(hp))
					lista2.add(carta);
			}
		}
		lista = lista2;
		lista2 = new ArrayList<>();
	}*/
		
		return lista;
	}
	
	private int checkNull(String set, String types, String hp, String rarity, String supertype, String subtypes) {
		int i=0;
		if (set != null)
			i++;
		if (types != null)
			i++;
		if (hp != null)
			i++;
		if (rarity != null)
			i++;
		if (supertype != null)
			i++;
		if (subtypes != null)
			i++;
		return i;
	}
	
	public List<Card> FilteredQuery(String set, String types, String hp, String rarity, String supertypes, String subtypes) {
		return this.cardDAO.FilteredQuery(set, hp, rarity, types, supertypes, subtypes);
	}
	
	
	public List<Card> FilteredQuery2(String set, String types) {
		String hp = null;
		String rarity =null;
		String supertypes =null;
		String subtypes =null;
		return this.cardDAO.FilteredQuery(set, hp, rarity, types, supertypes, subtypes);
	}
}
