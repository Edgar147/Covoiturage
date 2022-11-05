package com.covoiturage.covoiturage.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.covoiturage.covoiturage.dao.UserDAO;
import com.covoiturage.covoiturage.entity.User;
import com.covoiturage.covoiturage.service.Services;

@Controller
public class UserController {

	@Autowired
	@Qualifier("userService")
	private Services<User> userService;


	@Autowired
	private UserDAO ud;

	@GetMapping("/")
	public String LoginPage() {

		return "login-page";
	}
	
	
	@GetMapping("/home")
	public String Homepage() {
		
		return "home";
	}

	@GetMapping("/registration")
	public String UserRegistration(@ModelAttribute("user") User theUser) {

		return "saveUser";
	}
	
	@GetMapping("/registration-success")
	public String UserRegistrationSuccess(@ModelAttribute("user") User theUser) {

		return "registration-success";
	}
	
	
	@PostMapping("/saveUser")
	public String saveUser(@ModelAttribute("user") User theUser,
			@Nullable @RequestParam("newPassword") String value) {

	

		if (value != null) {
			theUser.setPassword(value);
		}

		userService.save(theUser);

		return "redirect:/registration-success";
	}
	
	
	
	
	
}
