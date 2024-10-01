package com.monkeysncode.repos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.monkeysncode.entites.Card;
import com.monkeysncode.entites.Deck;
import com.monkeysncode.entites.User;
import com.monkeysncode.entites.UserCards;

public interface UserCardDAO extends JpaRepository<UserCards, Long> {

	public List<UserCards> findByUserId(String userId);
	
	public Optional<UserCards> findByUserAndCard(User user, Card card);


}
