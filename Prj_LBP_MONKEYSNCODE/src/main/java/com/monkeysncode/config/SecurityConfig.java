package com.monkeysncode.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.monkeysncode.servicies.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

		private final UserService serviceUser;

		//chiamata a user service
	    public SecurityConfig(UserService serviceUser) {
	        this.serviceUser = serviceUser;
	    }
	    //filti applicati a ogni richiesta http

	    @Bean
	    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    	 return http
	    			 	.csrf(csrf -> csrf.disable())
	    	            .authorizeHttpRequests(auth -> {

	    	                auth.requestMatchers("/").permitAll();//accesso a tutti alla home page
	    	                auth.requestMatchers("/cards").permitAll();//accesso a tutti al json di cards
	    	                auth.anyRequest().authenticated();//richiesta di essere autenticato a tutte le altre pagine

	    	            })
	    	            .oauth2Login(oauth -> oauth
	    	                .successHandler(oAuth2AuthenticationSuccessHandler()) 
	    	            )
	    	            .build();
	    	    }

	    //gestione autenticazione

	    @Bean
	    public AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler() {
	        return (request, response, authentication) -> {
	            OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

	            serviceUser.saveOrUpdateUser(oAuth2User); 
	            response.sendRedirect("http://localhost:8080/Secured");
	        };
	    }
	}

