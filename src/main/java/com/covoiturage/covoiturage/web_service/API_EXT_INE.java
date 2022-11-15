package com.covoiturage.covoiturage.web_service;

import com.covoiturage.covoiturage.controller.UserController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class API_EXT_INE {

    Logger logger = LoggerFactory.getLogger(UserController.class);



    public  String getJson(URL url) {
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

    public List<Integer> getListUsersAPI() throws MalformedURLException, JsonProcessingException {
        URL url=new URL("https://c363cd03-adfa-4394-8a07-3a0a269acdf5.mock.pstmn.io/numeroEtudiant");
        String EtudiantsAPI=this.getJson(url);

                ObjectMapper mapper = new ObjectMapper();
        List<Integer> listUsersAPI = Arrays.asList(mapper.readValue(EtudiantsAPI, Integer[].class));
        return listUsersAPI;
    }



    public  void distance(String origine, String destionation) throws MalformedURLException, JsonProcessingException {
        URL url=new URL("https://fr.distance24.org/route.json?stops="+origine+"|"+destionation+"");
        String reponse= this.getJson(url);
        //ObjectMapper mapper = new ObjectMapper();
        //Map<String,Integer> participantJsonList = mapper.readValue(reponse,  HashMap.class);

        Map<String, ArrayList<Map<String,Double>>>  map = new ObjectMapper().readValue(reponse, HashMap.class);
        //Map<String,Map<String,Double>> latitude=new HashMap<>();
logger.error("APIAPIAPIAPIAPIAPIAPIAPIAPI"+map.get("stops").get(0).get("latitude"));
        //return (int) map.get("distance");
    }



}


//https://api.radar.io/v1/route/matrix?origins=49.119666,6.176905&destinations=49.470163,5.930146&mode=car&units=imperial
//AIzaSyBN4_F3cBbadQ4x1PqZf6_OCktum1dmkJg
//https://fr.distance24.org/route.json?stops=Metz|Villerupt



