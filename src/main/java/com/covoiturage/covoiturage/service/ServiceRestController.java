package com.covoiturage.covoiturage.service;

import com.covoiturage.covoiturage.controller.UserController;
import com.covoiturage.covoiturage.entity.Annonce;
import com.covoiturage.covoiturage.entity.Trajet;
import com.covoiturage.covoiturage.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/services")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ServiceRestController {
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


    public static String getApi(URL url) {
        try (InputStream input = url.openStream()) {
            InputStreamReader isr = new InputStreamReader(input);
            BufferedReader reader = new BufferedReader(isr);
            StringBuilder json = new StringBuilder();
            int c;
            while ((c = reader.read()) != -1) {
                json.append((char) c);
            }
            return json.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/users")
    public List<User> findAllUsers() throws MalformedURLException, JsonProcessingException {

        URL url=new URL("http://localhost:8090/dao/users");
        String usersAPI=this.getApi(url);

        ObjectMapper mapper = new ObjectMapper();
        List<User> listUsersAPI = Arrays.asList(mapper.readValue(usersAPI, User[].class));
//logger.info("xxxxxxxxxxxxxxxxxxxxx"+listUsersAPI.get(0).getFirstName());

        return listUsersAPI;
    }


    @GetMapping("/user")
    public User findByIdUser(int id) throws MalformedURLException, JsonProcessingException {
        URL url=new URL("http://localhost:8090/dao/user/"+id);
        String usersAPI=this.getApi(url);

        ObjectMapper mapper = new ObjectMapper();
        User UsersAPI = mapper.readValue(usersAPI, User.class);
       // logger.info("fffffffffffffffffffff"+listUsersAPI.getFirstName());
        return UsersAPI;
    }




    @GetMapping("/userByName")
    public User findByFirstNameUser(String name) throws MalformedURLException, JsonProcessingException {
        URL url=new URL("http://localhost:8090/dao/userName/"+name);
        String usersAPI=this.getApi(url);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);

        User UsersAPI = mapper.readValue(usersAPI, User.class);
        logger.info("xxxxxxxxxxxxxxxx"+UsersAPI.getFirstName());
        return UsersAPI;
    }

    @GetMapping(value = "/saveUser", produces = "application/json")
    public User saveUser(User user) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject personJsonObject = new JSONObject();

        personJsonObject.put("firstName",user.getFirstName());
        personJsonObject.put("lastName",user.getLastName());
        personJsonObject.put("ine",user.getIne());
        personJsonObject.put("email",user.getEmail());
        personJsonObject.put("password",user.getPassword());

        String createPersonUrl = "http://localhost:8090/dao/saveUser";


        ObjectMapper objectMapper = new ObjectMapper();

        HttpEntity<String> request =
                new HttpEntity<String>(personJsonObject.toString(), headers);
        String personResultAsJsonStr =
                restTemplate.postForObject(createPersonUrl, request, String.class);//C'est ça qui post
/*
        JsonNode root = objectMapper.readTree(personResultAsJsonStr);
*//*
logger.info("TTTTTTTTTTTTTTTTTTTTT"+personResultAsJsonStr);*/



        return user;


    }



    @GetMapping("/trajets")
    public List<Trajet> findAllTrajets() throws MalformedURLException, JsonProcessingException {

        URL url=new URL("http://localhost:8090/dao/trajets");
        String usersAPI=this.getApi(url);

        ObjectMapper mapper = new ObjectMapper();
        List<Trajet> listTrajetsAPI = Arrays.asList(mapper.readValue(usersAPI, Trajet[].class));
//logger.info("xxxxxxxxxxxxxxxxxxxxx"+listUsersAPI.get(0).getFirstName());

        return listTrajetsAPI;
    }


    @GetMapping("/trajet")
    public Trajet findByIdTrajet(int id) throws MalformedURLException, JsonProcessingException {
        URL url=new URL("http://localhost:8090/dao/trajet/"+id);
        String trajetAPI=this.getApi(url);

        ObjectMapper mapper = new ObjectMapper();
        Trajet trajet = mapper.readValue(trajetAPI, Trajet.class);
        // logger.info("fffffffffffffffffffff"+listUsersAPI.getFirstName());
        return trajet;
    }

    @GetMapping(value = "/saveTrajet", produces = "application/json")
    public Trajet saveTrajet(Trajet trajet) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject personJsonObject = new JSONObject();

        if(trajet.getId()>0) {
            personJsonObject.put("id", trajet.getId());
        }
        personJsonObject.put("userId",trajet.getUserId());
        personJsonObject.put("annonceId",trajet.getAnnonceId());
        personJsonObject.put("estAccepte",trajet.getEstAccepte());
        personJsonObject.put("conducteurId",trajet.getConducteurId());

        String createPersonUrl = "http://localhost:8090/dao/saveTrajet";


        ObjectMapper objectMapper = new ObjectMapper();

        HttpEntity<String> request =
                new HttpEntity<String>(personJsonObject.toString(), headers);
        String personResultAsJsonStr =
                restTemplate.postForObject(createPersonUrl, request, String.class);//C'est ça qui post
