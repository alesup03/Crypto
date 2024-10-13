package com.monkeysncode.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import com.monkeysncode.entites.Role;

public interface RoleDAO extends JpaRepository<Role, String> {
	
}
