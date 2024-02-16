package org.example;

import java.awt.*;
import java.util.*;
import java.io.File;
import java.io.*;

import static org.example.Utilitaires.*;

public class Compagnie {

    static final String FICHIER_VOLS_TEXTE = "src/main/resources/vols.txt";
    static final String FICHIER_VOLS_OBJ = "src/main/resources/vols.obj";
    static Map<Integer, Vol> mapVols;
    static BufferedReader tmpVolsRead;
    static ObjectInputStream tmpVolsReadObj;
    static ObjectOutputStream tmpVolsWriteObj;

    //**************************************************************MODELE**************************************************************//

    //---Mettra tout le fichier objet dans la map
    public static void chargerVolsObj() {
        try {
            tmpVolsReadObj = new ObjectInputStream(new FileInputStream(FICHIER_VOLS_OBJ));
            mapVols = (HashMap<Integer, Vol>) tmpVolsReadObj.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("Fichier introuvable. Vérifiez le chemin et nom du fichier." + e.getMessage());
        } catch (IOException e) {
            System.out.println("Un probléme est arrivé lors de la manipulation du fichier. Verifiez vos donnees." + e.getMessage());

        } catch (Exception e) {
            System.out.println("Un probléme est arrivé lors du chargement du fichier. Contactez l'administrateur." + e.getMessage());
        } finally {// Exécuté si erreur ou pas
            try {
                if (tmpVolsReadObj != null) {
                    tmpVolsReadObj.close();
                }
            } catch (IOException e) {
                System.out.println("Erreur lors de la fermeture du flux : " + e.getMessage());
            }
        }
    }

    //Mettra tout le fichier texte dans la map
    public static void chargerVolsTxt() throws Exception {
        //exemple de ligne: B;14567;Toronto;12;12;2024;100;167;1;0;1;1
        try {
            String ligne;
            int numDeVol;
            String dest;
            Date date;
            int numAvion;
            int nbReservation;

            String[] elems = new String[11];
            mapVols = new HashMap<Integer, Vol>();
            tmpVolsRead = new BufferedReader(new FileReader(FICHIER_VOLS_TEXTE));
            ligne = tmpVolsRead.readLine();// Lire la premiére ligne du fichier
            while (ligne != null) {// Si ligne == null alors ont a atteint la fin du fichier
                elems = ligne.split(";");// elems[1] contient le numDevVol;elems[1] la dest, etc
                numDeVol = Integer.parseInt(elems[1]);
                dest = elems[2];
                date = new Date(Integer.parseInt(elems[3]), Integer.parseInt(elems[4]), Integer.parseInt(elems[5]));
                numAvion = Integer.parseInt(elems[6]);
                nbReservation = Integer.parseInt(elems[7]);
                boolean opt1 = elems[8].equals("1");  // Convert "1" to true, anything else to false
                boolean opt2 = elems[9].equals("1");
                boolean opt3 = elems[10].equals("1");
                boolean opt4 = elems[11].equals("1");

                Avion avion = new Avion(numAvion);

                //-------!! C'EST ICI SEULEMENT QUE L'ON CASTE LES VOLS DANS LEURS TYPES RESPECTIFS (selon b, r, c, p) !! ------->
                if (elems[0].equalsIgnoreCase("B")) { // Bas Prix
                    mapVols.put(numDeVol, new VolBasPrix(numDeVol, dest, date, avion,
                            nbReservation, opt1, opt2, opt3, opt4));
                } else if (elems[0].equalsIgnoreCase("R")) { // Régulier (R;17890;Guadeloupe;10;12;2024;200;340;1;1)

                    mapVols.put(numDeVol, new VolRegulier(numDeVol, dest, date, avion,
                            nbReservation, opt1, opt2, opt3, opt4));
                } else if (elems[0].equalsIgnoreCase("C")) { // Charter
                    mapVols.put(numDeVol, new VolCharter(numDeVol, dest, date, avion,
                            nbReservation, opt1, opt2, opt3, opt4));
                } else if (elems[0].equalsIgnoreCase("P")) { // Privé
                    mapVols.put(numDeVol, new VolPrive(numDeVol, dest, date, avion,
                            nbReservation, opt1, opt2, opt3, opt4));
                }
                ligne = tmpVolsRead.readLine();
            } // fin while

        } catch (FileNotFoundException e) {
            System.out.println("Fichier introuvable. Vérifiez le chemin et nom du fichier.");
        } catch (IOException e) {
            System.out.println("Un probléme est arrivé lors de la manipulation du fichier. Vérifiez vos donn�es.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Un probléme est arrivé lors du chargement du fichier. Contactez l'administrateur.");
        } finally {// Exécuté si erreur ou pas
            if (tmpVolsRead != null) {
                tmpVolsRead.close();
            }
        }
    }

