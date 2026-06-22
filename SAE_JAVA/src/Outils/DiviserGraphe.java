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
 * Outil de partitionnement d'un graphe en deux zones de livraison (une par
 * camion). La repartition cherche a equilibrer les deux groupes tout en
 * minimisant les durees de trajet, a partir des plus courts chemins calcules par
 * Dijkstra.
 *
 * @author Theo Pipelier
 */
public class DiviserGraphe {

    /**
     * Aligne les sommets du graphe global dans deux listes distinctes (secteurs).
     *
     * @param grapheGlobal graphe à diviser en 2
     * @return Un tableau de deux Listes de Sommets [SecteurCamion1, SecteurCamion2]
     */
    public static List<Sommet>[] diviserEnDeuxSecteurs(Graphe grapheGlobal) {
        int totalSommets = grapheGlobal.getNbSommet();
        Sommet depot = grapheGlobal.getSommet(0); // Le Centre 1

        // 1. Recherche des 2 meilleurs sommets piliers (Logique inchangée de Théo)
        Sommet meilleurA = grapheGlobal.getSommet(1);
        Sommet meilleurB = (totalSommets > 2) ? grapheGlobal.getSommet(2) : meilleurA;
        double meilleurCoutGlobal = Double.MAX_VALUE;

        for (int i = 1; i < totalSommets; i++) {
            for (int j = i + 1; j < totalSommets; j++) {
                Sommet pilierA = grapheGlobal.getSommet(i);
                Sommet pilierB = grapheGlobal.getSommet(j);

                List<DistanceVers> cheminsDepuisA = grapheGlobal.distancesCroissantesVersTous(pilierA, "Duree");
                List<DistanceVers> cheminsDepuisB = grapheGlobal.distancesCroissantesVersTous(pilierB, "Duree");

                double coutConfiguration = 0;
                int compteA = 1;
                int compteB = 1;

                for (int k = 1; k < totalSommets; k++) {
                    if (k != i && k != j) {
                        Sommet s = grapheGlobal.getSommet(k);

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
                double penaliteEquilibre = ecart * 100.0;

                double coutTotalPondere = coutConfiguration + penaliteEquilibre;

                if (coutTotalPondere < meilleurCoutGlobal) {
                    meilleurCoutGlobal = coutTotalPondere;
                    meilleurA = pilierA;
                    meilleurB = pilierB;
                }
            }
        }

        List<Sommet> secteurA = new ArrayList<>();
        List<Sommet> secteurB = new ArrayList<>();

        // On insère le dépôt et le pilier dans chaque secteur
        secteurA.add(depot);
        secteurB.add(depot);
        secteurA.add(meilleurA);
        secteurB.add(meilleurB);

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
                    secteurA.add(s);
                } else {
                    secteurB.add(s);
                }
            }
        }

        // On retourne directement le tableau contenant les deux listes de sommets propres
        return new List[]{secteurA, secteurB};
    }
}
