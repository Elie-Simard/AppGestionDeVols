package org.Compagnie.dao;

import org.Compagnie.connexion.ConnexionBD;
import org.Compagnie.model.*;
import org.Compagnie.model.Date;
import java.sql.*;

import static org.Compagnie.util.UtilVue.*;

//***************Permet de faire des requetes a la BD***************
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
    /*         Pour ajouter un vol, on doit d'abord ajouter un avion,
     ensuite le vol de base pour après inserer les extras du type de vol dans sa table respective */

    public String addAvion(Avion avion) {
        String msg;
        try {
            connexion = ConnexionBD.getInstance().getConnexionMySQL(); // appel de la connexion (de la classe ConnexionBD)
            preparedStatement = connexion.prepareStatement("INSERT INTO avion (avion_id, type_avion, nb_place, categorie) VALUES (?, ?, ?, ?)");
            preparedStatement.setInt(1, avion.getNumAvion());
            preparedStatement.setString(2, avion.getTypeAvion());
            preparedStatement.setInt(3, avion.getNbPlaces());
            preparedStatement.setInt(4, avion.getCategorieAvion());
            preparedStatement.executeUpdate();
            msg = "Avion " + avion.getNumAvion() + " ajouté avec succès.";
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
            preparedStatement = connexion.prepareStatement("INSERT INTO vol (vol_id, destination, date_depart, avion_id) VALUES (?, ?, ?, ?)");
            preparedStatement.setInt(1, vol.getNumDeVol());
            preparedStatement.setString(2, vol.getDestination());
            preparedStatement.setString(3, vol.getDateDepartString());
            preparedStatement.setInt(4, vol.getNumAvion());
            preparedStatement.executeUpdate();
            msg = "Vol " + vol.getNumDeVol() + " ajouté avec succès.";
        } catch (SQLException e) {
            msg = "Erreur lors de l'ajout du vol : " + e.getMessage();
        } finally {
            fermer();
        }
        return msg;
    }


    // dans le vue on va ensuite ask quel type de vol et continuer lajout dextra dans les tables selon le type

    public String addVolByType(Vol vol) {
        String msg = "";
        try {
            connexion = ConnexionBD.getInstance().getConnexionMySQL();
            String query = "";

            if (vol instanceof VolBasPrix) {
                VolBasPrix vbp = (VolBasPrix) vol;
                query = "INSERT INTO volBasPrix (vol_id, repas_xtra, choix_siege_xtra, divertissement_xtra, ecouteur_xtra) VALUES (?, ?, ?, ?, ?)";
                preparedStatement = connexion.prepareStatement(query);
                // Set parameters using vbp getters
                preparedStatement.setInt(1, vbp.getNumDeVol()); // Supposons que getNumDeVol() soit un getter dans la classe parent `Vol`
                preparedStatement.setInt(2, vbp.getRepasXtra()); // Supposons que isRepasXtra() retourne un boolean pour repas_xtra
                preparedStatement.setInt(3, vbp.getChoixSiegeXtra()); // Supposons que isChoixSiegeXtra() retourne un boolean pour choix_siege_xtra
                preparedStatement.setInt(4, vbp.getDivertissementXtra()); // Supposons que isDivertissementXtra() retourne un boolean pour divertissement_xtra
                preparedStatement.setInt(5, vbp.getEcouteursXtra()); // Supposons que isEcouteursXtra() retourne un boolean pour ecouteur_xtra
                preparedStatement.executeUpdate();
                msg = "Vol Bas Prix " + vol.getNumDeVol() + " ajouté avec succès.";
            } else if (vol instanceof VolRegulier) {
                VolRegulier vr = (VolRegulier) vol;
                query = "INSERT INTO volRegulier (vol_id, repas_inclus, choix_siege_inclus, espace_xtra, bagage_soute_xtra) VALUES (?, ?, ?, ?, ?)";
                preparedStatement = connexion.prepareStatement(query);
                preparedStatement.setInt(1, vr.getNumDeVol());
                preparedStatement.setInt(2, vr.getRepasInclus());
                preparedStatement.setInt(3, vr.getChoixSiegeInclus());
                preparedStatement.setInt(4, vr.getEspaceExtra());
                preparedStatement.setInt(5, vr.getBagageSouteExtra());
                preparedStatement.executeUpdate();
                msg = "Vol Regulier " + vol.getNumDeVol() + " ajouté avec succès.";
            } else if (vol instanceof VolCharter) {
                VolCharter vc = (VolCharter) vol;
                query = "INSERT INTO volCharter (vol_id, repas_luxe_xtra, siege_luxe_xtra, wifi_xtra, salon_vip_xtra) VALUES (?, ?, ?, ?, ?)";
                preparedStatement = connexion.prepareStatement(query);
                preparedStatement.setInt(1, vc.getNumDeVol());
                preparedStatement.setInt(2, vc.getRepasCinqEtoilesExtra());
                preparedStatement.setInt(3, vc.getChoixSiegePremiumExtra());
                preparedStatement.setInt(4, vc.getWifiExtra());
                preparedStatement.setInt(5, vc.getSalonVIPExtra());
                preparedStatement.executeUpdate();
                msg = "Vol Charter " + vol.getNumDeVol() + " ajouté avec succès.";
            } else if (vol instanceof VolPrive) {
                VolPrive vp = (VolPrive) vol;
                query = "INSERT INTO volPrive (vol_id, repas_luxe, choix_siege_luxe, wifi, salon_vip) VALUES (?, ?, ?, ?, ?)";
                preparedStatement = connexion.prepareStatement(query);
                preparedStatement.setInt(1, vp.getNumDeVol());
                preparedStatement.setInt(2, vp.getRepasCinqEtoiles());
                preparedStatement.setInt(3, vp.getChoixSiegePremium());
                preparedStatement.setInt(4, vp.getWifi());
                preparedStatement.setInt(5, vp.getSalonVIP());
                preparedStatement.executeUpdate();
                msg = "Vol Prive " + vol.getNumDeVol() + " ajouté avec succès.";
            }
        } catch (SQLException e) {
            msg = "Erreur lors de l'ajout du vol : " + e.getMessage();
        } finally {
            fermer();
        }
        return msg;
    }

    //****************** SUPPRESSIONS ******************
      /* la contrainte de suppression en cascade est déjà implémentée dans la BD
            La suppression d'un vol entraine la suppression de des différents
            types de vols associés, qui sont stockés dans des tables différentes

            Par contre, étant donné qu'un avion peut être utilisé pour d'autres vols,
            la suppression d'un vol n'entraine pas la suppression de l'avion associé.
      */
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
}