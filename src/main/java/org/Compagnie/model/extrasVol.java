package org.Compagnie.model;

public interface extrasVol {
    // Cette interface est utilisée pour définir les extras de chaque type de vol

    // VolBasPrix
    boolean repasExtra = false;
    boolean choixSiegeExtra = false;
    boolean divertissementExtra = false;
    boolean ecouteursExtra = false;

    // VolRegulier
    boolean repasInclus = false;
    boolean choixSiegeInclus = false;
    boolean espaceExtra = false;
    boolean bagageSouteExtra = false;

    // VolCharter
    boolean repasCinqEtoilesExtra = false;
    boolean choixSiegePremiumExtra = false;
    boolean wifiExtra = false;
    boolean salonVIPExtra = false;

    // VolPrivé
    boolean isRepasCinqEtoiles = false;
    boolean choixSiegePremium = false;
    boolean wifi = false;
    boolean salonVIP = false;

}
