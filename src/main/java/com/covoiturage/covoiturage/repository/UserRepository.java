package com.covoiturage.covoiturage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.covoiturage.covoiturage.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	// c'est tout, .... data jpa va faire les methodes  necessaires

}
