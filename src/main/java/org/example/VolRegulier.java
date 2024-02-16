package org.example;

import static org.example.Utilitaires.ajouterEspacesFin;

public class VolRegulier extends Vol implements extrasVol {

    boolean repasInclus;
    boolean choixSiegeInclus;
    boolean espaceExtra;
    boolean bagageSouteExtra;


    public VolRegulier(int numDeVol, String destination, Date dateDepart, Avion avion, int nbReservation, boolean repasInclus, boolean choixSiegeInclus, boolean espaceExtra,  boolean bagageSouteExtra) {
        super(numDeVol, destination, dateDepart, avion, nbReservation);
        this.repasInclus = repasInclus;
        this.choixSiegeInclus = choixSiegeInclus;
        this.espaceExtra = espaceExtra;
        this.bagageSouteExtra = bagageSouteExtra;
    }

    public void setRepasInclus(boolean repasInclus) {
        this.repasInclus = repasInclus;
    }

    public void setChoixSiegeInclus(boolean choixSiegeInclus) {
        this.choixSiegeInclus = choixSiegeInclus;
    }

    public void setEspaceExtra(boolean espaceExtra) {
        this.espaceExtra = espaceExtra;
    }

    public void setBagageSouteExtra(boolean bagageSouteExtra) {
        this.bagageSouteExtra = bagageSouteExtra;
    }

    public String toString() {
        String repas = repasInclus ? "Oui" : "Non";
        String choixSiege = choixSiegeInclus ? "Oui" : "Non";
        String espace = espaceExtra ? "Oui" : "Non";
        String bagage = bagageSouteExtra ? "Oui" : "Non";

        return (
                super.toString()
                        + ajouterEspacesFin(15   , repas)
                        + ajouterEspacesFin(15, choixSiege)
                        + ajouterEspacesFin(15, espace)
                        + ajouterEspacesFin(15, bagage)
                        + "\n"
        );
    }
}
