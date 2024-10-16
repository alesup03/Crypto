package com.monkeysncode.servicies;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

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
	
	public List<Card> findAllSorted(String sort, boolean desc){
		if(desc)
		return cardDAO.findAll(Sort.by(Sort.Direction.DESC, sort));
		else return cardDAO.findAll(Sort.by(Sort.Direction.ASC, sort));
	}
	
	public List<Card> findByName(String name){
		
		List<Card> lista = cardDAO.findByName(name);
		return lista;
		
	}
	
	public List<Card> filterByParam(HashMap<String,String> filters,List<Card> lista){
		
		ArrayList<Card> lista2 = new ArrayList<>();
		
		if (checkHashNull(filters))
			return lista;
		
		//per ogni parametro inserito viene applicato il filtro richiesto

		if(filters.get("set") != null && filters.get("set")!="") {
			for (Card carta : lista){
				if(containsIgnoreCase(carta.getSet(), filters.get("set")))
					lista2.add(carta);
			}
			lista = lista2;
			lista2 = new ArrayList<>();
		}
		
		if(filters.get("types") != null && filters.get("types")!="") {
			for (Card carta : lista){
				if(carta.getTypes().equals(filters.get("types")))
					lista2.add(carta);
			}
			lista = lista2;
			lista2 = new ArrayList<>();
		}
		
		if(filters.get("name")!= null && filters.get("name")!="") {
			for (Card carta : lista){
				if(containsIgnoreCase(carta.getName(), filters.get("name")))
					lista2.add(carta);
			}
			lista = lista2;
			lista2 = new ArrayList<>();
		}
		
		if(filters.get("rarity") != null && filters.get("rarity")!="") {
			for (Card carta : lista){
				if(carta.getRarity().equals(filters.get("rarity")))
					lista2.add(carta);
			}
			lista = lista2;
			lista2 = new ArrayList<>();
		}
		
		if(filters.get("supertype") != null && filters.get("supertype")!="") {
			for (Card carta : lista){
				if(carta.getSupertypes().equals(filters.get("supertype")))
					lista2.add(carta);
			}
			lista = lista2;
			lista2 = new ArrayList<>();
		}
		
		if(filters.get("subtypes") != null && filters.get("subtypes")!="") {
			for (Card carta : lista){
				if(carta.getSubtypes().equals(filters.get("subtypes")))
					lista2.add(carta);
			}
			lista = lista2;
			lista2 = new ArrayList<>();
		}
		
		return lista;

		
	}
	
	private boolean checkHashNull(HashMap<String, String> filters) {
		int count=0;
		for (String i : filters.keySet()) {
			  if(filters.get(i)==null)
				  count++;
			}
		if(count==filters.size())
			return true;
		return false;
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
				if(containsIgnoreCase(carta.getSet(), set))
					lista2.add(carta);
			}
			lista = lista2;
			lista2 = new ArrayList<>();
		}
		
		if(types != null && types!="") {
			for (Card carta : lista){
				if(carta.getTypes().equals(types))
					lista2.add(carta);
			}
			lista = lista2;
			lista2 = new ArrayList<>();
		}
		
		if(name != null && name!="") {
			for (Card carta : lista){
				if(containsIgnoreCase(carta.getName(), name))
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
		
		
		return lista;
	}
	
	//
	private boolean containsIgnoreCase(String str, String check) {
		if (check == null || check.length()==0)
			return true;
		if (str.toLowerCase().contains(check.toLowerCase()))
			return true;
	    return false;
		
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
	
	public List<Card> getCardsByPage(List<Card> allCards, int page, int cardsPerPage) {
	    // Assicurati che la pagina non sia inferiore a 1
	    if (page < 1) {
	        page = 1;
	    }

	    // Calcola l'indice di partenza
	    int startIndex = (page - 1) * cardsPerPage;

	    // Se l'indice di partenza è oltre la dimensione della lista, restituisci una lista vuota
	    if (startIndex >= allCards.size()) {
	        return new ArrayList<>();
	    }

	    // Calcola l'indice finale
	    int endIndex = Math.min(startIndex + cardsPerPage, allCards.size());

	    // Restituisci il sottoinsieme della lista
	    return new ArrayList<>(allCards.subList(startIndex, endIndex));
	}
	
	public Optional<Card> getCardById(String cardId){
		return cardDAO.findById(cardId);
	}

	public Card findById(String cardId) {
	    Optional<Card> card = cardDAO.findById(cardId);
	    return card.orElse(null); // Restituisce la Card se presente, altrimenti null
	}
	 


}
