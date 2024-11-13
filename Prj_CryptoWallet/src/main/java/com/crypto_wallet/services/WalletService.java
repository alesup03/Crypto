package com.crypto_wallet.services;

import java.util.List;

import com.crypto_wallet.entities.Wallet;

public interface WalletService { //classe astratta walletService 

	List<Wallet> getWalletUser(int user_id);
	void addCoinToWallet(int coin_id, int quantity, int wallet_id, int user_id);
	
}
