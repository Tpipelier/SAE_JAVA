/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sae_java;

/**
 *
 * @author compteadmin
 */
public class Route {

    String name;
    double fiabilité;
    int distance;
    int durée;
    private Sommet sDépart;
    String sArrivée;

    public Route(String name, double fiabilité, int distance, int durée, Sommet sD, String sA) {
        this.name = name;
        this.fiabilité = fiabilité;
        this.distance = distance;
        this.durée = durée;
        this.sDépart = sD;
        this.sArrivée = sA;
    }

    public String getName() {
        return name;
    }

    public double getFiabilité() {
        return fiabilité;
    }

    public int getDistance() {
        return distance;
    }

    public int getDurée() {
        return durée;
    }

    /**
     * Durée estimée du trajet (en minutes pondérées). La durée standard est
     * pénalisée par un malus d'autant plus important que la route est peu
     * fiable. Formule : durée × (20 - fiabilité) / 10, la fiabilité étant
     * exprimée sur une échelle de 1 (10 %) à 10 (100 %).
     *
     * @return la durée estimée prenant en compte la fiabilité
     */
    public double getDuréeEstimée() {
        return durée * (20 - fiabilité) / 10.0;
    }

    public Sommet getsDépart() {
        return sDépart;
    }

    public String getsArrivée() {
        return sArrivée;
    }

    

}