    public static void sauvegarderVolsObj() throws IOException {
        try {
            tmpVolsWriteObj = new ObjectOutputStream(new FileOutputStream(FICHIER_VOLS_OBJ));
            tmpVolsWriteObj.writeObject(mapVols);
        } catch (FileNotFoundException e) {
            System.out.println("Fichier introuvable. Vérifiez le chemin et nom du fichier.");
        } catch (IOException e) {
            System.out.println("Un probléme est arrivé lors de la manipulation du fichier. V�rifiez vos donn�es.");
        } catch (Exception e) {
            System.out.println("Un probléme est arrivé lors du chargement du fichier. Contactez l'administrateur.");
        } finally {// Exécuté si erreur ou pas
            tmpVolsWriteObj.close();
        }
    }


    public static ArrayList<Integer> trierKey() {
        ArrayList<Integer> orderKeys = new ArrayList<>();
        orderKeys.addAll(mapVols.keySet());
        Collections.sort(orderKeys);
        return orderKeys;
    }

    public static Map<Integer, Vol> trierMap(Map<Integer, Vol> messyMap) {
        ArrayList<Integer> orderKeys = trierKey();//retrieve les cles de mapVols
        Map<Integer, Vol> newMap = new HashMap<>();
        for (Integer cle : orderKeys) {
            newMap.put(cle, messyMap.get(cle));
        }
        return newMap;
    }

    //**************************************************************VUE**************************************************************//

    //---Menu pour le choix d'opération sur les vols
    public static int choixPourMenuGeneral() {
        String contenu = "1-Lister\n2-Ajouter un Vol\n3-Enlever un Vol\n4-Modifier un Vol\n5-Rechercher\n6-Sauvegarder et Terminer \n7-Quitter sans sauvegarder\n\n";
        contenu += "Entrez votre choix parmis[1-5] : ";
        return inputInteger(contenu, "MENU GESTION VOLS");
    }

