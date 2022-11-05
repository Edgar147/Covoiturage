package com.covoiturage.covoiturage.service;

import com.covoiturage.covoiturage.dao.AnnonceDAO;
import com.covoiturage.covoiturage.dao.UserDAO;
import com.covoiturage.covoiturage.entity.Annonce;
import com.covoiturage.covoiturage.entity.Role;
import com.covoiturage.covoiturage.entity.User;
import com.covoiturage.covoiturage.repository.AnnonceRepository;
import com.covoiturage.covoiturage.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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
