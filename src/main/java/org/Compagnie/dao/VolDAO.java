package org.Compagnie.dao;

import org.Compagnie.connexion.ConnexionBD;
import org.Compagnie.model.*;
import org.Compagnie.model.Date;

import java.sql.*;

import static org.Compagnie.util.UtilVue.*;

public class VolDAO {
    private Connection connexion = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    // ****************** PARTIE SINGLETON *********************
    // La seule instance de cette classe
    private static VolDAO instance = null;

    private VolDAO() { // pour empêcher de créer des objets VolDAO
    }

    public static VolDAO getInstance() {
        if (instance == null) {
            instance = new VolDAO();
        }
        return instance;
    }
    // ************************ FIN PARTIE SINGLETON ********************

    //************************* LISTER **********************************
    public ResultSet listerVols() {
        try {
            connexion = ConnexionBD.getInstance().getConnexionMySQL();
            if (connexion != null) {
                statement = connexion.createStatement();
                resultSet = statement.executeQuery("SELECT * FROM vol");
            }
        } catch (SQLException e) {
            resultSet = null;
        }
        return resultSet;
    }

    public ResultSet listerAvions() {
        try {
            connexion = ConnexionBD.getInstance().getConnexionMySQL();
            if (connexion != null) {
                statement = connexion.createStatement();
                resultSet = statement.executeQuery("SELECT * FROM avion");
            }
        } catch (SQLException e) {
            resultSet = null;
        }
        return resultSet;
    }

    public ResultSet listerVolsSelonType(String type) {
        //retourner dabord les colonnes de vol puis les colonnes du type
        try {
            connexion = ConnexionBD.getInstance().getConnexionMySQL();
            if (connexion != null) {
                statement = connexion.createStatement();
                if (type.equals("Bas Prix")) {
                    resultSet = statement.executeQuery("SELECT * FROM vol JOIN volBasPrix ON vol.vol_id = volBasPrix.vol_id"); // retourne l'adresse de la ligne
                } else if (type.equals("Regulier")) {
                    resultSet = statement.executeQuery("SELECT * FROM vol JOIN volRegulier ON vol.vol_id = volRegulier.vol_id");
                } else if (type.equals("Charter")) {
                    resultSet = statement.executeQuery("SELECT * FROM vol JOIN volCharter ON vol.vol_id = volCharter.vol_id");
                } else if (type.equals("Prive")) {
                    resultSet = statement.executeQuery("SELECT * FROM vol JOIN volPrive ON vol.vol_id = volPrive.vol_id");
                }
            }
        } catch (SQLException e) {
            resultSet = null;
        }
        return resultSet;
    }



    //************************** AJOUTS *********************************

    public String addAvion(Avion avion) {
        String msg;
        try {
            connexion = ConnexionBD.getInstance().getConnexionMySQL(); // appel de la connexion (de la classe ConnexionBD)
            // Vérifier si l'avion existe déjà
            preparedStatement = connexion.prepareStatement("SELECT * FROM avion WHERE avion_id = ?");
            preparedStatement.setInt(1, avion.getNumAvion()); // va chercher l'avion avec le numéro d'avion entré
            resultSet = preparedStatement.executeQuery(); //resultSet donnera un null si l'avion n'existe pas
            if (!resultSet.next()) { // Si l'avion n'existe pas, l'ajouter
                preparedStatement = connexion.prepareStatement("INSERT INTO avion (avion_id, type_avion, nb_place, categorie) VALUES (?, ?, ?, ?)");
                preparedStatement.setInt(1, avion.getNumAvion());
                preparedStatement.setString(2, avion.getTypeAvion());
                preparedStatement.setInt(3, avion.getNbPlaces());
                preparedStatement.setInt(4, avion.getCategorieAvion());
                preparedStatement.executeUpdate();
                msg = "Avion " + avion.getNumAvion() + " ajouté avec succès.";
            } else {
                msg = "Avion " + avion.getNumAvion() + " existe déjà.";
            }
        } catch (SQLException e) {
            msg = "Erreur lors de l'ajout de l'avion : " + e.getMessage();
        }
        // Fermer les ressources ici
        fermer();
        return msg;
    }

