package org.example;
import java.time.LocalDate;
import java.io.Serializable;

import static org.example.Utilitaires.errorMessage;

public class Date implements Serializable {
    private int jour;
    private int mois;
    private int an;
    private static String[] tabMois = {null, "Janvier", "Février", "Mars", "Avril", "Mai", "Juin", "Juillet",
            "Août", "Septembre", "Octobre", "Novembre", "Décembre"};
    static public LocalDate dateActuelle = LocalDate.now();
    public Date(){
        jour= mois = 1;
    };
    public Date(int jour, int mois, int an) {
        setAn(an);
        setMois(mois);
        setJour(jour);
    }

    public int getJour() {
        return jour;
    }
    public int getMois() {
        return mois;
    }
    public int getAn() {
        return an;
    }
    public void setJour(int jour) {
        int nbJours = determinerNbJoursDuMois(this.mois, this.an);
        if(jour > nbJours || jour < 1) {
            throw new IllegalArgumentException("Jour invalide pour le mois de " + tabMois[mois]);
        }
        this.jour = jour;
    }

    public void setMois(int mois) {
        if(mois < 1 || mois > 12) {
            throw new IllegalArgumentException("Le mois " + mois + " est invalide");
        }
        this.mois = mois;
    }

    public void setAn(int an) {
        if(an < LocalDate.now().getYear()) {
            throw new IllegalArgumentException("L'année doit être supérieure ou égale à l'année actuelle");
        }
        this.an = an;
    }


    public boolean initFromStringAndValidation(String dateStr) {
        String[] parts = dateStr.split("/");
        boolean hasError = false; // Indicateur d'erreur

        if (parts.length == 3) {
            int jour = Integer.parseInt(parts[0]);
            int mois = Integer.parseInt(parts[1]);
            int an = Integer.parseInt(parts[2]);

            try {
                setJour(jour);
            } catch (IllegalArgumentException e) {
                errorMessage(e.getMessage());
                hasError = true;
            }

            try {
                setMois(mois);
            } catch (IllegalArgumentException e) {
                errorMessage(e.getMessage());
                hasError = true;
            }

            try {
                setAn(an);
            } catch (IllegalArgumentException e) {
                errorMessage(e.getMessage());
                hasError = true;
            }
        } else {
            errorMessage("Format de date invalide : " + dateStr);
            hasError = true;
        }

        return hasError; // Retourne true s'il y a une erreur, false sinon
    }


    public static String validerDate(int jour, int mois , int an, boolean etat[]){
    String message="";
    int nbJours=0;

    if (mois < 1 || mois > 12){
       etat[1] = false;
    } else etat[1] = true;
    if(etat[1]){
        nbJours = determinerNbJoursDuMois(mois, an);
        if(jour < 1 || jour > nbJours){
            etat[0] = false;
            message+="Jour invalide pour le mois de " + tabMois[mois];
        } else etat[0] = true;

    } else { message+="Le Mois " + tabMois[mois] + " est invalide"; }
    int anneeActuelle = dateActuelle.getYear();
    if (an < anneeActuelle) {
        etat[2] = false;
        message += "L'année doit être supérieure ou égale à l'année actuelle, soit" + anneeActuelle;
    } else etat[2] = true;
    return message;
    }
    public static Boolean validerDateReservation(Date dateReservation) {
        boolean etatDate = true;

        if(dateReservation.getMois() < dateActuelle.getMonthValue() && dateReservation.getAn() <= dateActuelle.getYear()){
                etatDate = false;
                errorMessage("Le mois de réservation doit être supérieure ou égale à la date actuelle");
        }

        if(etatDate){
            if(dateReservation.getJour() < dateActuelle.getDayOfMonth() && dateReservation.getMois() <= dateActuelle.getMonthValue() && dateReservation.getAn() <= dateActuelle.getYear()){
                etatDate = false;
                errorMessage("Le jour de réservation doit être supérieure ou égale à la date actuelle");
            }
        } else
            errorMessage("La date de réservation doit être supérieure ou égale à la date actuelle");

        return etatDate;

    }

    public static int determinerNbJoursDuMois(int mois, int an) {
        int nbJours = 0;
        int tabJrMois[] = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

        if (mois == 2 && estBissextile(an)){
            nbJours = 29;
        }
        else nbJours = tabJrMois[mois];
        return nbJours;
    }
    public static boolean estBissextile(int an){
        return (an % 4 == 0 && an % 100 != 0) || (an % 400 == 0);
    }

    public String toString(){
        String leJour, leMois;
        leJour= Utilitaires.ajouterCaractereGauche('0', 2, this.jour+"");
        leMois= Utilitaires.ajouterCaractereGauche('0', 2, this.mois+"");
    return   leJour + '/' + leMois + '/' + an;
    }



}
