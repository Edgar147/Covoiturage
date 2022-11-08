package com.covoiturage.covoiturage.service;

import com.covoiturage.covoiturage.dao.TrajetDAO;
import com.covoiturage.covoiturage.entity.Trajet;
import com.covoiturage.covoiturage.entity.User;
import com.covoiturage.covoiturage.repository.TrajetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Component("trajetService")
public class TrajetServiceImpl implements Services<Trajet> {

	@Autowired
	private TrajetDAO trajetDAO;

	private TrajetRepository trajetRepository;



	// VERY IMPORTANt!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	public TrajetServiceImpl(TrajetRepository ar) {
		this.trajetRepository = ar;
	}

	@Override
	public Trajet findById(int theId) {
		Trajet theTrajet = trajetRepository.findById(theId).get();
		return theTrajet;
	}

	@Override
	public List<Trajet> findAll() {

		return trajetRepository.findAll();
	}

	@Override
	public UserDetails loadUserByUsername(String userName) {
		return null;
	}

	@Override
	public User findByUserName(String userName) {
		return null;
	}


	@Override
	@Transactional
	public void save(Trajet theTrajet) {

		trajetDAO.saveTrajet(theTrajet);


	}

	@Override
	public void deleteById(int t) {
		// TODO Auto-generated method stub
		
	}



}