    public String addVolDeBase(Vol vol) {
        String msg;
        try {
            connexion = ConnexionBD.getInstance().getConnexionMySQL();
            // Vérifier si le vol existe déjà
            preparedStatement = connexion.prepareStatement("SELECT * FROM vol WHERE vol_id = ?");
            preparedStatement.setInt(1, vol.getNumDeVol());
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                // Insérer le vol si non existant
                preparedStatement = connexion.prepareStatement("INSERT INTO vol (vol_id, destination, date_depart, avion_id) VALUES (?, ?, ?, ?)");
                preparedStatement.setInt(1, vol.getNumDeVol());
                preparedStatement.setString(2, vol.getDestination());
                preparedStatement.setString(3, vol.getDateDepartString());
                preparedStatement.setInt(4, vol.getNumAvion());
                preparedStatement.executeUpdate();
                msg = "Vol " + vol.getNumDeVol() + " ajouté avec succès.";
            } else {
                msg = "Vol " + vol.getNumDeVol() + " existe déjà.";
            }
        } catch (SQLException e) {
            msg = "Erreur lors de l'ajout du vol : " + e.getMessage();
        } finally {
            fermer();
        }
        return msg;
    }


    // dans le vue on va ensuite ask quel type de vol et continuer lajout dextra dans les tables selon le type
    public String addVolBasPrix(VolBasPrix vol) {
        String msg;
        try {
            connexion = ConnexionBD.getInstance().getConnexionMySQL();
            // Vérifier si le vol de base existe déjà
            preparedStatement = connexion.prepareStatement("SELECT * FROM vol WHERE vol_id = ?");
            preparedStatement.setInt(1, vol.getNumDeVol());
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {  // Si le vol de base n'existe pas, afficher un message d'erreur
                msg = "Le vol " + vol.getNumDeVol() + " n'existe pas. Veuillez d'abord ajouter le vol de base.";
            } else {
                // Vérifier si le volBasPrix existe déjà
                preparedStatement = connexion.prepareStatement("SELECT * FROM volBasPrix WHERE vol_id = ?");
                preparedStatement.setInt(1, vol.getNumDeVol());
                resultSet = preparedStatement.executeQuery();
                if (!resultSet.next()) {  // Si le volBasPrix n'existe pas, l'ajouter
                    preparedStatement = connexion.prepareStatement("INSERT INTO volBasPrix (vol_id, repas_xtra, choix_siege_xtra, divertissement_xtra, ecouteur_xtra) VALUES (?, ?, ?, ?, ?)");
                    preparedStatement.setInt(1, vol.getNumDeVol());
                    preparedStatement.setInt(2, vol.getRepasXtra());
                    preparedStatement.setInt(3, vol.getChoixSiegeXtra());
                    preparedStatement.setInt(4, vol.getDivertissementXtra());
                    preparedStatement.setInt(5, vol.getEcouteursXtra());
                    preparedStatement.executeUpdate();
                    msg = "VolBasPrix " + vol.getNumDeVol() + " ajouté avec succès.";
                } else {
                    msg = "Le volBasPrix pour le vol " + vol.getNumDeVol() + " existe déjà.";
                }
            }
        } catch (SQLException e) {
            msg = "Erreur lors de l'ajout du volBasPrix : " + e.getMessage();
        } finally {
            fermer();
        }
        return msg;
    }


    public String addVolRegulier(VolRegulier vol) {
        String msg;
        try {
            connexion = ConnexionBD.getInstance().getConnexionMySQL();
            // Check if the volRegulier already exists
            preparedStatement = connexion.prepareStatement("SELECT * FROM volRegulier WHERE vol_id = ?");
            preparedStatement.setInt(1, vol.getNumDeVol());
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) { // volRegulier does not exist, proceed with insertion
                preparedStatement = connexion
                        .prepareStatement("INSERT INTO volRegulier (vol_id, repas_inclus, choix_siege_inclus, espace_xtra, bagage_soute_xtra) VALUES (?, ?, ?, ?, ?)");
                preparedStatement.setInt(1, vol.getNumDeVol());
                preparedStatement.setInt(2, vol.getRepasInclus());
                preparedStatement.setInt(3, vol.getChoixSiegeInclus());
                preparedStatement.setInt(4, vol.getEspaceExtra());
                preparedStatement.setInt(5, vol.getBagageSouteExtra());
                preparedStatement.executeUpdate();
                msg = "VolRegulier " + vol.getNumDeVol() + " ajouté avec succès.";
            } else {
                msg = "VolRegulier " + vol.getNumDeVol() + " existe déjà.";
            }
        } catch (SQLException e) {
            msg = "Problème lors de l'exécution de cette requête : " + e.getMessage();
        }
        // Close resources
        fermer();
        return msg;
    }

    public String addVolCharter(VolCharter vol) {
        String msg;
        try {
            connexion = ConnexionBD.getInstance().getConnexionMySQL();
            // Vérifier si le vol charter existe déjà
            preparedStatement = connexion.prepareStatement("SELECT * FROM volCharter WHERE vol_id = ?");
            preparedStatement.setInt(1, vol.getNumDeVol());
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) { // Si le vol charter n'existe pas, l'ajouter
                preparedStatement = connexion.prepareStatement("INSERT INTO volCharter (vol_id, repas_luxe_xtra, siege_luxe_xtra, wifi_xtra, salon_vip_xtra) VALUES (?, ?, ?, ?, ?)");
                preparedStatement.setInt(1, vol.getNumDeVol());
                preparedStatement.setInt(2, vol.getRepasCinqEtoilesExtra());
                preparedStatement.setInt(3, vol.getChoixSiegePremiumExtra());
                preparedStatement.setInt(4, vol.getWifiExtra());
                preparedStatement.setInt(5, vol.getSalonVIPExtra());
                preparedStatement.executeUpdate();
                msg = "VolCharter " + vol.getNumDeVol() + " ajouté avec succès.";
            } else {
                msg = "VolCharter " + vol.getNumDeVol() + " existe déjà.";
            }
        } catch (SQLException e) {
            msg = "Problème lors de l'ajout du vol charter : " + e.getMessage();
        } finally {
            fermer(); // Assurez-vous que cette méthode ferme correctement toutes les ressources
        }
        return msg;
    }


    public String addVolPrive(VolPrive vol) {
        String msg;
        try {
            connexion = ConnexionBD.getInstance().getConnexionMySQL();
            // Verify if the private flight already exists
            preparedStatement = connexion.prepareStatement("SELECT * FROM volPrive WHERE vol_id = ?");
            preparedStatement.setInt(1, vol.getNumDeVol());
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {  // If the flight doesn't exist, add it
                if (connexion != null) {
                    preparedStatement = connexion
                            .prepareStatement("INSERT INTO volPrive VALUES (?, ?, ?, ?, ?)");
                    preparedStatement.setInt(1, vol.getNumDeVol());
                    preparedStatement.setInt(2, vol.getRepasCinqEtoiles());
                    preparedStatement.setInt(3, vol.getChoixSiegePremium());
                    preparedStatement.setInt(4, vol.getWifi());
                    preparedStatement.setInt(5, vol.getSalonVIP());
                    preparedStatement.executeUpdate();
                    msg = "VolPrive " + vol.getNumDeVol() + " ajouté avec succès.";
                } else {
                    msg = "Erreur de connexion à la base de données.";
                }
            } else {
                msg = "VolPrive " + vol.getNumDeVol() + " existe déjà.";
            }
        } catch (SQLException e) {
            msg = "Problème lors de l'exécution de cette requête : " + e.getMessage();
        } finally {
            fermer();
        }
        return msg;
    }

    //************ SUPPRESSIONS ************
    public String supprimerVol(int numVol) {
        String msg;
        try {
            connexion = ConnexionBD.getInstance().getConnexionMySQL();
            preparedStatement = connexion.prepareStatement("DELETE FROM vol WHERE vol_id = ?");
            preparedStatement.setInt(1, numVol);
            preparedStatement.executeUpdate();
            msg = "Vol " + numVol + " supprimé avec succès.";
        } catch (SQLException e) {
            msg = "Problème lors de la suppression du vol : " + e.getMessage();
        } finally {
            fermer();
        }
        return msg;
    }

    //************ MODIFIER DATE ************
    public String modifierDateVol(int numVol, Date date) {
        String msg;
        try {
            connexion = ConnexionBD.getInstance().getConnexionMySQL();
            preparedStatement = connexion.prepareStatement("UPDATE vol SET date_depart = ? WHERE vol_id = ?");
            preparedStatement.setString(1, date.toString());
            preparedStatement.setInt(2, numVol);
            preparedStatement.executeUpdate();
            msg = "Date du vol " + numVol + " modifiée avec succès.";
        } catch (SQLException e) {
            msg = "Problème lors de la modification de la date du vol : " + e.getMessage();
        } finally {
            fermer();
        }
        return msg;
    }

    //************ RÉSERVER ************
    public String ajouterReservation(int numVol, int nbPlaces) {
        String msg;
        try {
            connexion = ConnexionBD.getInstance().getConnexionMySQL();
            preparedStatement = connexion.prepareStatement("UPDATE vol SET nb_res = nb_res + ? WHERE vol_id = ?");
            preparedStatement.setInt(1, nbPlaces);
            preparedStatement.setInt(2, numVol);
            preparedStatement.executeUpdate();
            msg = "Réservation pour le vol " + numVol + " ajoutée avec succès.";
        } catch (SQLException e) {
            msg = "Problème lors de l'ajout de la réservation : " + e.getMessage();
        } finally {
            fermer();
        }
        return msg;
    }


    private void fermer() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }

            if (preparedStatement != null) {
                preparedStatement.close();
            }

            if (connexion != null) {
                connexion.close();
            }
        } catch (Exception e) {

        }
    }

    public static void main(String[] args) throws SQLException { //POUR TESTER
        VolDAO volDAO = VolDAO.getInstance();
        Avion avion = new Avion(6, "AirBus", 100, 1);
        System.out.println(volDAO.addAvion(avion));

        // ajoutons maintenant un vol de base avec l'avion qu'on vient d'ajouter :
        Date dateDepart = null;
        while (dateDepart == null) {
            String dateStr = inputMessage("Entrez la date de départ (format jj/mm/yyyy)", "Ajout d'un vol");
            try {
                String[] parts = dateStr.split("/");
                int jour = Integer.parseInt(parts[0]);
                int mois = Integer.parseInt(parts[1]);
                int an = Integer.parseInt(parts[2]);
                dateDepart = new Date(); // Initialize dateDepart to avoid null pointer
                if (!dateDepart.initFromStringAndValidation(dateStr)) { // if date is valid
                    dateDepart = new Date(jour, mois, an); // Assign only if date is valid
                } else {
                    dateDepart = null; // Reset to null if date is invalid
                }
            } catch (Exception e) {
                errorMessage("Date invalide ou format incorrect. Veuillez réessayer.");
            }
        }
//
        Vol vol = new Vol(12, "Paris", dateDepart, avion, 0);
        System.out.println(vol.toString());
        System.out.println(volDAO.addVolDeBase(vol));

        // section pour les types de vols:

        VolBasPrix volBasPrix = new VolBasPrix(12, vol.getDestination(), vol.getDateDepart(), avion, 0, true, true, false, false);

        System.out.println(volDAO.addVolBasPrix(volBasPrix));

        VolRegulier volRegulier = new VolRegulier(12, vol.getDestination(), vol.getDateDepart(), avion, 0, true, true, true, true);
        System.out.println(volDAO.addVolRegulier(volRegulier));

        VolCharter volCharter = new VolCharter(12, vol.getDestination(), vol.getDateDepart(), avion, 0, true, true, true, true);
        System.out.println(volDAO.addVolCharter(volCharter));

        VolPrive volPrive = new VolPrive(12, vol.getDestination(), vol.getDateDepart(), avion, 0, true, true, true, true);
        System.out.println(volDAO.addVolPrive(volPrive));
    }
    public boolean avionExists(int numAvion) {
        // return true si ca existe:
        boolean exists = false;
        try {
            connexion = ConnexionBD.getInstance().getConnexionMySQL();
            preparedStatement = connexion.prepareStatement("SELECT avion_id FROM avion WHERE avion_id = ?");
            preparedStatement.setInt(1, numAvion);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                exists = true;
            }
        } catch (SQLException e) {
            exists = false;
        }
        return exists;
    }
    public boolean volExists(int numVol) {
        // return true si ca existe:
        boolean exists = false;
        try {
            connexion = ConnexionBD.getInstance().getConnexionMySQL();
            preparedStatement = connexion.prepareStatement("SELECT vol_id FROM vol WHERE vol_id = ?");
            preparedStatement.setInt(1, numVol);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                exists = true;
            }
        } catch (SQLException e) {
            exists = false;
        }
        return exists;
    }

    public Avion getAvion(int numAvion) {
        Avion avion = null;
        try {
            connexion = ConnexionBD.getInstance().getConnexionMySQL();
            preparedStatement = connexion.prepareStatement("SELECT * FROM avion WHERE avion_id = ?");
            preparedStatement.setInt(1, numAvion);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                avion = new Avion(resultSet.getInt("avion_id"), resultSet.getString("type_avion"), resultSet.getInt("nb_place"), resultSet.getInt("categorie"));
            }
        } catch (SQLException e) {
            avion = null;
        }
        return avion;
    }
}

