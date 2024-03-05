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

    /*EXPLICATION:
    le ctrl rajoute une couche de robustesse à l'application.

    Même si le DAO gère déjà l'erreur spécifique liée à la requête
    et que la classe ConnexionBD gestionne les erreurs de connexion,
     il est toujours possible que d'autres types d'erreurs non prévues
      surviennent lors de l'appel de la méthode. Par exemple, une erreur réseau,
       une modification inattendue de l'état de l'application, ou d'autres exceptions
        runtime non liées directement à la requête SQL.
        synonyme de robustesse: résilience,

        La présence du catch dans le contrôleur permet d'assurer une sécurité supplémentaire
         et une meilleure résilience de l'application, en garantissant que toute exception,
          même inattendue, sera gérée de manière à ne pas impacter
          négativement l'expérience utilisateur.
     */
    public static ResultSet listerVolsSelonType(String type) { //une methode pour tout type de listing
        try {
            ResultSet res = VolDAO.getInstance().listerVolsSelonType(type);
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            //---> affiche les erreurs non lié à la base de donnée dans la console
        }
        return null; //si erreur
    }

    public String addAvion(Avion avion) {
        try {
            return VolDAO.getInstance().addAvion(avion);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public String addVolDeBase(Vol vol) {
        try {
            return VolDAO.getInstance().addVolDeBase(vol);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public String addVolByType(Vol vol) {
        try {
            return VolDAO.getInstance().addVolByType(vol);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String supprimerVol(int numDeVol) {
        try {
            return VolDAO.getInstance().supprimerVol(numDeVol);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String modifierDateVol(int numVol, Date date) {
        try {
            return VolDAO.getInstance().modifierDateVol(numVol, date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String ajouterReservation(int numVol, int nbPlaces) {
        try {
            return VolDAO.getInstance().ajouterReservation(numVol, nbPlaces);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
