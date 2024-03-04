package org.Compagnie.connexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnexionBD {

    private Connection connexion = null;
    // **************************** SINGLETON
    // ***********************************************
    private static ConnexionBD instance = null;

    // Constructeur privé et donc on ne pourra pas créer des objets exterieures
    // à la classe puisqueon ne peut pas appeler le constructeur par le fait
    // qu'il est privé.
    private ConnexionBD() {
    }

    // Cette méthode se certifie qu'on crée une seule instance et que retourne
    // celle-ci.
    // Évidement elle est publique pour qu'on puisse l'appeller.
    public static ConnexionBD getInstance() {
        if (instance == null) {
            instance = new ConnexionBD();
        }
        return instance;
    }
    // **************************************************************************************

    // Code métier de notre classe Connexion
    public Connection getConnexionMySQL() throws SQLException {
        try {
            // Exemple dans notre cas :
            // "jdbc:mysql://localhost/vols?user=root&password="
            connexion = DriverManager.getConnection("jdbc:mysql://" + Env.SERVEUR_BD + "/" + Env.BD + "?user="
                    + Env.USER_BD + "&password=" + Env.PASS_BD);
        } catch (SQLException e) {
            System.out.println("Erreur SQL lors de la connection au serveur");
            connexion = null;
        }
        return connexion;
    }

    public static void main(String[] args) throws SQLException { // POUR TESTER
        ConnexionBD connexionBD = ConnexionBD.getInstance();
        Connection connexion = connexionBD.getConnexionMySQL();
    }
}
