package com.crypto_wallet.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.crypto_wallet.services.CoinServiceImp;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }
    @Bean // 
    public RestTemplate restTamplate(){
        return new RestTemplate();
    }
    @Bean 
    public CoinServiceImp coinServiceImp() {
    	
    	return new CoinServiceImp();
    }
}