    public static void menuGeneral() throws Exception {
        int choix;
        do {
            mapVols = trierMap(mapVols);
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
                    modifierVol();
                    break;
                case 5:
                    rechercherVol();
                    break;
                case 6:
                    sauvegarderVolsObj();
                    message("Fin du programme", "Fin");
                    System.exit(0);  // terminer le programme immédiatement
                    return;
                case 7 :
                    message("Fin du programme", "Fin");
                    System.exit(0);  // terminer le programme immédiatement
                    return;
                default:
                    message("Choix invalide", "Erreur");
                    break;
            }
        } while (choix != 6);
    }

    //------------- ici les vues de sous menu------------------

    //---Menu pour le choix de type de vol à lister
    public static Integer choixPourMenuDeListage() {
        String contenu = "1-Tous\n2-Bas Prix\n3-Régulier\n4-Charter\n5-Privé\n6-Terminer(ou esc/cancel)\n\n";
        contenu += "Quel type de vol souhaité vous lister ? Entrez votre choix parmis[1-6] : ";
        return inputInteger(contenu, "MENU CHOIX DE VOL À LISTER");

    }

    public static void menuDeListage() {
        Integer choix;
        do {
            choix = choixPourMenuDeListage();
            if (choix == null || choix == -1) { // Sortir si l'utilisateur annule ou appuie sur ESC
                break;
            }
            switch (choix) {
                case 1:
                    String resultat =
                            listerVolsBasPrix() + "\n\n"
                                    + listerVolsReguliers() + "\n\n"
                                    + listerVolsCharters() + "\n\n"
                                    + listerVolsPrives();
                    displayVolsPanel(resultat); ////les methodes de manipulation sont un peu plus bas
                    break;
                case 2:
                    displayVolsPanel(listerVolsBasPrix()); //display meth recoit le string recu par la methode listerVolsBasPrix
                    break;
                case 3:
                    displayVolsPanel(listerVolsReguliers());
                    break;
                case 4:
                    displayVolsPanel(listerVolsCharters());
                    break;
                case 5:
                    displayVolsPanel(listerVolsPrives());
                    break;
                case 6:
                    break;
                default:
                    message("Choix invalide", "Erreur");
                    break;
            }
        } while (choix != 6 && choix != -1);

    }

    //---Menu pour le choix de type de vol à ajouter
    public static Integer choixPourMenuDAjout() {
        String contenu = "1-Bas Prix\n2-Régulier\n3-Charter\n4-Privé\n5-Terminer(ou esc/cancel)\n\n";
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

    //menu pour l'attribut à modifier
    public static int choixPourMenuModifier() {
        String contenu = """
                1-Destination
                2-Date Départ
                3-Numero d'avion
                4-Nb Reservation
                5-Modifier les extras
                6-Afficher
                7-Terminer (ou esc/cancel)
                """;
        contenu += "Que souhaitez-vous modifier?[1-7] : ";
        return inputInteger(contenu, "MENU MODIFICATION VOL");
    }

    // pour le listage
    public static String nomColonne(String suiteEntete, String titre) {
        String resultat = "\t\t\t\t\tListe Des Vols " + titre + "\n";
        resultat += ajouterEspacesFin(10, "Num Vol")
                + ajouterEspacesFin(20, "Destination")
                + ajouterEspacesFin(20, "Date de départ")
                + ajouterEspacesFin(10, "Avion")
                + ajouterEspacesFin(15, "Réservation")
                + suiteEntete + "\n"
                + "----------------------------------------------------------------------------------------------------------------------------"
                + "\n";
        return resultat;
    }

//    **************************************************************CONTROLLEUR (méthodes de manipulation)**************************************************************//

    public static String listerVolsBasPrix() {
        String suiteEnteteBasPrix = ajouterEspacesFin(15, "REPAS")
                + ajouterEspacesFin(15, "CHOIX SIEGE")
                + ajouterEspacesFin(15, "DIVERTISSEMENT")
                + ajouterEspacesFin(15, "ECOUTEURS");

        String resultat = nomColonne(suiteEnteteBasPrix, "Bas Prix");
        ArrayList<Integer> orderKeys = trierKey(); //pour parcourir les clés en ordre
        Vol unVol;
        for (Integer cle : orderKeys) {
            unVol = mapVols.get(cle);
            if (unVol instanceof VolBasPrix) {
                VolBasPrix volBasPrix = (VolBasPrix) unVol; //cast pour call la méthode toString spécifique à VolBasPrix
                resultat += volBasPrix.toString();
            }
        }

        return resultat;
    }

    public static String listerVolsReguliers() {

        String suiteEnteteRegulier = ajouterEspacesFin(15, "REPAS")
                + ajouterEspacesFin(15, "CHOIX SIEGE")
                + ajouterEspacesFin(15, "ESPACE ")
                + ajouterEspacesFin(15, "BAGAGE");

        String resultat = nomColonne(suiteEnteteRegulier, "Régulier");

        ArrayList<Integer> orderKeys = trierKey();
        Vol unVol;
        for (Integer cle : orderKeys) {
            unVol = mapVols.get(cle);
            if (unVol instanceof VolRegulier) {
                VolRegulier volRegulier = (VolRegulier) unVol;
                resultat += volRegulier.toString();
            }
        }
        return resultat;
    }

    public static String listerVolsCharters() {
        String suiteEnteteCharter = ajouterEspacesFin(15, "REPAS+")
                + ajouterEspacesFin(15, "CHOIX SIEGE+ ")
                + ajouterEspacesFin(15, "WIFI")
                + ajouterEspacesFin(15, "SALON VIP");

        String resultat = nomColonne(suiteEnteteCharter, "Charter");

        ArrayList<Integer> orderKeys = trierKey();
        Vol unVol;
        for (Integer cle : orderKeys) {
            unVol = mapVols.get(cle);
            if (unVol instanceof VolCharter) {
                VolCharter volCharter = (VolCharter) unVol;
                resultat += volCharter.toString();
            }
        }
        return resultat;
    }

    public static String listerVolsPrives() {
        String suiteEntetePrive = ajouterEspacesFin(15, "REPAS+")
                + ajouterEspacesFin(15, "CHOIX SIEGE+")
                + ajouterEspacesFin(15, "WIFI")
                + ajouterEspacesFin(15, "SALON VIP");

        String resultat = nomColonne(suiteEntetePrive, "Privé");

        ArrayList<Integer> orderKeys = trierKey();
        Vol unVol;
        for (Integer cle : orderKeys) {
            unVol = mapVols.get(cle);
            if (unVol instanceof VolPrive) {
                VolPrive volPrive = (VolPrive) unVol;
                resultat += volPrive.toString();
            }
        }
        return resultat;
    }

    public static void ajoutVol(String typeDeVol) {
        //exemple de ligne: B;14567;Toronto;12;12;2024;100;167;1;0;1;1

        int num = getNewNumVol();
        String dest = inputMessage("Entrez la destination", "AJOUTER UN VOL");

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

        int numAvion = inputInteger("Entrez le numéro de l'avion", "AJOUTER UN VOL");
        boolean ajoutInfoAvion = yesNoInputMessage("Voulez-vous ajouter des informations sur l'avion ?");
        Avion avion;
        if (ajoutInfoAvion) {
            String typeAvion = inputMessage("Entrez le type de l'avion (Boing, etc)", "AJOUTER UN VOL");
            int nbPlaces = inputInteger("Entrez le nombre de places disponible dans l'avion", "AJOUTER UN VOL");
            int categorie = inputInteger("Entrez la catégorie (1-Court Courrier, 2-Moyen Courrier, 3-Long Courrier)", "AJOUTER UN VOL");
            avion = new Avion(numAvion, typeAvion, nbPlaces, categorie);
        } else {
            avion = new Avion(numAvion);
        }
        int nbReservation = 0;

        String[] optionMessages = messageAddOnSelonLeType(typeDeVol);

        if (optionMessages.length >= 4) {
            Map<String, Boolean> boolMap = optionsRadioInputMessage(optionMessages[0], optionMessages[1], optionMessages[2], optionMessages[3]);

            boolean opt1 = boolMap.get("opt1");
            boolean opt2 = boolMap.get("opt2");
            boolean opt3 = boolMap.get("opt3");
            boolean opt4 = boolMap.get("opt4");

            if (typeDeVol.equals("Bas Prix")) {
                mapVols.put(num, new VolBasPrix(num, dest, dateDepart, avion, nbReservation, opt1, opt2, opt3, opt4));
            } else if (typeDeVol.equals("Regulier")) {
                mapVols.put(num, new VolRegulier(num, dest, dateDepart, avion, nbReservation, opt1, opt2, opt3, opt4));
            } else if (typeDeVol.equals("Charter")) {
                mapVols.put(num, new VolCharter(num, dest, dateDepart, avion, nbReservation, opt1, opt2, opt3, opt4));
            } else if (typeDeVol.equals("Prive")) {
                mapVols.put(num, new VolPrive(num, dest, dateDepart, avion, nbReservation, opt1, opt2, opt3, opt4));
            }

            message("Le vol a été ajouté avec succès !", "AJOUTER UN VOL");



        } else {
            errorMessage("Error: Insufficient option messages.");
        }


    }

    public static void supprimerVol() {
        try {
            Integer num;
            Vol volASupprimer;

            do {
                num = inputInteger("Saisir le numero du vol à supprimer", "Suppression");
                volASupprimer = getExistingVol(num);

                if (volASupprimer != null) {
                    mapVols.remove(num);
                    message("Le vol a été supprimé avec succès !", "Suppression");
                }
            } while (volASupprimer == null && num != -1);
        } catch (NumberFormatException e) {
            message("Format de numéro invalide. Veuillez saisir un numéro valide.", "Erreur");
        } catch (Exception e) {
            message("Une erreur s'est produite : " + e.getMessage(), "Erreur");
        }
    }

    public static void modifierVol() throws Exception {
        int type;
        Vol volTrouve = getExistingVol(inputInteger("Entrez le numero du vol à modifier", "modifier"));
        if (volTrouve == null) {
            menuGeneral();
            return;
        } else {
            afficherVol(volTrouve);
            do {
                type = choixPourMenuModifier();

                switch (type) {
                    case 1: //Destination
                        String dest = inputMessage("Entrez la nouvelle destination", "modifier");
                        volTrouve.setDestination(dest);
                        break;
                    case 2: // Date de départ
                        Date newDate = new Date();
                        boolean isValidDate;
                        do {
                            String dateStr = inputMessage("Entrez la nouvelle date (jj/mm/yyyy)", "modifier");
                            isValidDate = newDate.initFromStringAndValidation(dateStr); // Inverser la logique car false signifie maintenant que la date est valide

                            if (!isValidDate) { // Si la date est valide, sortir de la boucle
                                volTrouve.setDateDepart(newDate); // Mettre à jour la date de départ du vol trouvé
                            } else {
                                errorMessage("Date invalide. Veuillez réessayer."); // Afficher un message d'erreur si la date est invalide
                            }
                        } while (isValidDate); // Continuer jusqu'à ce qu'une date valide soit entrée
                        break;

                    case 3: // Modification Avion
                        Avion newAvion;
                        int numAvion = inputInteger("Entrez le nouveau numero d'avion", "modifier");
                        if (yesNoInputMessage("Voudriez-vous modifier les attributs de l'avion?")) {
                            String typeAvion = inputMessage("Entrez le type de l'avion (Boing, etc)", "AJOUTER UN VOL");
                            int nbPlaces = inputInteger("Entrez le nombre de places disponible dans l'avion", "AJOUTER UN VOL");
                            int categorie = inputInteger("Entrez la catégorie (1-Court Courrier, 2-Moyen Courrier, 3-Long Courrier)", "AJOUTER UN VOL");
                            newAvion = new Avion(numAvion, typeAvion, nbPlaces, categorie);
                        } else {
                            newAvion = new Avion(numAvion);
                        }
                        volTrouve.setAvion(newAvion);
                        break;
                    case 4: // nbRes
                        int nbRes = inputInteger("Entrez le nouveau nb de res", "modifier");
                        volTrouve.setNbReservation(nbRes);
                        break;

                    case 5: // Modifier les extras
                        Map<String, Boolean> optionsExtras;
                        String[] labelsExtras;

                        if (volTrouve instanceof VolBasPrix) {
                            labelsExtras = new String[]{"Repas extra", "Choix de siège extra", "Divertissement extra", "Écouteurs extra"};
                        } else if (volTrouve instanceof VolRegulier) {
                            labelsExtras = new String[]{"Repas inclus", "Choix de siège inclus", "Espace extra", "Bagage en soute extra"};
                        } else if (volTrouve instanceof VolCharter) {
                            labelsExtras = new String[]{"Repas luxe extra", "Choix de siège premium extra", "Wifi extra", "Salon VIP extra"};
                        } else if (volTrouve instanceof VolPrive) {
                            labelsExtras = new String[]{"Repas luxe inclus", "Choix de siège premium inclus", "Wifi inclus", "Salon VIP inclus"};
                        } else {
                            // Gérer le cas où le type de vol n'est pas reconnu
                            break;
                        }

                        optionsExtras = optionsRadioInputMessage(labelsExtras[0], labelsExtras[1], labelsExtras[2], labelsExtras[3]);

                        // Appliquer les options sélectionnées au vol trouvé
                        if (volTrouve instanceof VolBasPrix) {
                            VolBasPrix vol = (VolBasPrix) volTrouve;
                            vol.setRepasExtra(optionsExtras.get("opt1"));
                            vol.setChoixSiegeExtra(optionsExtras.get("opt2"));
                            vol.setDivertissementExtra(optionsExtras.get("opt3"));
                            vol.setEcouteursExtra(optionsExtras.get("opt4"));
                        } else if (volTrouve instanceof VolRegulier) {
                            VolRegulier vol = (VolRegulier) volTrouve;
                            vol.setRepasInclus(optionsExtras.get("opt1"));
                            vol.setChoixSiegeInclus(optionsExtras.get("opt2"));
                            vol.setEspaceExtra(optionsExtras.get("opt3"));
                            vol.setBagageSouteExtra(optionsExtras.get("opt4"));
                        } else if (volTrouve instanceof VolCharter) {
                            VolCharter vol = (VolCharter) volTrouve;
                            vol.setRepasLuxeExtra(optionsExtras.get("opt1"));
                            vol.setChoixSiegePremiumExtra(optionsExtras.get("opt2"));
                            vol.setWifiExtra(optionsExtras.get("opt3"));
                            vol.setSalonVIPExtra(optionsExtras.get("opt4"));
                        } else if (volTrouve instanceof VolPrive) {
                            VolPrive vol = (VolPrive) volTrouve;
                            vol.setRepasLuxeInclus(optionsExtras.get("opt1"));
                            vol.setChoixSiegePremiumInclus(optionsExtras.get("opt2"));
                            vol.setWifiInclus(optionsExtras.get("opt3"));
                            vol.setSalonVIPInclus(optionsExtras.get("opt4"));
                        }
                        break;

                    case 6: //afficher
                        afficherVol(volTrouve);
                        break;
                    case 7:
                        break;
                }
            } while (type != 7);
        }

    }

    public static void rechercherVol() {
        Integer numKey = inputInteger("Entrez le numero du vol à rechercher", "recherche");
        Vol vol = mapVols.get(numKey);
        if (vol == null) {
            message("Vol introuvable", "recherche");
            return;
                    }
        afficherVol(vol);
    }

    //**************************************************************Méthodes Utils Specifique à chaque fonctionnalité**************************************************************//

    // pour la recherche d'un vol à modifier ou supprimer
    public static Vol getExistingVol(Integer numKey) {
        Vol vol = mapVols.get(numKey);
        if (vol == null) {
            message("Vol introuvable", "recherche");
        }
        return vol;
    }

    //pour ajouter un vol qui n'existe pas
    public static int getNewNumVol() { // Retourne un numéro de vol qui n'existe pas
        boolean numDeVolExiste;
        Integer num;
        do {
            num = inputInteger("Entrez le numéro", "AJOUTER UN VOL");
            numDeVolExiste = mapVols.containsKey(num);
            if (numDeVolExiste) {
                message("Le numero de vol " + num + " existe déjà !", "AJOUTER UN VOL");

            }
        } while (numDeVolExiste);
        return num;
    }

    //pour l'ajout d'options
    public static String[] messageAddOnSelonLeType(String typeDeVol) {
        switch (typeDeVol) {
            case "Bas Prix":
                return new String[]{
                        "Repas (non inclus) ",
                        "Choix de siège (non inclus) ",
                        "Chaines de divertissements (non inclus) ",
                        "Écouteurs (non inclus) "
                };
            case "Regulier":
                return new String[]{
                        "Repas (inclus) ",
                        "Choix de siège (inclus) ",
                        "Espaces supplémentaires(extra) ",
                        "Bagage en soute (extra) "
                };
            case "Charter":
                return new String[]{
                        "Repas cinq étoiles (extra) ",
                        "Choix de siège premium (extra) ",
                        "Wifi (extra) ",
                        "Accès au salon VIP (extra) "
                };
            case "Prive":
                return new String[]{
                        "Repas cinq étoiles (inclus) ",
                        "Choix de siège premium (inclus) ",
                        "Wifi (inclus) ",
                        "Accès au salon VIP (inclus) "
                };
            default:
                return new String[]{};
        }
    }

    // pour la recherche
    public static void afficherVol(Vol vol) {

        String suiteColonne = null;
        if (vol instanceof VolBasPrix) {
            suiteColonne = ajouterEspacesFin(15, "REPAS")
                    + ajouterEspacesFin(15, "CHOIX SIEGE")
                    + ajouterEspacesFin(15, "DIVERTISSEMENT")
                    + ajouterEspacesFin(15, "ECOUTEURS");
        } else if (vol instanceof VolRegulier) {
            suiteColonne = ajouterEspacesFin(15, "REPAS")
                    + ajouterEspacesFin(15, "CHOIX SIEGE")
                    + ajouterEspacesFin(15, "ESPACE ")
                    + ajouterEspacesFin(15, "BAGAGE");
        } else if (vol instanceof VolCharter) {
            suiteColonne = ajouterEspacesFin(15, "REPAS+")
                    + ajouterEspacesFin(15, "CHOIX SIEGE+ ")
                    + ajouterEspacesFin(15, "WIFI")
                    + ajouterEspacesFin(15, "SALON VIP");
        } else if (vol instanceof VolPrive) {
            suiteColonne = ajouterEspacesFin(15, "REPAS+")
                    + ajouterEspacesFin(15, "CHOIX SIEGE+")
                    + ajouterEspacesFin(15, "WIFI")
                    + ajouterEspacesFin(15, "SALON VIP");
        }
        String nomColonne = nomColonne(suiteColonne, "Recherche");
        displayVolsPanel(nomColonne + "\n" + vol.toString());
    }

    //**************************************************************MAIN**************************************************************//

    public static void main(String[] args) throws Exception {
        File f = new File(FICHIER_VOLS_OBJ);
        if (f.exists()) {
            chargerVolsObj();
        } else {
            chargerVolsTxt();
        }
        mapVols = trierMap(mapVols);

//        displayAdvancedMenu();
        menuGeneral();
        // test: 10/10/2025
    } //main

}


