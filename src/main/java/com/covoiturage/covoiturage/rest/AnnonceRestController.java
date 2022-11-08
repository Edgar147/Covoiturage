package com.covoiturage.covoiturage.rest;

import com.covoiturage.covoiturage.entity.Annonce;
import com.covoiturage.covoiturage.service.AnnonceServiceImpl;
import com.covoiturage.covoiturage.service.Services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AnnonceRestController {


    @Autowired
    @Qualifier("annonceService")
    private Services<Annonce> annonceService;


    @Autowired
    public AnnonceRestController(AnnonceServiceImpl theAnnonceService) {
        annonceService = theAnnonceService;
    }


    @GetMapping("/annonces-tout5")
    public List<Annonce> findAll() {
        return annonceService.findAll();
    }


    @PostMapping("/annonces-tout")
    public Annonce addAnnonce(@RequestBody Annonce theAnnonce) {

        theAnnonce.setId(0);
        theAnnonce.setDate(null);
        annonceService.save(theAnnonce);

        return theAnnonce;
    }



}