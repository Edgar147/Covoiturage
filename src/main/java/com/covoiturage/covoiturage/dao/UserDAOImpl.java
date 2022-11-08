package com.covoiturage.covoiturage.dao;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.covoiturage.covoiturage.entity.User;

@Repository 
public class UserDAOImpl implements UserDAO {

	// define field for entityManager, EM is for working with entities(search by id,
	// remove...
	private EntityManager entityManager; //ça vient de JPA

	// set up constructor injection
	@Autowired
	public UserDAOImpl(EntityManager theEntityManager) {
		entityManager = theEntityManager;
	}



	@Override
	public void saveUser(User theUser) {



		Session currentSession = entityManager.unwrap(Session.class);
		currentSession.saveOrUpdate(theUser); // if id is 0->save, else->update

	}

	@Override
	public User findByUserName(String userName) {
		Session currentSession = entityManager.unwrap(Session.class);

		// now read from database using username
		Query<User> theQuery = currentSession.createQuery("from User where firstName=:uName", User.class);
		theQuery.setParameter("uName", userName);
		User theUser = null;
		try {
			theUser = theQuery.getSingleResult();
		} catch (Exception e) {
			theUser = null;
		}

		return theUser;
	}



}
