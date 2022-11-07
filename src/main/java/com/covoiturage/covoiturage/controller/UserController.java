package com.covoiturage.covoiturage.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.covoiturage.covoiturage.dao.TrajetDAO;
import com.covoiturage.covoiturage.entity.Annonce;
import com.covoiturage.covoiturage.entity.Trajet;
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
	@Qualifier("annonceService")
	private Services<Annonce> annonceService;

	@Autowired
	@Qualifier("trajetService")
	private Services<Trajet> trajetService;


	@Autowired
	private UserDAO ud;

	@Autowired
	private TrajetDAO td;

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


	@PostMapping("/saveUser")
	public String saveUser(@ModelAttribute("user") User theUser,
						   @Nullable @RequestParam("newPassword") String value) {

		if (value != null) {
			theUser.setPassword(value);
		}
		userService.save(theUser);
		return "redirect:/registration-success";
	}


	@GetMapping("/registration-success")
	public String UserRegistrationSuccess(@ModelAttribute("user") User theUser) {

		return "registration-success";
	}

	@GetMapping("/creer-annonce")
	public String CreateAnnoncePage(@ModelAttribute("annonce") Annonce theAnnonce) {

		return "saveAnnonce";
	}

	@PostMapping("/saveAnnonce")
	public String saveAnnonce(@ModelAttribute("annonce") Annonce theAnnonce) {


		Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
		String login = loggedInUser.getName();
		User user = ud.findByUserName(login);

		int userId= user.getId();

		theAnnonce.setUserId(userId);

		annonceService.save(theAnnonce);
		return "redirect:/home";
	}


	@GetMapping("/liste-annonce")
	public String returnListOfAnnonces(Model theModel) {

		List<Annonce> theAnnonces = annonceService.findAll();

		// add to the spring model
		theModel.addAttribute("annonces", theAnnonces);

		return "liste-annonce";
	}

	@GetMapping("/accepte-annonce")
	public String AccepterAnnonce(@ModelAttribute("annonce_id") int annonce_id, Model theModel) {




		Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
		String login = loggedInUser.getName();
		User user = ud.findByUserName(login);

		int userId= user.getId();

		//Annonce theAnnonce = annonceService.findById(annonce_id);
		Trajet tr=new Trajet(userId,annonce_id);

		trajetService.save(tr);

		return "redirect:/home";
	}



}
