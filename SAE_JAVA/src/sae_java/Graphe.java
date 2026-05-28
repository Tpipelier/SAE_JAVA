/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sae_java;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Theo Pipelier
 */
public class Graphe {

    int nbSommet;
    Sommet[] tabS;
    Route[][] tabR;
    HashMap<Route, Integer> durees;

    public Graphe() throws FileNotFoundException {
        this.nbSommet = 0;
        this.tabS = null;
        this.tabR = null;
        this.durees = null;

    }

    public static int compteSommet(String nomFichier) {
        int cpt = 0;
        try {
            // Le fichier d'entrée
            FileInputStream file = new FileInputStream(nomFichier);
            Scanner scanner = new Scanner(file);

            //renvoie true tant qu'il y a une autre ligne à lire
            while (scanner.hasNext()) {
                cpt++;
                scanner.nextLine();
            }
            scanner.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 0;
        }

        return cpt;

    }

    public int getNbSommet() {
        return nbSommet;
    }

    public Sommet getSommet(int numeroSommet) {
        return tabS[numeroSommet];
    }

    public Route getRoute(int numeroSommetD, int numeroSommetA) {
        return tabR[numeroSommetD][numeroSommetA];
    }

    public Sommet[] getTabS() {
        return tabS;
    }

    public Route[][] getTabR() {
        return tabR;
    }

    public HashMap<Route, Integer> getDurees() {
        return this.durees;
    }

    public void setSommet(int numeroSommet, Sommet S) {
        this.tabS[numeroSommet] = S;
    }

    public void setRoute(int numeroSommetD, int numeroSommetA, Route R) {
        this.tabR[numeroSommetD][numeroSommetA] = R;
    }

    public void setDuree(Route R, int duree) {
        this.durees.put(R, duree);
    }

    public void chargerGraphes(String nomFichier) throws FileNotFoundException {
        this.nbSommet = compteSommet(nomFichier);
        this.tabS = new Sommet[nbSommet];
        this.tabR = new Route[nbSommet][nbSommet];
        this.durees = new HashMap<>();

        // Le fichier d'entrée
        FileInputStream fichier = new FileInputStream(nomFichier);
        Scanner scanner = new Scanner(fichier);

        //renvoie true tant qu'il y a une autre ligne à lire
        int i = 0;
        int numRoute = 0;
        while (scanner.hasNext()) {

            String ligne = scanner.nextLine();
            String[] tokens = ligne.split(";");
            if (ligne.compareTo("// Graphe planaire maximal;") != 0 && ligne.compareTo("// 10 sommets") != 0) {
                setSommet(i, new Sommet(tokens[0], tokens[1]));
                for (int j = 2; j < tokens.length; j++) {

                    if (tokens[j].compareTo("0") != 0) {

                        String[] triplet = tokens[j].split(",");
                        setRoute(i, j - 2, new Route("R" + numRoute, Double.parseDouble(triplet[0]), Integer.parseInt(triplet[1]), Integer.parseInt(triplet[2]), tabS[i], "S" + (j - 2)));
                        setDuree(tabR[i][j - 2], Integer.parseInt(triplet[2]));

                    } else {

                    }
                    numRoute++;

                }

                i++;

            }
        }
        scanner.close();

    }

    public void afficheContenuGraphe() {
        String ligne = "";
        for (int i = 0; i < this.nbSommet; i++) {
            for (int j = 0; j < this.nbSommet; j++) {
                if (this.tabR[i][j] != null) {
                    ligne = "Nom : " + this.tabR[i][j].getName() + " sa fiabilite est de :" + this.tabR[i][j].getFiabilité() + " sa distance du sommet " + this.tabS[i].getNom() + " au sommet :" + this.tabS[j].getNom() + " est de : " + this.tabR[i][j].getDistance() + " la duree du trajet est de : " + this.tabR[i][j].getDurée() + "\n";
                    System.out.println(ligne);
                }
            }
        }

    }

    public void genererGrapheAleatoire(int nbSommets) {
        this.nbSommet = nbSommets;
        this.tabS = new Sommet[nbSommet];
        this.tabR = new Route[nbSommet][nbSommet];
        this.durees = new HashMap<>();

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

            setSommet(i, new Sommet("S" + (i + 1), type));
        }
        // 2. Création des arêtes avec probabilité 1/5
        for (int i = 0; i < nbSommets; i++) {
            for (int j = i + 1; j < nbSommets; j++) {
                if (random.nextInt(5) == 0) {
                    double fiabilite = (random.nextInt(9) + 2) / 10.0;
                    int distance = random.nextInt(41) + 10;
                    int duree = random.nextInt(111) + 10;

                    setRoute(i, j, new Route("R" + numRoute, fiabilite, distance, duree, this.getSommet(i), "S" + i + 1));
                    setDuree(tabR[i][j], duree);
                    numRoute++;
                }
            }
        }
    }

}
