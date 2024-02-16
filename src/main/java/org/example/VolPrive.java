package org.example;

public class VolPrive extends Vol implements extrasVol {
    boolean repasCinqEtoiles;
    boolean choixSiegePremium;
    boolean wifi;
    boolean salonVIP;

    public VolPrive(int numDeVol, String destination, Date dateDepart, Avion avion, int nbReservation, boolean repasCinqEtoiles,
                      boolean choixSiegePremium, boolean wifi, boolean salonVIP) {
        super(numDeVol, destination, dateDepart, avion, nbReservation);
        this.repasCinqEtoiles = repasCinqEtoiles;
        this.choixSiegePremium = choixSiegePremium;
        this.wifi = wifi;
        this.salonVIP = salonVIP;
    }

    public void setRepasLuxeInclus(boolean repasLuxeInclus) {
        this.repasCinqEtoiles = repasLuxeInclus;
    }

    public void setChoixSiegePremiumInclus(boolean choixSiegePremiumInclus) {
        this.choixSiegePremium = choixSiegePremiumInclus;
    }

    public void setWifiInclus(boolean wifiInclus) {
        this.wifi = wifiInclus;
    }

    public void setSalonVIPInclus(boolean salonVIPInclus) {
        this.salonVIP = salonVIPInclus;
    }

    public String toString() {
        String repasCinqEtoiles = this.repasCinqEtoiles ? "Oui" : "Non";
        String choixSiegePremium = this.choixSiegePremium ? "Oui" : "Non";
        String wifi = this.wifi ? "Oui" : "Non";
        String salonVIP = this.salonVIP ? "Oui" : "Non";

        return (
                super.toString()
                        + Utilitaires.ajouterEspacesFin(15, repasCinqEtoiles)
                        + Utilitaires.ajouterEspacesFin(15, choixSiegePremium)
                        + Utilitaires.ajouterEspacesFin(15, wifi)
                        + Utilitaires.ajouterEspacesFin(15, salonVIP)
                        + "\n"
        );
    }

}
