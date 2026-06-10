/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package sae_java;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author Theo Pipelier
 */
public class SAE_JAVA {

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException {
        Graphe g = new Graphe();
        g.genererGrapheAleatoire(20);

        GrapheVisuel grapheVisuel = new GrapheVisuel("graphe1", g.getTabS(), g.getTabR());

        // Recherche de plus court chemin via le Dijkstra de la librairie GraphStream.
        Recherche recherche = new Recherche(grapheVisuel);
        String depart = "S1";
        String arrivee = "S7";

        Recherche.Itineraire parDuree = recherche.plusCourtCheminDuree(depart, arrivee);
        Recherche.Itineraire parDureeEstimee = recherche.plusCourtCheminDureeEstimee(depart, arrivee);

        System.out.println("Plus court chemin de " + depart + " a " + arrivee + " :");
        System.out.println("  - duree standard : " + parDuree);
        System.out.println("  - duree estimee  : " + parDureeEstimee);

        grapheVisuel.afficher();
    }
    

}
