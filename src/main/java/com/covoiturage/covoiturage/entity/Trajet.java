package com.covoiturage.covoiturage.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "trajet")
public class Trajet {

/*	@ManyToMany
	@JoinTable(name = "user_annonce", joinColumns = @JoinColumn(name = "annonce_id"), inverseJoinColumns = @JoinColumn(name = "club_id"))
	private List<User> users;*/

/*
	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
*/

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "user_id")
	private int userId;

	@Column(name = "annonce_id")
	private int annonceId;


	public Trajet(int id, int userId, int annonceId) {
		this.id = id;
		this.userId = userId;
		this.annonceId = annonceId;

	}

	public Trajet(int userId, int annonceId) {
		this.userId = userId;
		this.annonceId = annonceId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getAnnonceId() {
		return annonceId;
	}

	public void setAnnonceId(int annonceId) {
		this.annonceId = annonceId;
	}
}
