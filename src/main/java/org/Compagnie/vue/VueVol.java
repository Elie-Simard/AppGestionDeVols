package org.Compagnie.vue;

import org.Compagnie.dao.VolDAO;
import org.Compagnie.model.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import static org.Compagnie.util.UtilVue.*;

public class VueVol {

    // **************************************************************VUE**************************************************************//

    // ---Menu pour le choix d'opération sur les vols
    public static int choixPourMenuGeneral() {
        String contenu = "1- Lister \n2- Ajouter \n3- Supprimer\n4-Modifier Date\n5- Réserver\n6- Quitter\n";
        contenu += "Entrez votre choix parmis[1-6] : ";
        return inputInteger(contenu, "MENU GESTION VOLS");
    }

    public static void menuGeneral() throws Exception {
        int choix;
        do {
            choix = choixPourMenuGeneral();
            switch (choix) {
                case 1:
                    menuDeListage();
                    break;
                case 2:
                    menuAjouter();
                    break;
                case 3:
                    supprimerVol();
                    break;
                case 4:
                    modifierDateVol();
                    break;
                case 5: // changer pour réserver
                    ajouterReservation();
                    break;
                case 6:
                    message("Fin du programme", "Fin");
                    System.exit(0); // terminer le programme immédiatement
                    return;
                default:
                    message("Choix invalide", "Erreur");
                    break;
            }
        } while (choix != 6);
    }

    //**************************************************LISTAGE DES VOLS************************************************//
    public static Integer choixPourMenuDeListage() {
        String contenu = "1-Tous\n2-Bas Prix\n3-Régulier\n4-Charter\n5-Privé\n6-Terminer\n\n";
        contenu += "Quel type de vol souhaité vous lister ? Entrez votre choix parmis[1-6] : ";
        return inputInteger(contenu, "MENU CHOIX DE VOL À LISTER");

    }

    public static void menuDeListage() throws SQLException {
        Integer choix;
        do {
            choix = choixPourMenuDeListage();
            VolDAO dao = VolDAO.getInstance();
            String resultat = "";
            ResultSet listeVols;
            if (choix == null || choix == -1) { // Sortir si l'utilisateur annule ou appuie sur ESC
                break;
            }
            switch (choix) {
                case 1: // Tous

                    resultat = afficherVols("Bas Prix") +
                            afficherVols("Regulier") +
                            afficherVols("Charter") +
                            afficherVols("Prive");
                    displayVolsPanel(resultat);
                    break;
                case 2: // Bas Prix
                    resultat = afficherVols("Bas Prix");
                    displayVolsPanel(resultat);

                    break;
                case 3: // Régulier
                    resultat = afficherVols("Regulier");
                    displayVolsPanel(resultat);
                    break;
                case 4: // Charter
                    resultat = afficherVols("Charter");
                    displayVolsPanel(resultat);
                    break;
                case 5: // Privé
                    resultat = afficherVols("Prive");
                    displayVolsPanel(resultat);
                    break;
                case 6:
                    break;
                default:
                    message("Choix invalide", "Erreur");
                    break;
            }
        } while (choix != 6 && choix != -1);

    }
    public static String nomColonne(String[] extraColonnes, String type) {

        String resultat = "\n\t\t\t\t\t\t\t\tListe Des Vols " + type + "\n";
        resultat += ajouterEspacesFin(10, "Num Vol")
                + ajouterEspacesFin(20, "Destination")
                + ajouterEspacesFin(20, "Date de départ")
                + ajouterEspacesFin(10, "Avion")
                + ajouterEspacesFin(15, "Réservation");
        // Ajouter les colonnes supplémentaires à partir du tableau extraColonnes
        for (String colonne : extraColonnes) {
            resultat += ajouterEspacesFin(20, colonne.trim()); // Utiliser trim() pour enlever les espaces supplémentaires
        }

        resultat += "\n------------------------------------------------------------------------------------------------------------------------------------------------------"
                + "\n";
        return resultat;
    }
    public static String[] xtraSelonLeType(String typeDeVol) { // POUR L'AJOUT DE VOL
        switch (typeDeVol) {
            case "Bas Prix":
                return new String[] {
                        "repas_xtra",
                        "choix_siege_xtra",
                        "divertissement_xtra",
                        "ecouteur_xtra"
                };
            case "Regulier":
                return new String[] {
                        "repas_inclus",
                        "choix_siege_inclus",
                        "espace_xtra",
                        "bagage_soute_xtra"
                };
            case "Charter":
                return new String[] {
                        "repas_luxe_xtra",
                        "siege_luxe_xtra",
                        "wifi_xtra",
                        "salon_vip_xtra"
                };
            case "Prive":
                return new String[] {
                        "repas_luxe",
                        "choix_siege_luxe",
                        "wifi",
                        "salon_vip"
                };
            default:
                return new String[] {};
        }
    }

