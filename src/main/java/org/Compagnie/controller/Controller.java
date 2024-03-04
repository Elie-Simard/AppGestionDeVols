package org.Compagnie.controller;

import org.Compagnie.dao.VolDAO;
import org.Compagnie.model.Avion;
import org.Compagnie.model.Date;
import org.Compagnie.model.Vol;

import java.sql.ResultSet;

public class Controller { // on passe par ici pour connecter les methodes de la vue avec les methodes du DAO
    //instance
    private static Controller instance = null;

    //constructeur
    private Controller() {
    }

    //methode
    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    // **************************^Fin Singleton^*************************************

    public static ResultSet listerVolsSelonType(String type) { //une methode pour tout type de listing
        try {
            ResultSet res = VolDAO.getInstance().listerVolsSelonType(type);
            return res;
        } catch (Exception e) { //Afin de ne pas avoir à gérer les exceptions dans la vue (?)
            e.printStackTrace();
        }
        return null;

    }

    public String addAvion(Avion avion) {
        return VolDAO.getInstance().addAvion(avion);
    }
    public String addVolDeBase(Vol vol) {
        return VolDAO.getInstance().addVolDeBase(vol);
    }
    public String addVolByType(Vol vol) {
        return VolDAO.getInstance().addVolByType(vol);
    }

    public String supprimerVol(int numDeVol) {
        return VolDAO.getInstance().supprimerVol(numDeVol);
    }

    public String modifierDateVol(int numVol, Date date) {
        return VolDAO.getInstance().modifierDateVol(numVol, date);
    }

    public String ajouterReservation(int numVol, int nbPlaces) {
        return VolDAO.getInstance().ajouterReservation(numVol, nbPlaces);
    }

    public boolean volExists(int numVol) {
        return VolDAO.getInstance().volExists(numVol);
    }

    public boolean avionExists(int numAvion) {
        return VolDAO.getInstance().avionExists(numAvion);
    }

    public Avion getAvion(int numAvion) {
        return VolDAO.getInstance().getAvion(numAvion);
    }

}
