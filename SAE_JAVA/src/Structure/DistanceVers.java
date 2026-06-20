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

    public DistanceVers(Sommet sommet, int distance, int duree, int dureeEstimee) {
        this.sommet = sommet;
        this.distance = distance;
        this.duree = duree;
        this.dureeEstimee = dureeEstimee;
    }

    public Sommet getSommet() {
        return sommet;
    }

    public String getNom() {
        return sommet.getNom();
    }

    public String getType() {
        return sommet.getType();
    }

    public int getDistance() {
        return distance;
    }

    public int getDuree() {
        return duree;
    }

    public int getDureeEstimee() {
        return dureeEstimee;
    }

    @Override
    public String toString() {
        return sommet.getNom() + " (" + sommet.getType() + ") : " + distance + "km" + " : " + duree + "min" + " : " + dureeEstimee + "min estimee\n";
    }
}