//public int getnumAvionBD(int numAvion) {
//    //retourne un id ou null si l'avion n'existe pas
//    int id;
//    try {
//        connexion = ConnexionBD.getInstance().getConnexionMySQL();
//        preparedStatement = connexion.prepareStatement("SELECT avion_id FROM avion WHERE avion_id = ?");
//        preparedStatement.setInt(1, numAvion);
//        resultSet = preparedStatement.executeQuery();
//        if (resultSet.next()) {
//            id = resultSet.getInt("avion_id");
//        } else {
//            id = -1;
//        }
//    } catch (SQLException e) {
//        id = -1;
//    }
//}

// */


// //reste a faire pour les autres types de vols

// public String addVol(Vol vol, Avion avion) {
// String msg;
// msg = addAvion(avion);
// msg = addVolDeBase(vol, avion);
// if (vol instanceof VolBasPrix) {
// msg = addVolBasPrix(vol);
// } else if (vol instanceof VolCharter) {
// msg = addVolCharter(vol);
// } else if (vol instanceof VolPrive) {
// msg = addVolPrive(vol);
// } else if (vol instanceof VolRegulier) {
// msg = addVolRegulier(vol);
// }

// return msg;

// }

// public String Mdl_Enregistrer(Vol unVol) throws Exception { // ajouter un Vol
// String msg;
// try {
// connexion =
// org.Compagnie.connexion.ConnexionBD.getInstance().getConnexionMySQL();
// if (connexion != null) {
// // PreparedStatements pour des rquêtes paramétrées
// preparedStatement = connexion
// .prepareStatement("INSERT INTO films VALUES (0, ?, ?, ?, ? , ?, ?)");
// // Les paramétres commencent à 1
// preparedStatement.setString(1, unFilm.getTitre());
// preparedStatement.setInt(2, unFilm.getDuree());
// preparedStatement.setString(3, unFilm.getRes());
// preparedStatement.setString(4, unFilm.getBandea());
// preparedStatement.setDouble(5, unFilm.getPrixloc());
// preparedStatement.setString(6, unFilm.getPochette());

