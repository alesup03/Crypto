package com.monkeysncode.servicies;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
	
	public List<Card> findByName(String name){
		
		List<Card> lista = cardDAO.findByName(name);
		return lista;
		
	}
	
	public List<Card> findByParam(String set, String types, String name, String rarity, String supertype, String subtypes, String sort, boolean desc){
		
		ArrayList<Card> lista = new ArrayList<>();
		//sort contiene il campo in base al quale ordinare l'elenco
		if (sort != null)
			if(desc)
				lista = (ArrayList<Card>) cardDAO.findAll(Sort.by(Sort.Direction.DESC, sort));
			else lista = (ArrayList<Card>) cardDAO.findAll(Sort.by(Sort.Direction.ASC, sort));
		else lista = (ArrayList<Card>) cardDAO.findAll(Sort.unsorted());
		ArrayList<Card> lista2 = new ArrayList<>();
		
		//checkNull ritorna quanti sono i parametri NON null, se =0 non ci sono filtri e può tornare la lista completa
		if (checkNull(set,types,name,rarity,supertype,subtypes)==0)
			return lista;
		
		//per ogni parametro inserito viene applicato il filtro richiesto
		if(set != null && set!="") {
			for (Card carta : lista){
				if(carta.getSet().contains(set))
					lista2.add(carta);
			}
			lista = lista2;
			lista2 = new ArrayList<>();
		}
		
		if(types != null && types!="") {
			for (Card carta : lista){
				if(carta.getTypes().contains(types))
					lista2.add(carta);
			}
			lista = lista2;
			lista2 = new ArrayList<>();
		}
		
		if(name != null && name!="") {
			for (Card carta : lista){
				if(carta.getName().contains(name))
					lista2.add(carta);
			}
			lista = lista2;
			lista2 = new ArrayList<>();
		}
		
		if(rarity != null && rarity!="") {
			for (Card carta : lista){
				if(carta.getRarity().equals(rarity))
					lista2.add(carta);
			}
			lista = lista2;
			lista2 = new ArrayList<>();
		}
		
		if(supertype != null && supertype!="") {
			for (Card carta : lista){
				if(carta.getSupertypes().equals(supertype))
					lista2.add(carta);
			}
			lista = lista2;
			lista2 = new ArrayList<>();
		}
		
		if(subtypes != null && subtypes!="") {
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
	


}
