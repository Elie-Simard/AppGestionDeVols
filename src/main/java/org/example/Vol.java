package org.example;

import static org.example.Utilitaires.*;
import java.io.Serializable;
public class Vol implements Serializable {

    private static final long serialVersionUID = 2040890116313738088L;

    private int numDeVol;
    private String destination;
    private Date dateDepart;
    private Avion avion;
    private int nbReservation;

    public Vol(int numDeVol, String destination, Date dateDepart, Avion avion, int nbReservation) {
        this.numDeVol = numDeVol;
        this.destination = destination;
        setDateDepart(dateDepart);
        this.avion = avion;
        this.nbReservation = nbReservation;
    }

    public void setNbReservation(int nbReservation) {
        this.nbReservation = nbReservation;
    }
    public void setDateDepart(Date dateDepart) {
        boolean isValidDate = Date.validerDate(dateDepart.getJour(), dateDepart.getMois(), dateDepart.getAn(), new boolean[3]).isEmpty();
        boolean isReservationValid = Date.validerDateReservation(dateDepart);

        if (isValidDate && isReservationValid) {
            this.dateDepart = dateDepart;
        } else {
            errorMessage("Date invalide");
        }
    }

    public int getNumDeVol() {
        return this.numDeVol;
    }
    public String getDestination() {
        return this.destination;
    }

    public Date getDateDepart() {
        return this.dateDepart;
    }

    public int getNumAvion() {
        return this.avion.getNumAvion(); //va chercher dans la classe avion le numero de l'avion
    }

    public int getNbReservation() {
        return this.nbReservation;
    }

    public int compareTo(Vol vol) { // - la méthode compareTo( ) qui permet de comparer deux vols selon leur numéro de vol, pour ainsi pouvoir les trier par ordre croissant de numéro de vol a l'interieur d'une liste(arraylist, linkedList
        return this.numDeVol - vol.numDeVol;
    }

    public void setDestination(String dest) {
        this.destination = dest;
    }

    public void setAvion(Avion avion) {
        this.avion = avion;
    }

    public String toString() {
        return (
                ajouterEspacesFin(10, String.valueOf(getNumDeVol()))
                        + ajouterEspacesFin(20, getDestination())
                        + (getDateDepart() != null ? ajouterEspacesFin(20, getDateDepart().toString()) : "N/A")
                        + ajouterEspacesFin(10, String.valueOf(getNumAvion()))
                        + ajouterEspacesFin(15, String.valueOf(getNbReservation()))
        );
    }

}


