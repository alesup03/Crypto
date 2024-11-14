package com.crypto_wallet.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.crypto_wallet.entities.Coin;
import com.crypto_wallet.services.CoinServiceImp;

@RestController
@RequestMapping("/crypto")
public class CoinController {

	private final CoinServiceImp coinServiceImp;
	
	public CoinController(CoinServiceImp coinServiceImp) {
		this.coinServiceImp = coinServiceImp;
	}
	@GetMapping("all")
	public ResponseEntity<?> getallCoin(){
		return new ResponseEntity<>(coinServiceImp.getAllCoins(),HttpStatus.OK);
		
	}
}
