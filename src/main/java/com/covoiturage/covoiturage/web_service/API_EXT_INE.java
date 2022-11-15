package com.covoiturage.covoiturage.web_service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class API_EXT_INE {




    public  String getEtudiantAPI(URL url) {
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
        String EtudiantsAPI=this.getEtudiantAPI(url);

                ObjectMapper mapper = new ObjectMapper();
        List<Integer> listUsersAPI = Arrays.asList(mapper.readValue(EtudiantsAPI, Integer[].class));
        return listUsersAPI;
    }


}
