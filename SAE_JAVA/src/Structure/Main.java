/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Structure;

import java.io.FileNotFoundException;
import java.util.List;
import IHM.GrapheVisuel;

/**
 * Classe principale : teste la methode distancesCroissantesVersTous.
 *
 * @author walid
 */
public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        // 1. Graphe aléatoire connexe
        Graphe graphe = new Graphe();
        graphe.genererGrapheAleatoire(10);

        // 2. Graphe GraphStream + Recherche (Dijkstra)
        GrapheVisuel grapheVisuel = new GrapheVisuel("g", graphe.getTabS(), graphe.getTabR());
        Recherche recherche = new Recherche(grapheVisuel);
        grapheVisuel.afficher();

        // 3. Algorithme glouton à partir du sommet 0
        Sommet depart = graphe.getSommet(0);
        glouton g = new glouton(recherche);
        List<Sommet> parcours = g.unCamion(graphe.getTabS(), depart);

        // 4. Affichage du parcours
        System.out.println("Depart : " + depart.getNom());
        System.out.print("Parcours glouton : ");
        for (int i = 0; i < parcours.size(); i++) {
            System.out.print(parcours.get(i).getNom());
            if (i < parcours.size() - 1) {
                System.out.print(" -> ");
            }
        }
        System.out.println();
        System.out.println("Nombre de sommets visites : " + parcours.size());
        }
}
