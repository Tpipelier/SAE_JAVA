/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Structure;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import Structure.Sommet;
import Structure.Route;

/**
 * Petit resultat associant un sommet d'arrivee a la distance la plus courte
 * depuis le sommet de depart. Donne acces au nom et au type.
 * @author Theo Pipelier
 */
public class DistanceVers {

    private final Sommet sommet;
    private final int distance;
    private final int duree;
    private final int dureeEstimee;


    /**
     * Construit un resultat de distance vers un sommet.
     *
     * @param sommet       le sommet d'arrivee
     * @param distance     distance la plus courte (en km) depuis le depart
     * @param duree        duree la plus courte (en min) depuis le depart
     * @param dureeEstimee duree estimee (ponderee par la fiabilite) en min
     */
    public DistanceVers(Sommet sommet, int distance, int duree, int dureeEstimee) {
        this.sommet = sommet;
        this.distance = distance;
        this.duree = duree;
        this.dureeEstimee = dureeEstimee;
    }


    /**
     * Renvoie le sommet d'arrivee.
     *
     * @return le sommet associe a ce resultat
     */
    public Sommet getSommet() {
        return sommet;
    }


    /**
     * Renvoie le nom du sommet d'arrivee.
     *
     * @return le nom du sommet
     */
    public String getNom() {
        return sommet.getNom();
    }


    /**
     * Renvoie le type du sommet d'arrivee.
     *
     * @return le type du sommet
     */
    public String getType() {
        return sommet.getType();
    }


    /**
     * Renvoie la distance la plus courte vers le sommet.
     *
     * @return la distance en kilometres
     */
    public int getDistance() {
        return distance;
    }


    /**
     * Renvoie la duree la plus courte vers le sommet.
     *
     * @return la duree en minutes
     */
    public int getDuree() {
        return duree;
    }


    /**
     * Renvoie la duree estimee (ponderee par la fiabilite) vers le sommet.
     *
     * @return la duree estimee en minutes
     */
    public int getDureeEstimee() {
        return dureeEstimee;
    }


    /**
     * Renvoie une representation textuelle du resultat (nom, type, distance,
     * duree et duree estimee).
     *
     * @return une chaine decrivant ce resultat
     */
    @Override
    public String toString() {
        return sommet.getNom() + " (" + sommet.getType() + ") : " + distance + "km" + " : " + duree + "min" + " : " + dureeEstimee + "min estimee\n";
    }
}
