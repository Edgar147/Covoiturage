package com.covoiturage.covoiturage.dao;

import com.covoiturage.covoiturage.entity.Annonce;

import java.util.List;

public interface AnnonceDAO {

    public void saveAnnonce(Annonce theAnnonce);


   public List<Annonce> findAll();
}
