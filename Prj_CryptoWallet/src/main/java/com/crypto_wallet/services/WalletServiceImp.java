package com.crypto_wallet.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crypto_wallet.entities.*;
import com.crypto_wallet.repos.CoinDAO;
import com.crypto_wallet.repos.UserDAO;
import com.crypto_wallet.repos.WalletDAO;

@Service
public class WalletServiceImp implements WalletService {

	@Autowired
	private UserDAO udao; 
	@Autowired
	private WalletDAO wdao;
	@Autowired 
	private CoinDAO cdao;
	@Override
	public List<Wallet> getWalletUser(int user_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addCoinToWallet(int coin_id, int quantity, int wallet_id, int user_id) {
		// TODO Auto-generated method stub
		boolean exits = udao.existsById(user_id);
		User user = udao.findById(user_id);
		Wallet wallet = wdao.findById(wallet_id);
		
		Coin coin = cdao.findById(coin_id);
		if(wallet!= null) {
			wallet.setQuantity(quantity);
			wallet.setCoins(coin);
			wallet.setUser(user);
		}
				
	}

}