// preparedStatement.executeUpdate();
// msg = "Film " + unFilm.getTitre() + " ajouté avec succès.";

// } else {
// msg = "Erreur de connection à la base de données.";
// }
// } catch (Exception e) {
// msg = "Problème lors de l'exécution de cette requête.";
// }
// fermer();
// return msg;
// }

// public ResultSet Mdl_Lister() throws Exception {
// try {
// connexion = ConnexionBD.getInstance().getConnexionMySQL();
// if (connexion != null) {
// statement = connexion.createStatement();
// // ResultSet est le résultat de la requête
// resultSet = statement
// .executeQuery("SELECT * FROM films");
// // fermer(); // Fermeture manuelle du ResultSet mais pas la faire ici sinon
// // on ne peut plus utiliser ce DAO car le ResultSet est fermé
// return resultSet;
// } else {
// fermer();
// throw new SQLException("Erreur de connection à la base de données.");
// }
// } catch (Exception e) {
// fermer();
// throw new Exception("Problème lors de l'exécution de cette requête.");
// }

// }

// public String Mdl_Supprimer(int idf) throws Exception {
// String msg;
// try {
// connexion = ConnexionBD.getInstance().getConnexionMySQL();
// if (connexion != null) {
// // PreparedStatements pour des rquêtes paramétrées
// preparedStatement = connexion
// .prepareStatement("DELETE FROM films WHERE idf=?");
// // Les paramétres commencent à 1
// preparedStatement.setInt(1, idf);

