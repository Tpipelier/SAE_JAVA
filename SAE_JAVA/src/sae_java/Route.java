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
    private Sommet sArrivée;

    public Route(String name, double fiabilité, int distance, int durée, Sommet sDépart, Sommet sArrivée) {
        this.name = name;
        this.fiabilité = fiabilité;
        this.distance = distance;
        this.durée = durée;
        this.sDépart = sDépart;
        this.sArrivée = sArrivée;
    }

    

    public String getName() {
        return name;
    }

    public double getValuation() {
        return valuation;
    }

    public Sommet getsDépart() {
        return sDépart;
    }

    public Sommet getsArrivée() {
        return sArrivée;
    }
    
    
}
