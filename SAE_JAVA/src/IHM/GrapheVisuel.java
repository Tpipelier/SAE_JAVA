package IHM;

import Structure.Sommet;
import Structure.Route;
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
                
                if ("M".equals(s.getType())) {
                    n.setAttribute("ui.class", "maternite");
                } else if ("O".equals(s.getType())) {
                    n.setAttribute("ui.class", "blocOperatoire");
                } else if ("N".equals(s.getType())) {
                    n.setAttribute("ui.class", "nutrition");
                }
                
            }
        }

        for (int i = 0; i < tabR.length; i++) {
            for (int j = 0; j < tabR[i].length; j++) {
                Route r = tabR[i][j];
                if (r != null && i < j) {
                    String idArete = tabS[i].getNom() + "-" + tabS[j].getNom();
                    Edge e = addEdge(idArete, tabS[i].getNom(), tabS[j].getNom());
                    e.setAttribute("ui.label", r.getDistance() + "km");
                    
                    if (r.getFiabilité() < 0.3) {
                    e.setAttribute("ui.class", "danger");
                    }
                }
            }
        }
    }

    public void afficher() {
        display();
    }
}
