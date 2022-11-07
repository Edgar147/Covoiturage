package com.covoiturage.covoiturage.repository;

import com.covoiturage.covoiturage.entity.Trajet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrajetRepository extends JpaRepository<Trajet, Integer> {

	// c'est tout, .... data jpa va faire les methodes  necessaires

}
