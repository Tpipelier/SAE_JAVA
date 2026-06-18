/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Outils;

import Structure.Graphe;
import Structure.Route;
import Structure.Sommet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author theop
 */
public class AlgorithmePrim {

    private Graphe graphe;

    public AlgorithmePrim(Graphe graphe) {
        this.graphe = graphe;
    }

    /**
     * Méthode qui calcule l'Arbre Couvrant Minimum (ACM) d'un graphe donné.
     *
     * @param graphe Le graphe d'origine
     * @return Une liste de routes qui forment l'arbre couvrant minimum
     */
    public List<Route> determinerACM() {
        List<Route> acm = new ArrayList<>();
        if (graphe == null || graphe.getTabS() == null || graphe.getNbSommet() == 0) {
            return acm;
        }
        int n = graphe.getNbSommet();

        boolean[] visites = new boolean[n];

        // On commence par le premier sommet
        visites[0] = true;

        // Drapeau pour continuer tant qu'on trouve des routes et qu'on n'a pas fini
        boolean routeTrouvee = true;
        int etape = 0;

        while (etape < n - 1 && routeTrouvee) {
            Route routeMin = null;
            int prochainSommetId = -1;
            double coutMin = Double.MAX_VALUE;

            // On parcourt tous les sommets existants
            for (int i = 0; i < n; i++) {
                if (visites[i]) {

                    // On cherche ses voisins dans la matrice tabR
                    for (int j = 0; j < n; j++) {
                        if (!visites[j] && graphe.getRoute(i, j) != null) {
                            Route routeActuelle = graphe.getRoute(i, j);
                            double coutActuel = routeActuelle.getDuree();

                            if (coutActuel < coutMin) {
                                coutMin = coutActuel;
                                routeMin = routeActuelle;
                                prochainSommetId = j;
                            }
                        }
                    }
                }
            }

            // Si une route minimale a été trouvée, on met à jour nos variables
            if (routeMin != null) {
                acm.add(routeMin);
                visites[prochainSommetId] = true;
                etape++;
            } else {
                // Si aucune route n'est trouvée, on change le drapeau pour arrêter la boucle while
                routeTrouvee = false;
            }
        }
        
        return acm;
    }
    
}
