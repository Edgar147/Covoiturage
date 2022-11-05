package com.covoiturage.covoiturage.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.covoiturage.covoiturage.dao.UserDAO;
import com.covoiturage.covoiturage.entity.User;
import com.covoiturage.covoiturage.entity.Role;
import com.covoiturage.covoiturage.repository.UserRepository;

@Service
@Component("userService")
public class UserServiceImpl implements Services<User> {

	@Autowired
	private UserDAO userDao;

	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	// VERY IMPORTANt!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	public UserServiceImpl(UserRepository ur) {
		this.userRepository = ur;
	}

	@Override
	public User findById(int theId) {
		User theUser = userRepository.findById(theId).get();
		return theUser;
	}

	@Override
	public List<User> findAll() {

		return userRepository.findAll();
	}

	@Override
	public User findByUserName(String userName) {
		return userDao.findByUserName(userName);
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		User user = userDao.findByUserName(userName);
		if (user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(user.getFirstName(), user.getPassword(),user.getAuthorities());	}

	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public void save(User theUser) {
		theUser.setPassword(passwordEncoder.encode(theUser.getPassword()));
		userDao.saveUser(theUser);

	}

	@Override
	public void deleteById(int t) {
		// TODO Auto-generated method stub
		
	}



}
