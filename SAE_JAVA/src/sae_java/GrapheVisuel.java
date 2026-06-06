package sae_java;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

public class GrapheVisuel extends SingleGraph {

    public GrapheVisuel(String id, Sommet[] tabS, Route[][] tabR) {
        super(id);
        construireGraphe(tabS, tabR);
    }

    private void construireGraphe(Sommet[] tabS, Route[][] tabR) {
        for (Sommet s : tabS) {
            if (s != null) {
                Node n = addNode(s.getNom());
                n.setAttribute("ui.label", s.getNom() + " (" + s.getType() + ")");
            }
        }

        for (int i = 0; i < tabR.length; i++) {
            for (int j = 0; j < tabR[i].length; j++) {
                Route r = tabR[i][j];
                if (r != null && i < j) {
                    String idArete = tabS[i].getNom() + "-" + tabS[j].getNom();
                    Edge e = addEdge(idArete, tabS[i].getNom(), tabS[j].getNom());
                    e.setAttribute("ui.label", r.getDistance() + "km");
                    // Pour Dijkstra 
                    e.setAttribute("duree", r.getDurée());
                    e.setAttribute("dureeEstimee", r.getDuréeEstimée());
                }
            }
        }
    }

    public void afficher() {
        display();
    }
}
