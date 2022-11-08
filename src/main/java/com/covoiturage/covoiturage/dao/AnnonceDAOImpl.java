package com.covoiturage.covoiturage.dao;

import com.covoiturage.covoiturage.entity.Annonce;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

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

	@Override
	@Transactional
	public List<Annonce> findAll() {

		// get the current hibernate session
		Session currentSession = entityManager.unwrap(Session.class);

		// create a query
		Query<Annonce> theQuery =
				currentSession.createQuery("from Annonce", Annonce.class);

		// execute query and get result list
		List<Annonce> annonces = theQuery.getResultList();

		// return the results
		return annonces;
	}
}
