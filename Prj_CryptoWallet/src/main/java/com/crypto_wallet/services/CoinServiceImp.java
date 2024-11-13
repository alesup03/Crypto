package com.crypto_wallet.services;

import com.crypto_wallet.entities.Coin;
import com.crypto_wallet.repos.CoinDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CoinServiceImp implements CoinService {

    @Autowired
    private CoinDAO coinDAO;

    @Override
    public List<Coin> getAllCoins() {
        return coinDAO.findAll();
    }

    @Override
    public Coin getCoinById(int id) {
        return coinDAO.findById(id).orElse(null);
    }

    @Override
    public Coin getCoinBySymbol(String symbol) {
        return coinDAO.findBySymbols(symbol);
    }

    @Override
    public void saveCoin(Coin coin) {
        coinDAO.save(coin);
    }

    @Override
    public void updateCoin(int id, Coin updatedCoin) {
        Coin existingCoin = coinDAO.findById(id).orElse(null);
        if (existingCoin != null) {
            existingCoin.setName(updatedCoin.getName());
            existingCoin.setSymbols(updatedCoin.getSymbols());
            existingCoin.setImg(updatedCoin.getImg());
            existingCoin.setPrice(updatedCoin.getPrice());
            coinDAO.save(existingCoin);
        }
    }

    @Override
    public void deleteCoin(int id) {
        coinDAO.deleteById(id);
    }

    @Override
    public List<Coin> getCoinsByPriceRange(double minPrice, double maxPrice) {
        return coinDAO.findByPriceBetween(minPrice, maxPrice);
    }
}
