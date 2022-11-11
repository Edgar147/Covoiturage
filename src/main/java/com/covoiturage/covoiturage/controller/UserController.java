package com.covoiturage.covoiturage.controller;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.covoiturage.covoiturage.dao.TrajetDAO;
import com.covoiturage.covoiturage.entity.Annonce;
import com.covoiturage.covoiturage.entity.Trajet;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	Logger logger = LoggerFactory.getLogger(UserController.class);

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
						   @Nullable @RequestParam("newPassword") String value) throws MalformedURLException, ParseException, JsonProcessingException {
		URL url=new URL("https://c363cd03-adfa-4394-8a07-3a0a269acdf5.mock.pstmn.io/numeroEtudiant");
		String EtudiantsAPI=theUser.getEtudiantAPI(url);
/*
		JSONParser parse = new JSONParser(EtudiantsAPI);
*/
		//logger.info("bbbbbbbbbbb"+parse.list().toString());
		ObjectMapper mapper = new ObjectMapper();
		List<Integer> list2 = Arrays.asList(mapper.readValue(EtudiantsAPI, Integer[].class));
		logger.info("bbbbbbbbbbb2222222"+ list2.get(0));


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
		User user = userService.findByUserName(login);

		int userId= user.getId();

		theAnnonce.setUserId(userId);





		annonceService.save(theAnnonce);



		Trajet tr = new Trajet(userId, theAnnonce.getId(), userId, 3);
		trajetService.save(tr);


		return "redirect:/home";
	}


	@GetMapping("/liste-annonce")
	public String returnListOfAnnonces(Model theModel) {


		Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
		String login = loggedInUser.getName();
		User user = userService.findByUserName(login);
		int userId= user.getId();

		List<Trajet> trAll=  trajetService.findAll();

		List<Annonce> theAnnonces=  annonceService.findAll();

			List<Integer> annonceIds= new ArrayList<>();
		List<Integer> lesStatus= new ArrayList<>();




		for (int i = 0; i < trAll.size(); i++) {

			//si utilisateur a choisi cette annonec
			if(trAll.get(i).getUserId()==userId){
				annonceIds.add(trAll.get(i).getAnnonceId());
				lesStatus.add(trAll.get(i).getEstAccepte());
				//0-> rien, 1->accepté, 2-> refusé, 3->vous êtes le conducteur 4-> demande envoyée
			}
			else {
				annonceIds.add(-1);
				lesStatus.add(0);
			}


		}
		List<Integer> lastStatus= new ArrayList<>();
		for (int i = 0; i < theAnnonces.size(); i++) {
			boolean trouve=false;
			for (int j = 0; j < annonceIds.size(); j++) {
				if (theAnnonces.get(i).getId() ==annonceIds.get(j) && trouve==false){
					lastStatus.add(lesStatus.get(j));
					trouve=true;
				}

			}
			if(trouve==false){
				lastStatus.add(0);

			}
		}



		// add to the spring model
		theModel.addAttribute("annonces", theAnnonces);
		//theModel.addAttribute("userAnnonceIds", annonceIds);
		theModel.addAttribute("statusList", lastStatus);

		return "liste-annonce";
	}

	@GetMapping("/accepte-annonce")
	public String AccepterAnnonce(@ModelAttribute("annonce_id") int annonce_id, Model theModel) {

		Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
		String login = loggedInUser.getName();
		User user = userService.findByUserName(login);

		int userId= user.getId();

		Annonce theAnnonce = annonceService.findById(annonce_id);

		if(theAnnonce.getUserId()==userId) {
			Trajet tr = new Trajet(userId, annonce_id, theAnnonce.getUserId(),3 );
			trajetService.save(tr);

		}
		else {
			Trajet tr = new Trajet(userId, annonce_id, theAnnonce.getUserId(),4);
			trajetService.save(tr);
		}

		return "redirect:/liste-annonce";
	}




	@GetMapping("/liste-trajets-user")
	public String returnListOfTrajets(Model theModel) {

		List<Trajet> theTrajets = trajetService.findAll();

		Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
		String login = loggedInUser.getName();
		User user = userService.findByUserName(login);

		int userId= user.getId();



		List<Integer> theAnnoncesId = new ArrayList<>();
		List<Integer> lesStatus = new ArrayList<>();

		for (int i = 0; i < theTrajets.size(); i++) {

			if (theTrajets.get(i).getUserId() == userId) {
				theAnnoncesId.add(theTrajets.get(i).getAnnonceId());
				lesStatus.add(theTrajets.get(i).getEstAccepte());
			}
		}
		List<Annonce> theAnnonces= new ArrayList<>();


		for (int i = 0; i < theAnnoncesId.size(); i++) {

			theAnnonces.add(annonceService.findById(theAnnoncesId.get(i)));

		}






		// add to the spring model
		theModel.addAttribute("annonces", theAnnonces);
		theModel.addAttribute("statusList", lesStatus);

		return "liste-trajets-user";
	}





	@GetMapping("/liste-proposition")
	public String returnListOfPropositions(Model theModel) {


		Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
		String login = loggedInUser.getName();
		User user = userService.findByUserName(login);

		int userId= user.getId();


		List<Trajet> theTrajets = trajetService.findAll();



		List<Annonce> theAnnonces = new ArrayList<>();
		List<String> theUsersNomPrenom = new ArrayList<>();
		List<Integer> proposedUserId = new ArrayList<>();
		List<Integer> reponseConducteur = new ArrayList<>();

		for (int i = 0; i < theTrajets.size(); i++) {

			if (theTrajets.get(i).getConducteurId() == userId) {
				theAnnonces.add(annonceService.findById(theTrajets.get(i).getAnnonceId()));
				theUsersNomPrenom.add(userService.findById(theTrajets.get(i).getUserId()).getFirstName()+ " "+userService.findById(theTrajets.get(i).getUserId()).getLastName());
				proposedUserId.add(theTrajets.get(i).getUserId());


				if (theTrajets.get(i).getEstAccepte()==1) {
					reponseConducteur.add(1);
				}
				else if (theTrajets.get(i).getEstAccepte()==2){
					reponseConducteur.add(2);
				}
				else if (theTrajets.get(i).getEstAccepte()==3){
					reponseConducteur.add(3);
				}
				else {
					reponseConducteur.add(0);
				}

			}
		}

		theModel.addAttribute("annonces", theAnnonces);
		theModel.addAttribute("usersNomPrenom", theUsersNomPrenom);
		theModel.addAttribute("proposedUserId", proposedUserId);
		theModel.addAttribute("reponseConducteur", reponseConducteur);


		return "liste-proposition";

	}


	@GetMapping("/accepte-proposition/{id1}/{id2}")
	public String RefuserProposition(@ModelAttribute("id1") int userIdPropose,@ModelAttribute("id2") int annonceId, Model theModel) {

		List<Trajet> theTrajets = trajetService.findAll();


		for (int i = 0; i < theTrajets.size(); i++) {

			if ((theTrajets.get(i).getAnnonceId() == annonceId)&&(theTrajets.get(i).getUserId()==userIdPropose)) {
				Trajet trajetPropose=trajetService.findById(theTrajets.get(i).getId());
				trajetPropose.setEstAccepte(1);
				trajetService.save(trajetPropose);


			}
		}


			return "redirect:/home";

	}



	@GetMapping("/refuse-proposition/{id1}/{id2}")
	public String AccepterProposition(@ModelAttribute("id1") int userIdPropose,@ModelAttribute("id2") int annonceId, Model theModel) {

		List<Trajet> theTrajets = trajetService.findAll();


		for (int i = 0; i < theTrajets.size(); i++) {

			if ((theTrajets.get(i).getAnnonceId() == annonceId)&&(theTrajets.get(i).getUserId()==userIdPropose)) {
				Trajet trajetPropose=trajetService.findById(theTrajets.get(i).getId());
				trajetPropose.setEstAccepte(2);
				trajetService.save(trajetPropose);


			}
		}


		return "redirect:/home";

	}



	@GetMapping("/details")
	public String DetailsTrajet(@ModelAttribute("annonce_id") int annonceId, Model theModel) {

		List<Integer> userIds= new ArrayList<>();

		List<Trajet> theTrajets = trajetService.findAll();
		List<User> userList =new ArrayList<>();

		for (int i = 0; i < theTrajets.size(); i++) {
			if((theTrajets.get(i).getAnnonceId()==annonceId)&&(theTrajets.get(i).getEstAccepte() !=0)&&(theTrajets.get(i).getEstAccepte() !=2)&&(theTrajets.get(i).getEstAccepte() !=4)){
				userIds.add(theTrajets.get(i).getUserId());
			}
		}


		for (int i = 0; i < userIds.size(); i++) {
			userList.add(userService.findById(userIds.get(i)));
		}
		theModel.addAttribute("userList", userList);



		return "details";
		}

}
