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

	    public SecurityConfig(UserService serviceUser) {
	        this.serviceUser = serviceUser;
	    }

	    @Bean
	    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    	 return http
	    			 	.csrf(csrf -> csrf.disable())
	    	            .authorizeHttpRequests(auth -> {
	    	                auth.requestMatchers("/").permitAll();
	    	                auth.requestMatchers("/cards").permitAll();
	    	                auth.anyRequest().authenticated();
	    	            })
	    	            .oauth2Login(oauth -> oauth
	    	                .successHandler(oAuth2AuthenticationSuccessHandler()) 
	    	            )
	    	            .build();
	    	    }

	    @Bean
	    public AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler() {
	        return (request, response, authentication) -> {
	            OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

	            serviceUser.saveOrUpdateUser(oAuth2User); 
	            response.sendRedirect("http://localhost/cartellaPHP/lbpdemo/cards.php");
	        };
	    }
	}

