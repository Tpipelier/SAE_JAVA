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
    public String name;
    public double valuation;
    private Sommet sDépart;
    private Sommet sArrivée;

    public Route(String name, double valuation, Sommet sDépart, Sommet sArrivée) {
        this.name = name;
        this.valuation = valuation;
        this.sDépart = sDépart;
        this.sArrivée = sArrivée;
    }
    
    
}
