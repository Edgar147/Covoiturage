package com.covoiturage.covoiturage.dao;

import com.covoiturage.covoiturage.entity.Trajet;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository //
public class TrajetDAOImpl implements TrajetDAO {

	@Autowired
	private EntityManager entityManager;



	// set up constructor injection
	@Autowired
	public TrajetDAOImpl(EntityManager theEntityManager) {
		entityManager = theEntityManager;
	}



	@Override
	public void saveTrajet(Trajet theTrajet) {

		Session currentSession = entityManager.unwrap(Session.class);
		currentSession.saveOrUpdate(theTrajet); // if id is 0->save, else->update

	}

}
