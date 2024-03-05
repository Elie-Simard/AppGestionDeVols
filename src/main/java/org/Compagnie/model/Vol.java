package org.Compagnie.model;

import java.io.Serializable;

import static org.Compagnie.util.UtilVue.ajouterEspacesFin;
import static org.Compagnie.util.UtilVue.errorMessage;

public class Vol implements Serializable {

    private static final long serialVersionUID = 2040890116313738088L;

    private int numDeVol;
    private String destination;
    private Date dateDepart;
    private Avion avion;
    private int nbReservation;

    public Vol(int numDeVol, String destination, Date dateDepart, Avion avion) {
        this.numDeVol = numDeVol;
        this.destination = destination;
        setDateDepart(dateDepart);
        this.avion = avion;
        this.nbReservation = 0;
    }

    public Vol(int numDeVol) { //pour passer aux classes filles
        this.numDeVol = numDeVol;
    }
    public void setDateDepart(Date dateDepart) {
        boolean isValidDate = Date
                .validerDate(dateDepart.getJour(), dateDepart.getMois(), dateDepart.getAn(), new boolean[3]).isEmpty();
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

    public String getDateDepartString() {
        return this.dateDepart.toString();
    }

    public int getNumAvion() {
        return this.avion.getNumAvion(); // va chercher dans la classe avion le numero de l'avion
    }

    public int getNbReservation() {
        return this.nbReservation;
    }

    public int compareTo(Vol vol) { // - la méthode compareTo( ) qui permet de comparer deux vols selon leur numéro
                                    // de vol, pour ainsi pouvoir les trier par ordre croissant de numéro de vol a
                                    // l'interieur d'une liste(arraylist, linkedList
        return this.numDeVol - vol.numDeVol;
    }

    public String toString() {
        return (ajouterEspacesFin(10, String.valueOf(getNumDeVol()))
                + ajouterEspacesFin(20, getDestination())
                + (getDateDepart() != null ? ajouterEspacesFin(20, getDateDepart().toString()) : "N/A")
                + ajouterEspacesFin(10, String.valueOf(getNumAvion()))
                + ajouterEspacesFin(15, String.valueOf(getNbReservation())));
    }
}
