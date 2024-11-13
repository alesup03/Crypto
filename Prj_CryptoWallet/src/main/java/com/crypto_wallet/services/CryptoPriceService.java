package com.crypto_wallet.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CryptoPriceService {
	
	private final RestTemplate restTemplate ;

	public double getPriceCrypto(String cryptoS, String currency) {
		String url = "https://api.coingecko.com/api/v3/simple/price?ids=" + cryptoS + "&vs_currencies=" + currency;
		
		String resp = restTemplate.getForObject(url,String.class);
	  JSONObject json = new JSONObject(response);
    double price = json.getJSONObject(cryptoS).getDouble(currency);
		return price;
        
	}
}
