package com.monkeysncode.repos;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.monkeysncode.entites.DeckImg;

public interface DeckImgDAO extends JpaRepository<DeckImg, Long>{
	
	public List<DeckImg> findAll();
}