    public static String afficherVols(String type) throws SQLException { //aller get le vol de la BD via le dao (en passant par le controleur)

        String[] extraColonnes = xtraSelonLeType(type);
        String colonnes = nomColonne(extraColonnes, type);

        String resultat = colonnes;

        VolDAO dao = VolDAO.getInstance();
        ResultSet listeVols = dao.listerVolsSelonType(type);
        while(listeVols.next()) {
            resultat += ajouterEspacesFin(10, String.valueOf(listeVols.getInt("vol_id")))
                    + ajouterEspacesFin(20, listeVols.getString("destination"))
                    + ajouterEspacesFin(20, listeVols.getString("date_depart"))
                    + ajouterEspacesFin(10, String.valueOf(listeVols.getInt("avion_id")))
                    + ajouterEspacesFin(15, String.valueOf(listeVols.getInt("nb_res")));
            for (String colonne : extraColonnes) {
                String valeur = String.valueOf(listeVols.getInt(colonne) == 1 ? "Oui" : "Non");
                resultat += ajouterEspacesFin(20, valeur);
            }
            resultat += "\n";
        }

        return resultat;
    }

    // **********************************************AJOUT DE VOL*****************************************//
    public static Integer choixPourMenuDAjout() {
        String contenu = "1-Bas Prix\n2-Régulier\n3-Charter\n4-Privé\n5-Terminer\n\n";
        contenu += "De quel type de vol souhaité vous ajouter ? Entrez votre choix parmis[1-5] : ";
        return inputInteger(contenu, "MENU CHOIX DE VOL À AJOUTER");
    }

