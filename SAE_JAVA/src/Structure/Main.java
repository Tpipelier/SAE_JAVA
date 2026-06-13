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
        // 1. On construit un graphe aleatoire connexe (sommets de type M, O ou N)
        Graphe graphe = new Graphe();
        graphe.genererGrapheAleatoire(50);

        // 2. On affiche toutes les routes pour voir les distances du graphe
        System.out.println("===== Contenu du graphe =====");
        graphe.afficheContenuGraphe();

        // 3. On choisit un sommet de depart
        Sommet depart = graphe.getSommet(0);
        System.out.println("===== Distances depuis " + depart.getNom() + " (" + depart.getType() + ") =====");

        // 4. On appelle la methode a tester
        List<Graphe.DistanceVers> distances = graphe.distancesCroissantesVersTous(depart);

        // 5. On affiche le resultat (nom, type et distance, par ordre croissant)
        for (int i = 0; i < distances.size(); i++) {
            Graphe.DistanceVers d = distances.get(i);
            System.out.println((i + 1) + ") " + d.getNom() + " (" + d.getType() + ") -> distance en km = " + d.getDistance());
        }

        // 6. Test des plus courts chemins via la classe Recherche (GraphStream)
        //    On construit le graphe GraphStream (qui pose les poids duree et dureeEstimee)
        GrapheVisuel grapheVisuel = new GrapheVisuel("g", graphe.getTabS(), graphe.getTabR());
        Recherche recherche = new Recherche(grapheVisuel);

        // On choisit un depart et une arrivee (ici S1 et le dernier sommet)
        String nomDepart = graphe.getSommet(0).getNom();
        String nomArrivee = graphe.getSommet(graphe.getNbSommet() - 1).getNom();

        System.out.println("\n===== Plus courts chemins de " + nomDepart + " a " + nomArrivee + " =====");

        Recherche.Itineraire parDuree = recherche.plusCourtCheminDuree(nomDepart, nomArrivee);
        Recherche.Itineraire parDureeEstimee = recherche.plusCourtCheminDureeEstimee(nomDepart, nomArrivee);

        if (parDuree != null) {
            System.out.println("En duree standard      : " + parDuree);
        } else {
            System.out.println("En duree standard      : aucun chemin");
        }

        if (parDureeEstimee != null) {
            System.out.println("En duree estimee        : " + parDureeEstimee);
        } else {
            System.out.println("En duree estimee        : aucun chemin");
        }
    }
}
