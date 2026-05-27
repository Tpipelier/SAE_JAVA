/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sae_java;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author Theo Pipelier
 */
public class Graphe {

    int nbSommet;
    int nbRoute;
    Sommet[] tabS;
    Route[][] tabR;
    int[][] tabD;

    public Graphe(String nomFichier) throws FileNotFoundException {
        this.nbSommet = compteSommet(nomFichier);
        this.nbRoute = this.nbSommet * this.nbSommet;
        this.tabS = new Sommet[nbSommet];
        this.tabR = new Route[nbSommet][nbSommet];
        this.tabD = new int[nbSommet][nbSommet];
        chargerGraphes(nomFichier, tabS, tabR, tabD);
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

    public static void chargerGraphes(String file, Sommet[] tabS, Route[][] tabR, int[][] tabD) throws FileNotFoundException {

        // Le fichier d'entrée
        FileInputStream fichier = new FileInputStream(file);
        Scanner scanner = new Scanner(fichier);

        //renvoie true tant qu'il y a une autre ligne à lire
        int i = 0;
        int k;
        int numRoute = 0;
        while (scanner.hasNext()) {

            String ligne = scanner.nextLine();
            String[] tokens = ligne.split(";");
            if (ligne.compareTo("// Graphe planaire maximal;") != 0 && ligne.compareTo("// 10 sommets") != 0) {
                tabS[i] = new Sommet(tokens[0], tokens[1]);
                k = 0;
                for (int j = 2; j < tokens.length; j++) {

                    if (tokens[j].compareTo("0") != 0) {

                        String[] triplet = tokens[j].split(",");
                        tabR[i][k] = new Route("R" + numRoute, Double.parseDouble(triplet[0]), Integer.parseInt(triplet[1]), Integer.parseInt(triplet[2]), tabS[i], "S" + k);
                        tabD[i][k] = Integer.parseInt(triplet[2]);

                    } else {

                    }
                    k++;
                    numRoute++;

                }

                i++;

            }
        }
        scanner.close();

    }

    public int getNbSommet() {
        return nbSommet;
    }

    public int getNbRoute() {
        return nbRoute;
    }

    public Sommet[] getTabS() {
        return tabS;
    }

    public Route[][] getTabR() {
        return tabR;
    }

    public int[][] getTabD() {
        return tabD;
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

}
