package org.example;

import static org.example.Utilitaires.ajouterEspacesFin;

public class VolCharter extends Vol implements extrasVol {
    boolean repasCinqEtoilesExtra;
    boolean choixSiegePremiumExtra;
    boolean wifiExtra;
    boolean salonVIPExtra;


    public VolCharter(int numDeVol, String destination, Date dateDepart, Avion avion, int nbReservation, boolean repasCinqEtoilesExtra, boolean choixSiegePremiumExtra, boolean wifiExtra, boolean salonVIPExtra) {
        super(numDeVol, destination, dateDepart, avion, nbReservation);
        this.repasCinqEtoilesExtra = repasCinqEtoilesExtra;
        this.choixSiegePremiumExtra = choixSiegePremiumExtra;
        this.wifiExtra = wifiExtra;
        this.salonVIPExtra = salonVIPExtra;
    }

    public void setRepasLuxeExtra(boolean repasLuxeExtra) {
        this.repasCinqEtoilesExtra = repasLuxeExtra;
    }

    public void setChoixSiegePremiumExtra(boolean choixSiegePremiumExtra) {
        this.choixSiegePremiumExtra = choixSiegePremiumExtra;
    }

    public void setWifiExtra(boolean wifiExtra) {
        this.wifiExtra = wifiExtra;
    }

    public void setSalonVIPExtra(boolean salonVIPExtra) {
        this.salonVIPExtra = salonVIPExtra;
    }

    public String toString() {
        String repasCinqEtoilesExtra = this.repasCinqEtoilesExtra ? "Oui" : "Non";
        String choixSiegePremiumExtra = this.choixSiegePremiumExtra ? "Oui" : "Non";
        String wifiExtra = this.wifiExtra ? "Oui" : "Non";
        String salonVIPExtra = this.salonVIPExtra ? "Oui" : "Non";

        return (
                super.toString()
                        + ajouterEspacesFin(15, repasCinqEtoilesExtra)
                        + ajouterEspacesFin(15, choixSiegePremiumExtra)
                        + ajouterEspacesFin(15, wifiExtra)
                        + ajouterEspacesFin(15, salonVIPExtra)
                        + "\n"
        );
    }
}
