package org.Compagnie.model;

import static org.Compagnie.util.UtilVue.ajouterEspacesFin;

public class VolPrive extends Vol implements extrasVol {
    boolean repasCinqEtoiles;
    boolean choixSiegePremium;
    boolean wifi;
    boolean salonVIP;

    public VolPrive(int numDeVol, boolean repasCinqEtoiles,
            boolean choixSiegePremium, boolean wifi, boolean salonVIP) {
        super(numDeVol);
        this.repasCinqEtoiles = repasCinqEtoiles;
        this.choixSiegePremium = choixSiegePremium;
        this.wifi = wifi;
        this.salonVIP = salonVIP;
    }

    public int getRepasCinqEtoiles() {
        return repasCinqEtoiles ? 1 : 0;
    }

    public int getChoixSiegePremium() {
        return choixSiegePremium ? 1 : 0;
    }

    public int getWifi() {
        return wifi ? 1 : 0;
    }

    public int getSalonVIP() {
        return salonVIP ? 1 : 0;
    }
    public String toString() {
        String repasCinqEtoiles = this.repasCinqEtoiles ? "Oui" : "Non";
        String choixSiegePremium = this.choixSiegePremium ? "Oui" : "Non";
        String wifi = this.wifi ? "Oui" : "Non";
        String salonVIP = this.salonVIP ? "Oui" : "Non";

        return (super.toString()
                + ajouterEspacesFin(15, repasCinqEtoiles)
                + ajouterEspacesFin(15, choixSiegePremium)
                + ajouterEspacesFin(15, wifi)
                + ajouterEspacesFin(15, salonVIP)
                + "\n");
    }
}