    public static void menuAjouter() throws Exception {
        Integer choix;
        do {
            choix = choixPourMenuDAjout();
            switch (choix) {
                case 1:
                    ajoutVol("Bas Prix");
                    break;
                case 2:
                    ajoutVol("Regulier");
                    break;
                case 3:
                    ajoutVol("Charter");
                    break;
                case 4:
                    ajoutVol("Prive");
                    break;
                case 5:
                    break;
                default:
                    message("Choix invalide", "Erreur");
                    break;
            }
        } while (choix != 5 && choix != null);
    }
    public static void ajoutVol(String typeDeVol) { //****METHODE PRINCIPAL POUR COMMUNIQUER AVEC LE DAO

        VolDAO dao = VolDAO.getInstance();

        int num = inputInteger("Entrez le numéro du vol", "AJOUTER UN VOL");
        boolean volExiste = dao.volExists(num);
        if (volExiste) {
            errorMessage("Le vol existe déjà");
            return;
        } else {

            String dest = inputMessage("Entrez la destination", "AJOUTER UN VOL");

            Date dateDepart = inputDate(); //va ask aux user une date valide selon les critères de Date.java

            //*************Ajout de l'avion obligatoire pour le vol, du moins le id***************
            int numAvion = inputInteger("Entrez le numéro de l'avion", "AJOUTER UN AVION");
            boolean avionExiste = dao.avionExists(numAvion);
            Avion avion;
            if (avionExiste) {
                message("L'avion existe déjà", "AJOUTER AVION");
                avion = dao.getAvion(numAvion);
            } else {
                boolean ajoutInfoAvion = yesNoInputMessage("Voulez-vous ajouter des informations sur l'avion ?");
                if (ajoutInfoAvion) {
                    String typeAvion = inputMessage("Entrez le type de l'avion (Boeing, etc)", "AJOUTER UN AVION");
                    int nbPlaces = inputInteger("Entrez le nombre de places disponible dans l'avion", "AJOUTER UN AVION");
                    int categorie = inputInteger("Entrez la catégorie (1-Court Courrier, 2-Moyen Courrier, 3-Long Courrier)", "AJOUTER UN AVION");
                    avion = new Avion(numAvion, typeAvion, nbPlaces, categorie);
                } else {
                    avion = new Avion(numAvion);
                }
                message(dao.addAvion(avion), "AJOUTER AVION"); //ajout de l'avion dans la BD
            }
            int nbReservation = 0; // pour ne pas changer les cstr des types de vol...
            Vol vol = new Vol(num, dest, dateDepart, avion);

            message(dao.addVolDeBase(vol), "AJOUTER VOL");

            //*********************partie pour tables extrasVol ***********************
            String[] optionMessages = xtraSelonLeType(typeDeVol);

            if (optionMessages.length >= 4) {
                Map<String, Boolean> boolMap = optionsRadioInputMessage(optionMessages[0], optionMessages[1],
                        optionMessages[2], optionMessages[3]); //affiche un panel avec options radio selon type

                boolean opt1 = boolMap.get("opt1");
                boolean opt2 = boolMap.get("opt2");
                boolean opt3 = boolMap.get("opt3");
                boolean opt4 = boolMap.get("opt4");

                if (typeDeVol.equals("Bas Prix")) {
                    VolBasPrix volBasPrix = new VolBasPrix(num, dest, dateDepart, avion, nbReservation, opt1, opt2, opt3, opt4);
                    message(dao.addVolBasPrix(volBasPrix), "AJOUTER VOL");
                } else if (typeDeVol.equals("Regulier")) {
                    VolRegulier volRegulier = new VolRegulier(num, dest, dateDepart, avion, nbReservation, opt1, opt2, opt3, opt4);
                    message(dao.addVolRegulier(volRegulier), "AJOUTER VOL");
                } else if (typeDeVol.equals("Charter")) {
                    VolCharter volCharter = new VolCharter(num, dest, dateDepart, avion, nbReservation, opt1, opt2, opt3, opt4);
                    message(dao.addVolCharter(volCharter), "AJOUTER VOL");
                } else if (typeDeVol.equals("Prive")) {
                    VolPrive volPrive = new VolPrive(num, dest, dateDepart, avion, nbReservation, opt1, opt2, opt3, opt4);
                    message(dao.addVolPrive(volPrive), "AJOUTER VOL");
                } else {
                    errorMessage("Error: Invalid type of flight.");
                }
                message("Le vol a été ajouté avec succès dans les différentes tables de la bd!", "AJOUTER UN VOL");
            } else {
                errorMessage("Error: Insufficient option messages.");
            }
        }
    }


    //**************SUPPRIMER*****************************//
    private static void supprimerVol() {
        int numVol = inputInteger("Entrez le numéro du vol à supprimer", "SUPPRIMER UN VOL");
        VolDAO dao = VolDAO.getInstance();
        message(dao.supprimerVol(numVol), "SUPPRIMER UN VOL");
    }

    //**************MODIFIER DATE*****************************//
    private static void modifierDateVol() {
        int numVol = inputInteger("Entrez le numéro du vol à modifier", "MODIFIER DATE VOL");
        Date date = inputDate();
        VolDAO dao = VolDAO.getInstance();
        message(dao.modifierDateVol(numVol, date), "MODIFIER DATE VOL");
    }

    //**************RÉSERVER*****************************//
    private static void ajouterReservation() {
        int numVol = inputInteger("Entrez le numéro du vol à réserver", "RÉSERVER UN VOL");
        int nbPlaces = inputInteger("Entrez le nombre de places à réserver", "RÉSERVER UN VOL");
        VolDAO dao = VolDAO.getInstance();
        message(dao.ajouterReservation(numVol, nbPlaces), "RÉSERVER UN VOL");
    }


}

