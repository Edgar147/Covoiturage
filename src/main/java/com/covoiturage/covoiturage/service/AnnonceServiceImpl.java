package com.covoiturage.covoiturage.service;

import com.covoiturage.covoiturage.dao.AnnonceDAO;
import com.covoiturage.covoiturage.entity.Annonce;
import com.covoiturage.covoiturage.entity.User;
import com.covoiturage.covoiturage.repository.AnnonceRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Component("annonceService")
public class AnnonceServiceImpl implements Services<Annonce> {

	@Autowired
	private AnnonceDAO annonceDAO;

	private AnnonceRepository annonceRepository;



	// VERY IMPORTANt!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	public AnnonceServiceImpl(AnnonceRepository ar) {
		this.annonceRepository = ar;
	}

	@Override
	public Annonce findById(int theId) {
		Annonce theAnnonce = annonceRepository.findById(theId).get();
		return theAnnonce;
	}

	@Override
	public List<Annonce> findAll() {

		return annonceRepository.findAll();
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
	public void save(Annonce theAnnonce) {

		annonceDAO.saveAnnonce(theAnnonce);

	}

	@Override
	public void deleteById(int t) {
		// TODO Auto-generated method stub
		
	}



}
