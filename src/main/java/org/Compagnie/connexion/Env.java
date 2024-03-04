package org.Compagnie.connexion;

// Env pour Environnement. Je vais y placer des
// variables ou constantes qui seront partagées
public class Env {

    // Constantes d'environnement pour la connexion à la base de données
    public static final String SERVEUR_BD = "localhost";
    public static final String BD = "vols";
    public static final String USER_BD = "root";
    public static final String PASS_BD = "";

    // ****************** PARTIE SINGLETON *********************
    // La seule instance de cette classe
    private static Env instance = null;

    private Env() {
    }

    public static Env getInstance() {
        if (instance == null) {
            instance = new Env();
        }
        return instance;
    }
    // ************************ FIN PARTIE SINGLETON ********************

}