/*
        JsonNode root = objectMapper.readTree(personResultAsJsonStr);
*//*
logger.info("TTTTTTTTTTTTTTTTTTTTT"+personResultAsJsonStr);*/



        return trajet;


    }







    @GetMapping("/annonces")
    public List<Annonce> findAllAnnonces() throws MalformedURLException, JsonProcessingException {

        URL url=new URL("http://localhost:8090/dao/annonces");
        String usersAPI=this.getApi(url);

        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();// Pour que jackson comprends le LocalDateTime

        List<Annonce> listAnnoncesAPI = Arrays.asList(mapper.readValue(usersAPI, Annonce[].class));
//logger.info("xxxxxxxxxxxxxxxxxxxxx"+listUsersAPI.get(0).getFirstName());

        return listAnnoncesAPI;
    }

    @GetMapping("/annonce")
    public Annonce findByIdAnnonce(int id) throws MalformedURLException, JsonProcessingException {
        URL url=new URL("http://localhost:8090/dao/annonce/"+id);
        String annonceAPI=this.getApi(url);

        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();// Pour que jackson comprends le LocalDateTime

        Annonce annonce = mapper.readValue(annonceAPI, Annonce.class);
        // logger.info("fffffffffffffffffffff"+listUsersAPI.getFirstName());
        return annonce;
    }



    @GetMapping(value = "/saveAnnonce", produces = "application/json")
    public Annonce saveAnnonce(Annonce annonce) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject personJsonObject = new JSONObject();

        personJsonObject.put("userId",annonce.getUserId());
        String new_date= String.valueOf(annonce.getDate());
        new_date = new_date.replace(" ", "T");
        personJsonObject.put("date",new_date);
        personJsonObject.put("depart",annonce.getDepart());
        personJsonObject.put("arrive",annonce.getArrive());

        String createPersonUrl = "http://localhost:8090/dao/saveAnnonce";


        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();// Pour que jackson comprends le LocalDateTime

        HttpEntity<String> request =
                new HttpEntity<String>(personJsonObject.toString(), headers);
        String personResultAsJsonStr =
                restTemplate.postForObject(createPersonUrl, request, String.class);//C'est ça qui post
/*
        JsonNode root = objectMapper.readTree(personResultAsJsonStr);
*//*
logger.info("TTTTTTTTTTTTTTTTTTTTT"+personResultAsJsonStr);*/



        return annonce;


    }



    @PostMapping(value = "/saveUserPost", produces = "application/json")
    public void saveUserPost(@RequestBody User user) {

        userService.save(user);
    }



    @PostMapping(value = "/saveTrajetPost", produces = "application/json")
    public void saveTrajetPost(@RequestBody Trajet trajet) {
        trajetService.save(trajet);
    }



    @PostMapping(value = "/saveAnnoncePost", produces = "application/json")
    public void saveAnnoncePost(@RequestBody Annonce annonce) {
        annonceService.save(annonce);
    }



/*

    @GetMapping(value = "/nameByUser", consumes = "application/json", produces = "application/json")
    public User saveUser( User user) {


    }
*/
@GetMapping("/userByNamePost/{name}")
public User findByFirstNameUserPost(@ModelAttribute("name") String name) throws MalformedURLException, JsonProcessingException {
    URL url=new URL("http://localhost:8090/dao/userName/"+name);
    String usersAPI=this.getApi(url);

    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);

    User UsersAPI = mapper.readValue(usersAPI, User.class);
    logger.info("xxxxxxxxxxxxxxxx"+UsersAPI.getFirstName());
    return UsersAPI;
}


    @GetMapping("/userPost/{id}")
    public User findByIdUserPostId(@ModelAttribute("id") int id) throws MalformedURLException, JsonProcessingException {
        URL url=new URL("http://localhost:8090/dao/user/"+id);
        String usersAPI=this.getApi(url);

        ObjectMapper mapper = new ObjectMapper();
        User UsersAPI = mapper.readValue(usersAPI, User.class);
        // logger.info("fffffffffffffffffffff"+listUsersAPI.getFirstName());
        return UsersAPI;
    }


    @GetMapping("/trajetPost/{id}")
    public Trajet findByTrajetPostId(@ModelAttribute("id") int id) throws MalformedURLException, JsonProcessingException {
        URL url=new URL("http://localhost:8090/dao/trajet/"+id);
        String usersAPI=this.getApi(url);

        ObjectMapper mapper = new ObjectMapper();
        Trajet UsersAPI = mapper.readValue(usersAPI, Trajet.class);
        // logger.info("fffffffffffffffffffff"+listUsersAPI.getFirstName());
        return UsersAPI;
    }

    @GetMapping("/annoncePost/{id}")
    public Annonce findByAnnoncePostId(@ModelAttribute("id") int id) throws MalformedURLException, JsonProcessingException {
        URL url=new URL("http://localhost:8090/dao/annonce/"+id);
        String usersAPI=this.getApi(url);

        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();

        Annonce UsersAPI = mapper.readValue(usersAPI, Annonce.class);
        // logger.info("fffffffffffffffffffff"+listUsersAPI.getFirstName());
        return UsersAPI;
    }


    @GetMapping(value = "/sendError", produces = "application/json")
    public void ErrorPage() throws MalformedURLException, JsonProcessingException {
/*        URL url=new URL("http://localhost:8090/present/errorLogin");

        // logger.info("fffffffffffffffffffff"+listUsersAPI.getFirstName());
        return "redirect:http://localhost:8090/present/errorLogin";*/

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject personJsonObject = new JSONObject();

        personJsonObject.put("error",1);

        String createPersonUrl = "http://localhost:8090/present/errorLogin";


        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();// Pour que jackson comprends le LocalDateTime

        HttpEntity<String> request =
                new HttpEntity<String>(personJsonObject.toString(), headers);
        String personResultAsJsonStr =
                restTemplate.postForObject(createPersonUrl, request, String.class);//C'est ça qui post






    }




}