// preparedStatement.executeUpdate();
// msg = "Film " + idf + " a été supprimé.";

// } else {
// msg = "Erreur de connection à la base de données.";
// }
// } catch (Exception e) {
// msg = "Problème lors de l'exécution de cette requête.";
// }
// fermer();
// return msg;
// }

// public Object Mdl_Fiche(int idf) throws Exception {
// String msg;
// try {
// connexion = ConnexionBD.getInstance().getConnexionMySQL();
// if (connexion != null) {
// // PreparedStatements pour des rquêtes paramétrées
// preparedStatement = connexion
// .prepareStatement("SELECT * FROM films WHERE idf=?");
// // Les paramétres commencent à 1
// preparedStatement.setInt(1, idf);

// resultSet = preparedStatement.executeQuery();
// return resultSet;
// } else {
// msg = "Erreur de connection à la base de données.";
// }
// } catch (Exception e) {
// msg = "Problème lors de l'exécution de cette requête.";
// }
// fermer();
// return msg;
// }

// public String Mdl_Modifier(Film unFilm) throws Exception {
// String msg;
// try {
// connexion = ConnexionBD.getInstance().getConnexionMySQL();
// if (connexion != null) {
// // PreparedStatements pour des rquêtes paramétrées
// preparedStatement = connexion
// .prepareStatement(
// "UPDATE films SET titre=?, duree=?, res=?, bandea=?, prixLoc=?, pochette=?
// WHERE idf=?");
// // Les paramétres commencent à 1
// preparedStatement.setString(1, unFilm.getTitre());
// preparedStatement.setInt(2, unFilm.getDuree());
// preparedStatement.setString(3, unFilm.getRes());
// preparedStatement.setString(4, unFilm.getBandea());
// preparedStatement.setDouble(5, unFilm.getPrixloc());
// preparedStatement.setString(6, unFilm.getPochette());
// preparedStatement.setInt(7, unFilm.getIdf());

