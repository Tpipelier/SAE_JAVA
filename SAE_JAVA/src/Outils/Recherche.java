
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Outils;

import Structure.Itineraire;
import java.util.ArrayList;
import java.util.List;
import org.graphstream.algorithm.Dijkstra;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.Path;

/**
 * Moteur de recherche de plus courts chemins, fonde sur l'algorithme de Dijkstra
 * de la librairie GraphStream. Les poids des aretes ({@code "duree"} ou
 * {@code "dureeEstimee"}) sont poses par {@code GrapheVisuel}.
 *
 * @author walid
 */
public class Recherche {

    private final Graph graphe;

    /**
     * Construit le moteur de recherche pour un graphe GraphStream donne.
     *
     * @param graphe le graphe GraphStream sur lequel effectuer les recherches
     */
    public Recherche(Graph graphe) {
        this.graphe = graphe;
    }

    /**
     * Calcule le plus court chemin en duree standard (minutes).
     *
     * @param depart  nom du centre de depart
     * @param arrivee nom du centre d'arrivee
     * @return l'itineraire le plus court, ou {@code null} si l'arrivee est
     *         inatteignable
     * @throws IllegalArgumentException si un des centres est inconnu
     */
    public Itineraire plusCourtCheminDuree(String depart, String arrivee) {
        return chercher("duree", depart, arrivee);
    }

    /**
     * Calcule le plus court chemin en duree estimee (ponderee par la fiabilite).
     *
     * @param depart  nom du centre de depart
     * @param arrivee nom du centre d'arrivee
     * @return l'itineraire le plus court, ou {@code null} si l'arrivee est
     *         inatteignable
     * @throws IllegalArgumentException si un des centres est inconnu
     */
    public Itineraire plusCourtCheminDureeEstimee(String depart, String arrivee) {
        return chercher("dureeEstimee", depart, arrivee);
    }

    /**
     * Lance Dijkstra (librairie GraphStream) sur l'attribut de poids donné
     * ("duree" ou "dureeEstimee", posés sur les arêtes par GrapheVisuel).
     *
     * @return l'itinéraire trouvé, ou null si l'arrivée est inatteignable
     */
    private Itineraire chercher(String poids, String depart, String arrivee) {
        Node source = graphe.getNode(depart);
        Node cible = graphe.getNode(arrivee);
        if (source == null || cible == null) {
            throw new IllegalArgumentException("Centre inconnu : " + depart + " ou " + arrivee);
        }

        Dijkstra dijkstra = new Dijkstra(Dijkstra.Element.EDGE, null, poids);
        dijkstra.init(graphe);
        dijkstra.setSource(source);
        dijkstra.compute();

        Path chemin = dijkstra.getPath(cible);
        double cout = dijkstra.getPathLength(cible);
        dijkstra.clear();

        if (Double.isInfinite(cout)) {   // pas de chemin entre les deux centres
            return null;
        }

        ArrayList<String> sommets = new ArrayList<>();
        ArrayList<String> types = new ArrayList<>();
        for (Node n : chemin.getNodePath()) {
            sommets.add(n.getId());
            types.add(n.getAttribute("type"));
        }
        return new Itineraire(sommets, types, cout);
    }
}

