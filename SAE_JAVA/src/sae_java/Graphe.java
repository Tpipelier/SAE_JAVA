/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sae_java;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;
import static sae_java.SAE_JAVA.compteSommet;

/**
 *
 * @author walid
 */
public class Graphe {

    private Sommet[] tabS;
    private Route[][] tabR;
    private int nbSommets;

    public Graphe(int nbSommets) {
        this.nbSommets = nbSommets;
        this.tabS = new Sommet[nbSommets];
        this.tabR = new Route[nbSommets][nbSommets];
    }

    public Sommet[] getTabS() {
        return tabS;
    }

    public Route[][] getTabR() {
        return tabR;
    }

    public int getNbSommets() {
        return nbSommets;
    }

    public Sommet getSommet(int i) {
        return tabS[i];
    }

    public Route getRoute(int i, int j) {
        return tabR[i][j];
    }

    public void setSommet(int i, Sommet s) {
        tabS[i] = s;
    }

    public void setRoute(int i, int j, Route r) {
        tabR[i][j] = r;
    }

    public static Graphe genererGrapheAleatoire(int nbSommets) {
        Graphe graphe = new Graphe(nbSommets);
        Random random = new Random();
        int numRoute = 0;

        // 1. Création des sommets avec un type aléatoire
        for (int i = 0; i < nbSommets; i++) {
            int tirage = random.nextInt(5);
            String type;
            if (tirage < 3) {
                type = "M";
            } else if (tirage == 3) {
                type = "O";
            } else {
                type = "N";
            }

            graphe.setSommet(i, new Sommet("S" + (i + 1), type));
        }

        // 2. Création des arêtes avec probabilité 1/5
        for (int i = 0; i < nbSommets; i++) {
            for (int j = i + 1; j < nbSommets; j++) {
                if (random.nextInt(5) == 0) {
                    double fiabilite = (random.nextInt(9) + 2) / 10.0;
                    int distance = random.nextInt(41) + 10;
                    int duree = random.nextInt(111) + 10;

                    Route r = new Route("R" + numRoute, fiabilite, distance, duree, graphe.getSommet(i), graphe.getSommet(j).getNom());
                    graphe.setRoute(i, j, r);
                    numRoute++;
                }
            }
        }

        return graphe;
    }

}
