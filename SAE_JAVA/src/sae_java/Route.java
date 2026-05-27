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

    public Sommet getsDépart() {
        return sDépart;
    }

    public String getsArrivée() {
        return sArrivée;
    }

    

}
