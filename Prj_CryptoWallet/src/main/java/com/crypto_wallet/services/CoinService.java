package com.crypto_wallet.services;

import com.crypto_wallet.entities.Coin;
import java.util.List;

public interface CoinService {
    List<Coin> getAllCoins();
    Coin getCoinById(int id);
    Coin getCoinBySymbol(String symbol);
    void saveCoin(Coin coin);
    void updateCoin(int id, Coin updatedCoin);
    void deleteCoin(int id);
    List<Coin> getCoinsByPriceRange(double minPrice, double maxPrice);
}
