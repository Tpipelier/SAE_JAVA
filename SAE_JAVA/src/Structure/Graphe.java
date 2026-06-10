/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Structure;

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
    /**
     * 
     * @throws FileNotFoundException 
     */
    public Graphe() throws FileNotFoundException {
        this.nbSommet = 0;
        this.tabS = null;
        this.tabR = null;
        this.durees = null;

    }
    /**
     * 
     * @param nomFichier
     * @return cpt
     * @throws FileNotFoundException 
     */
    public static int compteSommet(String nomFichier) throws FileNotFoundException {
        int cpt = 0;

        // Le fichier d'entrée
        FileInputStream file = new FileInputStream(nomFichier);
        Scanner scanner = new Scanner(file);

        //renvoie true tant qu'il y a une autre ligne à lire
        while (scanner.hasNext()) {
            String ligne = scanner.nextLine();
            // On ne compte que les lignes de données utiles (pas les commentaires)
            if (!ligne.isEmpty() && !ligne.startsWith("//")) {
                cpt++;
            }
        }
        scanner.close();

        return cpt;

    }
    /**
     * 
     * @return int
     */
    public int getNbSommet() {
        return nbSommet;
    }
    /**
     * 
     * @param numeroSommet
     * @return Sommet
     */
    public Sommet getSommet(int numeroSommet) {
        return tabS[numeroSommet];
    }
    /**
     * 
     * @param numeroSommetD
     * @param numeroSommetA
     * @return Route
     */
    public Route getRoute(int numeroSommetD, int numeroSommetA) {
        return tabR[numeroSommetD][numeroSommetA];
    }
    /**
     * 
     * @return Sommet[]
     */
    public Sommet[] getTabS() {
        return tabS;
    }
    /**
     * 
     * @return Route[][]
     */
    public Route[][] getTabR() {
        return tabR;
    }
    /**
     * 
     * @return HashMap
     */
    public HashMap<Route, Integer> getDurees() {
        return this.durees;
    }
    /**
     * 
     * @param numeroSommet
     * @param Sommet 
     */
    public void ajouterSommet(int numeroSommet, Sommet Sommet) {
        this.tabS[numeroSommet] = Sommet;
    }
    /**
     * 
     * @param numeroSommetD
     * @param numeroSommetA
     * @param Route 
     */
    public void ajouterRoute(int numeroSommetD, int numeroSommetA, Route Route) {
        this.tabR[numeroSommetD][numeroSommetA] = Route;
    }
    /**
     * 
     * @param R
     * @param duree 
     */
    public void ajouterDuree(Route R, int duree) {
        this.durees.put(R, duree);
    }
    /**
     * 
     * @param nomFichier
     * @throws FileNotFoundException 
     */
    public void chargerGraphes(String nomFichier) throws FileNotFoundException {
        this.nbSommet = compteSommet(nomFichier);
        this.tabS = new Sommet[nbSommet];
        this.tabR = new Route[nbSommet][nbSommet];
        this.durees = new HashMap<>();

        // Le fichier d'entrée
        FileInputStream fichier = new FileInputStream(nomFichier);
        Scanner scannerTabS = new Scanner(fichier);

        int i = 0;
        while (scannerTabS.hasNext()) {

            String ligne = scannerTabS.nextLine();
            try {
                if (!ligne.startsWith("//")) {
                    String[] tokens = ligne.split(";");                
                    ajouterSommet(i, new Sommet(tokens[0].trim(), tokens[1].trim()));

                    i++;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                System.err.println("Erreur : La ligne du fichier CSV ne contient pas assez d'éléments pour créer le sommet à l'index " + i);
            }
        }
        scannerTabS.close();
        FileInputStream fileTabR = new FileInputStream(nomFichier);
        Scanner scannerTabR = new Scanner(fileTabR);
        i = 0;
        int numRoute = 0;
        while (scannerTabR.hasNext()) {

            String ligne = scannerTabR.nextLine();

            if (!ligne.startsWith("//")) {

                String[] tokens = ligne.split(";");

                for (int j = 2; j < tokens.length; j++) {
                    if (tokens[j].compareTo("0") != 0 && tokens[j].compareTo("")!=0){
                        try {
                            String[] triplet = tokens[j].trim().split(",");
                            ajouterRoute(i, j - 2, new Route("R" + numRoute, Double.parseDouble(triplet[0].trim()), Integer.parseInt(triplet[1].trim()), Integer.parseInt(triplet[2].trim()), tabS[i], tabS[j - 2]));
                            ajouterDuree(tabR[i][j - 2], Integer.parseInt(triplet[2].trim()));

                        } catch (NumberFormatException e) {
                            System.out.println("Route corrompue sur la ligne suivante : " + ligne + " (Raison : " + e.getMessage() + ")");
                        }

                    }
                        
                    numRoute++;

                }

                i++;

            }
        }
        scannerTabR.close();

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

            ajouterSommet(i, new Sommet("S" + (i + 1), type));
        }
        // 2. Création des arêtes avec probabilité 1/5
        for (int i = 0; i < nbSommets; i++) {
            for (int j = i + 1; j < nbSommets; j++) {
                if (random.nextInt(5) == 0) {
                    double fiabilite = (random.nextInt(9) + 2) / 10.0;
                    int distance = random.nextInt(41) + 10;
                    int duree = random.nextInt(111) + 10;
                    ajouterRoute(i, j, new Route("R" + numRoute, fiabilite, distance, duree, this.getSommet(i), this.getSommet(i + 1)));
                    ajouterDuree(tabR[i][j], duree);
                    numRoute++;
                }
            }
        }
    }
}
