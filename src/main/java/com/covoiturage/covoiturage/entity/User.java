package com.covoiturage.covoiturage.entity;

import java.util.*;

import javax.persistence.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.covoiturage.covoiturage.entity.Role;


@Entity
@Table(name = "user")
public class User implements UserDetails {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "email")
	private String email;


	@Column(name = "password")
	private String password;

	@Column(name = "role")
	private String role;


	public Collection<String> getRoles() {
		Collection<String> c =new ArrayList<String>();
	
		c.add("USER");
		return c;
	}


	
	
	
	
	
	
	
	
	













	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}


	public User() {
	


	}

	public User(int id, String firstName, String lastName, String email) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}

	public User(String firstName, String lastName, String email) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}


	public String getPassword() {

		return password;
	}

	public void setPassword(String password) {

		this.password = password;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	

	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + "]";
	}




	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
	    final Set<GrantedAuthority> authorities = new HashSet<>();

        authorities.add(new SimpleGrantedAuthority("USER"));
        return authorities;
	}













	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return null;
	}













	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}













	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return false;
	}













	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}













	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}






}