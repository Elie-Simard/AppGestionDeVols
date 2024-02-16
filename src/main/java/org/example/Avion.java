package org.example;

import java.io.Serializable;
public class Avion implements Serializable {
    private int numAvion;
    private String typeAvion;
    private int nbPlaces;
    private int categorie; // 1 = Court-courrier, 2 = Moyen-courrier, 3 = Long-courrier
    String tabCategs[] = {"Court-courrier", "Moyen-Courrier", "Long-Courrier", "Inconnu"};


    public Avion(int numAvion, String typeAvion, int nbPlaces, int categorie) {
        this.numAvion = numAvion;
        this.typeAvion = typeAvion;
        this.nbPlaces = nbPlaces;
        this.setCategorieAvion(categorie);
    }

    public Avion(int numAvion) {
        this.numAvion = numAvion;
        this.typeAvion = "Inconnu";
        this.nbPlaces = 0;
        this.setCategorieAvion(3); // soit Inconnu
    }

    public int getNumAvion() {
        return this.numAvion;
    }

    public String getTypeAvion() {
        return this.typeAvion;
    }

    public int getNbPlaces() {
        return this.nbPlaces;
    }

    public String getCategorieString() {
        return tabCategs[this.categorie];
    }

    public void setNbPlaces(int nbPlaces) {
        this.nbPlaces = nbPlaces;
    }

    public void setTypeAvion(String typeAvion) {
        this.typeAvion = typeAvion;
    }

    public void setNumAvion(int numAvion) {
        this.numAvion = numAvion;
    }

    public void setCategorieAvion(int categorie) {
        int nbCategs = tabCategs.length;
        if (categorie >= 0 && categorie < nbCategs) {
            this.categorie = categorie;
        } else {
            System.out.println("Catégorie invalide !");
        }
    }

}
