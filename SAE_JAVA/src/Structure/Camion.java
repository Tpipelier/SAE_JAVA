/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Structure;

import java.util.List;

/**
 *
 * @author theop
 */
public class Camion {

    private static int nbCamion = 0;
    private String nom;
    private Sommet sommetDepart;
    private Sommet sommetArrivee;
    private int tempsTrajet;//cumule des temps de trajet en minute
    private List<Sommet> parcours;
    private boolean enMission;

    /**
     *
     * @param sommetDepart
     * @param sommetArrivee
     * @param tempsTrajet
     */
    public Camion(Sommet sommetDepart, Sommet sommetArrivee, int tempsTrajet) {
        nbCamion++;
        this.nom = "V-" + nbCamion;
        this.sommetDepart = sommetDepart;
        this.sommetArrivee = sommetArrivee;
        this.tempsTrajet = tempsTrajet;
        this.parcours.add(sommetDepart);
        this.enMission=true;
    }

    /**
     *
     * @return int
     */
    public static int getNbCamion() {
        return nbCamion;
    }

    /**
     *
     * @return String
     */
    public String getNom() {
        return nom;
    }

    /**
     *
     * @return Sommet
     */
    public Sommet getSommetDepart() {
        return sommetDepart;
    }

    /**
     *
     * @return Sommet
     */
    public Sommet getSommetArrivee() {
        return sommetArrivee;
    }

    /**
     *
     * @return int
     */
    public int getTempsTrajet() {
        return tempsTrajet;
    }

    /**
     *
     * @return List
     */
    public List<Sommet> getParcours() {
        return parcours;
    }

    /**
     *
     * @param sommet
     */
    public void ajouterAuParcours(Sommet sommet) {
        parcours.add(sommet);
    }

    /**
     *
     * @param prochainSommet
     * @param tempsTrajet
     */
    public void deplacerVers(Sommet prochainSommet, int tempsTrajet) {
        this.enMission=true;
        this.sommetDepart = this.sommetArrivee;
        ajouterAuParcours(this.sommetArrivee);
        this.sommetArrivee = prochainSommet;
        this.tempsTrajet += tempsTrajet;

    }
    /**
     * stop le camion au sommet suivant
     */
    public void stopperCamion(){
        this.sommetDepart = this.sommetArrivee;
        this.tempsTrajet = 0; 
        this.enMission = false;
    }

}
