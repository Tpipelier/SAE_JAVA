
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Outils;

import java.util.ArrayList;
import java.util.List;
import org.graphstream.algorithm.Dijkstra;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.Path;

/**
 *
 * @author walid
 */
public class Recherche {

    private final Graph graphe;

    public Recherche(Graph graphe) {
        this.graphe = graphe;
    }

    /** Plus court chemin en durée standard (minutes). */
    public Itineraire plusCourtCheminDuree(String depart, String arrivee) {
        return chercher("duree", depart, arrivee);
    }

    /** Plus court chemin en durée estimée (pondérée par la fiabilité). */
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
        for (Node n : chemin.getNodePath()) {
            sommets.add(n.getId());
        }
        return new Itineraire(sommets, cout);
    }

    /** Résultat d'une recherche : les centres traversés et le coût total. */
    public static class Itineraire {

        private final ArrayList<String> sommets;
        private final double cout;

        public Itineraire(ArrayList<String> sommets, double cout) {
            this.sommets = sommets;
            this.cout = cout;
        }

        public ArrayList<String> getSommets() {
            return sommets;
        }

        public double getCout() {
            return cout;
        }

        @Override
        public String toString() {
            return sommets + " (" + Math.round(cout) + " min)";
        }
    }
}

