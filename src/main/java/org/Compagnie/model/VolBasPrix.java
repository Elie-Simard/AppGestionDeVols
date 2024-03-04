package org.Compagnie.model;

import static org.Compagnie.util.UtilVue.ajouterEspacesFin;

public class VolBasPrix extends Vol implements extrasVol {
    boolean repasExtra;
    boolean choixSiegeExtra;
    boolean divertissementExtra;
    boolean ecouteursExtra;

    public VolBasPrix(int numDeVol, String destination, Date dateDepart, Avion avion, int nbReservation,
            boolean repasExtra, boolean choixSiegeExtra, boolean divertissementExtra, boolean ecouteursExtra) {
        super(numDeVol, destination, dateDepart, avion, nbReservation);
        this.repasExtra = repasExtra;
        this.choixSiegeExtra = choixSiegeExtra;
        this.divertissementExtra = divertissementExtra;
        this.ecouteursExtra = ecouteursExtra;
    }

    public void setRepasExtra(boolean repasExtra) {
        this.repasExtra = repasExtra;
    }

    public void setChoixSiegeExtra(boolean choixSiegeExtra) {
        this.choixSiegeExtra = choixSiegeExtra;
    }

    public void setDivertissementExtra(boolean divertissementExtra) {
        this.divertissementExtra = divertissementExtra;
    }

    public void setEcouteursExtra(boolean ecouteursExtra) {
        this.ecouteursExtra = ecouteursExtra;
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

}
