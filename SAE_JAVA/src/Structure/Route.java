/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Structure;

/**
 *
 * @author compteadmin
 */
public class Route {

    String name;
    double fiabilite;
    int distance;
    int duree;
    private Sommet sDepart;
    private Sommet sArrivee;

    public Route(String name, double fiabilite, int distance, int duree, Sommet sD, Sommet sA) {
        this.name = name;
        this.fiabilite = fiabilite;
        this.distance = distance;
        this.duree = duree;
        this.sDepart = sD;
        this.sArrivee = sA;
    }

    public String getName() {
        return name;
    }

    public double getFiabilite() {
        return fiabilite;
    }

    public int getDistance() {
        return distance;
    }

    public int getDuree() {
        return duree;
    }

    public Sommet getSommetDepart() {
        return sDepart;
    }

    public Sommet getSommetArrivee() {
        return sArrivee;
    }

    

}
