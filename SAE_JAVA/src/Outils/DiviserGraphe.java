/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Outils;

import Structure.DistanceVers;
import Structure.Graphe;
import Structure.Route;
import Structure.Sommet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Theo Pipelier
 */
public class DiviserGraphe {

    public static Graphe[] diviserEnDeux(Graphe grapheGlobal) {
        int totalSommets = grapheGlobal.getNbSommet();
        Sommet depot = grapheGlobal.getSommet(0); // Le Centre 1

        // 1. Recherche des 2 meilleurs sommets 
        Sommet meilleurA = grapheGlobal.getSommet(1);
        Sommet meilleurB = (totalSommets > 2) ? grapheGlobal.getSommet(2) : meilleurA;
        double meilleurCoutGlobal = Double.MAX_VALUE;

        for (int i = 1; i < totalSommets; i++) {
            for (int j = i + 1; j < totalSommets; j++) {
                Sommet pilierA = grapheGlobal.getSommet(i);
                Sommet pilierB = grapheGlobal.getSommet(j);

                // On utilise Dijkstra pour calculer les vraies distances les plus courtes depuis les piliers
                List<DistanceVers> cheminsDepuisA = grapheGlobal.distancesCroissantesVersTous(pilierA, "Duree");
                List<DistanceVers> cheminsDepuisB = grapheGlobal.distancesCroissantesVersTous(pilierB, "Duree");

                double coutConfiguration = 0;
                int compteA = 1;
                int compteB = 1;

                for (int k = 1; k < totalSommets; k++) {
                    if (k != i && k != j) {
                        Sommet s = grapheGlobal.getSommet(k);

                        // Trouver la durée réelle via Dijkstra pour A
                        int dA = Integer.MAX_VALUE;
                        for (int idx = 0; idx < cheminsDepuisA.size(); idx++) {
                            if (cheminsDepuisA.get(idx).getSommet().equals(s)) {
                                dA = cheminsDepuisA.get(idx).getDuree();
                            }
                        }

                        int dB = Integer.MAX_VALUE;
                        for (int idx = 0; idx < cheminsDepuisB.size(); idx++) {
                            if (cheminsDepuisB.get(idx).getSommet().equals(s)) {
                                dB = cheminsDepuisB.get(idx).getDuree();
                            }
                        }

                        if (dA != Integer.MAX_VALUE || dB != Integer.MAX_VALUE) {
                            coutConfiguration += Math.min(dA, dB);
                            if (dA < dB) {
                                compteA++;
                            } else {
                                compteB++;
                            }
                        }
                    }
                }

                int ecart = Math.abs(compteA - compteB);
                double penaliteEquilibre = ecart * 100.0; // Pénalité renforcée pour forcer le groupe
                double coutTotalPondere = coutConfiguration + penaliteEquilibre;

                if (coutTotalPondere < meilleurCoutGlobal) {
                    meilleurCoutGlobal = coutTotalPondere;
                    meilleurA = pilierA;
                    meilleurB = pilierB;
                }
            }
        }

        // 2. Répartition finale basée sur le Dijkstra des deux piliers retenus
        List<Sommet> listeA = new ArrayList<>();
        List<Sommet> listeB = new ArrayList<>();

        listeA.add(depot);
        listeB.add(depot);
        listeA.add(meilleurA);
        listeB.add(meilleurB);

        List<DistanceVers> cheminsFinauxA = grapheGlobal.distancesCroissantesVersTous(meilleurA, "Duree");
        List<DistanceVers> cheminsFinauxB = grapheGlobal.distancesCroissantesVersTous(meilleurB, "Duree");

        for (int i = 1; i < totalSommets; i++) {
            Sommet s = grapheGlobal.getSommet(i);

            if (!s.equals(meilleurA) && !s.equals(meilleurB)) {
                int dA = Integer.MAX_VALUE;
                for (int idx = 0; idx < cheminsFinauxA.size(); idx++) {
                    if (cheminsFinauxA.get(idx).getSommet().equals(s)) {
                        dA = cheminsFinauxA.get(idx).getDuree();
                    }
                }

                int dB = Integer.MAX_VALUE;
                for (int idx = 0; idx < cheminsFinauxB.size(); idx++) {
                    if (cheminsFinauxB.get(idx).getSommet().equals(s)) {
                        dB = cheminsFinauxB.get(idx).getDuree();
                    }
                }

                if (dA < dB) {
                    listeA.add(s);
                } else {
                    listeB.add(s);
                }
            }
        }

        Graphe g1 = new Graphe(listeA.size());
        Graphe g2 = new Graphe(listeB.size());

        for (int i = 0; i < listeA.size(); i++) {
            g1.ajouterSommet(i, listeA.get(i));
        }
        for (int i = 0; i < listeB.size(); i++) {
            g2.ajouterSommet(i, listeB.get(i));
        }

        remplirMatriceRoutes(g1, grapheGlobal);
        remplirMatriceRoutes(g2, grapheGlobal);

        return new Graphe[]{g1, g2};
    }

    private static void remplirMatriceRoutes(Graphe sousGraphe, Graphe grapheGlobal) {
        int n = sousGraphe.getNbSommet();
        for (int i = 0; i < n; i++) {
            Sommet s1 = sousGraphe.getSommet(i);
            for (int j = 0; j < n; j++) {
                if (i != j) {
                    Sommet s2 = sousGraphe.getSommet(j);

                    Route r = grapheGlobal.getRoute(s1.getIndex(), s2.getIndex());
                    if (r == null) {
                        r = grapheGlobal.getRoute(s2.getIndex(), s1.getIndex());
                    }

                    if (r != null) {

                        sousGraphe.ajouterRoute(i, j, r);
                        sousGraphe.ajouterDuree(r, (int) r.getDuree());
                    }
                }
            }
        }
    }
}
