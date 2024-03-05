package org.Compagnie.model;

import static org.Compagnie.util.UtilVue.ajouterEspacesFin;

public class VolBasPrix extends Vol implements extrasVol {
    boolean repasExtra;
    boolean choixSiegeExtra;
    boolean divertissementExtra;
    boolean ecouteursExtra;

    //constructeur passant que les valeurs necessaires pour les tables par type de vol
    public VolBasPrix(int numDeVol, boolean repasExtra, boolean choixSiegeExtra, boolean divertissementExtra, boolean ecouteursExtra) {
        super(numDeVol);
        this.repasExtra = repasExtra;
        this.choixSiegeExtra = choixSiegeExtra;
        this.divertissementExtra = divertissementExtra;
        this.ecouteursExtra = ecouteursExtra;
    }

    //les getters pour aller insérer les valeurs dans les tables lors de l'ajout d'un vol
    public int getRepasXtra() {
        return repasExtra ? 1 : 0;
    }

    public int getChoixSiegeXtra() {
        return choixSiegeExtra ? 1 : 0;
    }

    public int getDivertissementXtra() {
        return divertissementExtra ? 1 : 0;
    }

    public int getEcouteursXtra() {
        return ecouteursExtra ? 1 : 0;
    }

    public String toString() {
        String repas = repasExtra ? "Oui" : "Non";
        String choixSiege = choixSiegeExtra ? "Oui" : "Non";
        String divertissement = divertissementExtra ? "Oui" : "Non";
        String ecouteurs = ecouteursExtra ? "Oui" : "Non";

        return (super.toString()
                + ajouterEspacesFin(15, repas)
                + ajouterEspacesFin(15, choixSiege)
                + ajouterEspacesFin(15, divertissement)
                + ajouterEspacesFin(15, ecouteurs)
                + "\n");
    }

}