// preparedStatement.executeUpdate();
// msg = "Film " + unFilm.getIdf() + " a été modifié.";

// } else {
// msg = "Erreur de connection à la base de données.";
// }
// } catch (Exception e) {
// msg = "Problème lors de l'exécution de cette requête.";
// }
// fermer();
// return msg;
// }

// Libérer les ressources


// /*
// * public void traitementAvecLaBD() throws Exception {
// * try {
// * // Chargement du pilote MySQL, chaque BD a son propre pilote
// * //Class.forName("com.mysql.jdbc.Driver");
// * // Connexion à la base de données
// * connexion =
// *
// Connexion.getInstance().getConnexionMySQL("localhost","umbdfilms","root","");
// * // Statement permet d'envoyer des requêtes à la base de données
// * statement = connexion.createStatement();
// * // ResultSet est le résultat de la requête
// * resultSet = statement
// * .executeQuery("SELECT * FROM films");
// * // Appel de la méthode pour afficher le résultat
// * System.out.println("\n********* DONNÉES AU DÉPART ************");
// * afficherResultat(resultSet);
// *
// *
// * // PreparedStatements pour des rquêtes paramétrées
// * preparedStatement = connexion
// * .prepareStatement("INSERT INTO films VALUES (0, ?, ?, ?, ? , ?, ?)");
// * // Les paramétres commencent à 1
// * preparedStatement.setString(1, "Rapides et dangereux 4");
// * preparedStatement.setInt(2, 140);
// * preparedStatement.setString(3, "Un autre 4");
// * preparedStatement.setString(4, "Aucune 4");
// * preparedStatement.setDouble(5, 4.25);
// * preparedStatement.setString(6,
// *
// "https://ia.media-imdb.com/images/M/MV5BMTU4NzMyNDk1OV5BMl5BanBnXkFtZTcwOTEwMzU1MQ@@._V1_SX300.jpg"
// * );
// * preparedStatement.executeUpdate();
// *
// * preparedStatement = connexion
// * .prepareStatement("SELECT * from FILMS");
// * resultSet = preparedStatement.executeQuery();
// * System.out.println("\n********* DONNÉES APRÈS L'AJOUT ************");
// * afficherResultat(resultSet);
// *
// * // Supprimer un film
// * preparedStatement = connexion
// * .prepareStatement("DELETE FROM films WHERE idf= ? ; ");
// * preparedStatement.setInt(1, 22);
// * preparedStatement.executeUpdate();
// *
// * resultSet = statement
// * .executeQuery("SELECT * FROM films");
// * System.out.println("\n********* DONNÉES APRÈS SUPPRÉSION ************");
// * afficherResultat(resultSet);
// *
// * // Modifier un film
// * preparedStatement = connexion
// * .prepareStatement("UPDATE films SET duree=? WHERE idf= ? ; ");
// * preparedStatement.setInt(1, 9999999);
// * preparedStatement.setInt(2, 23);
// * preparedStatement.executeUpdate();
// *
// * resultSet = statement
// * .executeQuery("SELECT * FROM films");
// * System.out.println("\n********* DONNÉES APRÈS MODIFICATION ************");
// * afficherResultat(resultSet);
// *
// * afficherMetaResultat(resultSet);
// *
// * } catch (Exception e) {
// * throw e;
// * } finally {
// * fermer();
// * }
// *
// * }
// *
// * private void afficherMetaResultat(ResultSet resultSet) throws SQLException
// {
// * // Afficher les meta données de la base de données
// * System.out.println("\n********* META DONNÉES ************");
// * System.out.println("Les colonnes de la table sont : ");
// *
// * System.out.println("Table: " + resultSet.getMetaData().getTableName(1));
// * for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
// * System.out.println("Colonne " + i + " " +
// * resultSet.getMetaData().getColumnName(i));
// * }
// * System.out.println("---------------------------");
// * }
// *
// * private void afficherResultat(ResultSet resultSet) throws SQLException {
// * // ResultSet : le curseur est avant le premier élément.
// * // Au début resultSet.next() place le curseur sur le premier élément
// * while (resultSet.next()) {
// * // Il est possible d'obtenir la colonne par son nom
// * // mais aussi possible d'obtenir par son numéro,
// * // qui débute à 1.
// * // Par exemple : resultSet.getString(3);
// * int idf = resultSet.getInt("idf");
// * String titre = resultSet.getString("titre");
// * int duree = resultSet.getInt("duree");
// * String res = resultSet.getString("res");
// *
// * System.out.println("ID du film : " + idf);
// * System.out.println("Titre : " + titre);
// * System.out.println("Durée : " + duree);
// * System.out.println("Réalisateur : " + res);
// * System.out.println("---------------------------");
// * }
// * }
// */

// }
