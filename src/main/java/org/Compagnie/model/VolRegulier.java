package org.Compagnie.model;

import static org.Compagnie.util.UtilVue.ajouterEspacesFin;

public class VolRegulier extends Vol implements extrasVol {

    boolean repasInclus;
    boolean choixSiegeInclus;
    boolean espaceExtra;
    boolean bagageSouteExtra;

    //constructeur passant que les valeurs necessaires pour les tables par type de vol
    public VolRegulier(int numDeVol, boolean repasInclus, boolean choixSiegeInclus, boolean espaceExtra, boolean bagageSouteExtra) {
        super(numDeVol);
        this.repasInclus = repasInclus;
        this.choixSiegeInclus = choixSiegeInclus;
        this.espaceExtra = espaceExtra;
        this.bagageSouteExtra = bagageSouteExtra;
    }

    //les getters pour aller insérer les valeurs dans les tables lors de l'ajout d'un vol
    public int getRepasInclus() {
        return repasInclus ? 1 : 0;
    }

    public int getChoixSiegeInclus() {
        return choixSiegeInclus ? 1 : 0;
    }

    public int getEspaceExtra() {
        return espaceExtra ? 1 : 0;
    }

    public int getBagageSouteExtra() {
        return bagageSouteExtra ? 1 : 0;
    }

    public String toString() {
        String repas = repasInclus ? "Oui" : "Non";
        String choixSiege = choixSiegeInclus ? "Oui" : "Non";
        String espace = espaceExtra ? "Oui" : "Non";
        String bagage = bagageSouteExtra ? "Oui" : "Non";

        return (super.toString()
                + ajouterEspacesFin(15, repas)
                + ajouterEspacesFin(15, choixSiege)
                + ajouterEspacesFin(15, espace)
                + ajouterEspacesFin(15, bagage)
                + "\n");
    }
}
