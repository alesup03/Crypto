package com.crypto_wallet.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crypto_wallet.entities.User;
import com.crypto_wallet.entities.Wallet;

public interface WalletDAO extends JpaRepository<Wallet, Integer> {

	Wallet  findByUserId(int id);

	boolean existsByUser(User user);

	Wallet findById(int id);


}
