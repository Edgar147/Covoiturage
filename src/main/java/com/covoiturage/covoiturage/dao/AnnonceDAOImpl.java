package com.covoiturage.covoiturage.dao;

import com.covoiturage.covoiturage.entity.Annonce;
import com.covoiturage.covoiturage.entity.Role;
import com.covoiturage.covoiturage.entity.User;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository //
public class AnnonceDAOImpl implements AnnonceDAO {

	@Autowired
	private EntityManager entityManager;



	// set up constructor injection
	@Autowired
	public AnnonceDAOImpl(EntityManager theEntityManager) {
		entityManager = theEntityManager;
	}



	@Override
	public void saveAnnonce(Annonce theAnnonce) {

		Session currentSession = entityManager.unwrap(Session.class);
		currentSession.saveOrUpdate(theAnnonce); // if id is 0->save, else->update

	}

}
