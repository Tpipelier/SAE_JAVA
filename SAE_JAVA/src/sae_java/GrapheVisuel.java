package sae_java;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

public class GrapheVisuel extends SingleGraph {

    public GrapheVisuel(String id, Graphe graphe) {
        super(id);
        construireGraphe(graphe);
    }

    private void construireGraphe(Graphe graphe) {
        for (int i = 0; i < graphe.getNbSommets(); i++) {
            Sommet s = graphe.getSommet(i);
            if (s != null) {
                Node n = addNode(s.getNom());
                n.setAttribute("ui.label", s.getNom() + " (" + s.getType() + ")");
            }
        }

        for (int i = 0; i < graphe.getNbSommets(); i++) {
            for (int j = i + 1; j < graphe.getNbSommets(); j++) {
                Route r = graphe.getRoute(i, j);
                if (r != null) {
                    String idArete = graphe.getSommet(i).getNom() + "-" + graphe.getSommet(j).getNom();
                    Edge e = addEdge(idArete, graphe.getSommet(i).getNom(), graphe.getSommet(j).getNom());
                    e.setAttribute("ui.label", r.getDistance() + " km");
                }
            }
        }
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
                }
            }
        }
    }
    
    public void afficher() {
        display();
    }
}
