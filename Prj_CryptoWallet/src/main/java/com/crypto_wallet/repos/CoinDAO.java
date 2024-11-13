package com.crypto_wallet.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import com.crypto_wallet.entities.Coin;
import java.util.List;

public interface CoinDAO extends JpaRepository<Coin, Integer> {
  
    // Trova una moneta per simbolo (es. "BTC")
    Coin findBySymbols(String symbols);
    
    // Trova tutte le monete che hanno un prezzo maggiore di un certo valore
    List<Coin> findByPriceGreaterThan(double minPrice);
    
    // Trova tutte le monete che hanno un prezzo compreso tra due valori
    List<Coin> findByPriceBetween(double minPrice, double maxPrice);
    
    // Trova tutte le monete per nome, con ricerca parziale (utile per la ricerca)
    List<Coin> findByNameContaining(String name);
}
