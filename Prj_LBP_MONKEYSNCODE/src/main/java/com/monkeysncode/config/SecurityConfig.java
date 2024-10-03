package com.monkeysncode.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.monkeysncode.servicies.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	

    private final UserService serviceUser;

    public SecurityConfig(@Lazy UserService serviceUser) {//Lazy usato per evitare reference circolare tra userservice e security config
        this.serviceUser = serviceUser;
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // Implementazione di BCrypt
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/", "/login", "/register", "/css/**", "/js/**").permitAll();  // Accesso libero a home, login, registrazione e risorse statiche
                    auth.anyRequest().authenticated();  // Tutte le altre pagine richiedono autenticazione
                })
                .formLogin(form -> form
                    .loginPage("/login")  // Definisci la pagina di login personalizzata
                    .defaultSuccessUrl("/", true)  // Reindirizza dopo il successo del login form-based
                    .loginProcessingUrl("/login")
                    .usernameParameter("email")  //set il controllo di spring a email invece del default username
                    .passwordParameter("password")
                    .permitAll()
                )
                .oauth2Login(oauth -> oauth
                    .loginPage("/login")  // Usa la stessa pagina di login per OAuth2
                    .successHandler(oAuth2AuthenticationSuccessHandler())
                )
                .build();
    }

    @Bean
    public AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler() {
        return (request, response, authentication) -> {
            OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
            serviceUser.saveOrUpdateUser(oAuth2User);
            response.sendRedirect("/");
        };
    }
}

