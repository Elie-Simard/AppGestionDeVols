package org.Compagnie.model;

import static org.Compagnie.util.UtilVue.ajouterEspacesFin;

public class VolCharter extends Vol implements extrasVol {
    boolean repasCinqEtoilesExtra;
    boolean choixSiegePremiumExtra;
    boolean wifiExtra;
    boolean salonVIPExtra;

    public VolCharter(int numDeVol, boolean repasCinqEtoilesExtra, boolean choixSiegePremiumExtra, boolean wifiExtra, boolean salonVIPExtra) {
        super(numDeVol);
        this.repasCinqEtoilesExtra = repasCinqEtoilesExtra;
        this.choixSiegePremiumExtra = choixSiegePremiumExtra;
        this.wifiExtra = wifiExtra;
        this.salonVIPExtra = salonVIPExtra;
    }

    public int getRepasCinqEtoilesExtra() {
        return repasCinqEtoilesExtra ? 1 : 0;
    }

    public int getChoixSiegePremiumExtra() {
        return choixSiegePremiumExtra ? 1 : 0;
    }

    public int getWifiExtra() {
        return wifiExtra ? 1 : 0;
    }

    public int getSalonVIPExtra() {
        return salonVIPExtra ? 1 : 0;
    }

    public String toString() {
        String repasCinqEtoilesExtra = this.repasCinqEtoilesExtra ? "Oui" : "Non";
        String choixSiegePremiumExtra = this.choixSiegePremiumExtra ? "Oui" : "Non";
        String wifiExtra = this.wifiExtra ? "Oui" : "Non";
        String salonVIPExtra = this.salonVIPExtra ? "Oui" : "Non";

        return (super.toString()
                + ajouterEspacesFin(15, repasCinqEtoilesExtra)
                + ajouterEspacesFin(15, choixSiegePremiumExtra)
                + ajouterEspacesFin(15, wifiExtra)
                + ajouterEspacesFin(15, salonVIPExtra)
                + "\n");
    }
}
