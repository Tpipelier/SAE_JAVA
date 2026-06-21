/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Outils;

import Structure.Graphe;
import Structure.Sommet;
import Structure.Itineraire;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Algorithme de Recuit Simulé Hybride Multi-Seed pour le défi algo sur la
 * tournée des camions.
 */
public class RecuitSimule {

    /**
     * Calcule la tournée optimale en s'appuyant sur une matrice de distances
     * pré-calculée.
     *
     * * @param graphe Le graphe complet de référence
     * @param matriceDijkstra Le tableau des plus courts chemins pré-calculés
     * [n][n]
     * @param sommetsAVisiter La liste restreinte de sommets (Secteur Camion),
     * ou null pour filtrer tout le graphe
     * @param typeFiltre Le filtre demandé ("M", "O", "N" ou "Tous")
     * @return L'itinéraire optimisé au maximum
     */
    public Itineraire calculerTourneeOptimale(Graphe graphe, int[][] matriceDijkstra, List<Sommet> sommetsAVisiter, String typeFiltre) {

        Itineraire meilleurItineraireGlobal = null;
        double scoreMinimalAbsolu = Double.MAX_VALUE;
        Sommet depart = graphe.getSommet(0);

        // 1. Détermination de la liste des cibles à visiter
        List<Sommet> cibles = new ArrayList<>();
        if (sommetsAVisiter == null) {
            // Cas 1 Camion : on filtre l'ensemble du graphe global
            for (Sommet s : graphe.getTabS()) {
                if (s.getIndex() != depart.getIndex()) {
                    if (typeFiltre.equalsIgnoreCase("Tous") || s.getType().equalsIgnoreCase(typeFiltre)) {
                        cibles.add(s);
                    }
                }
            }
        } else {
            // Cas 2 Camions : on utilise la liste fournie par le découpage (en excluant le dépôt)
            for (Sommet s : sommetsAVisiter) {
                if (s.getIndex() != depart.getIndex()) {
                    cibles.add(s);
                }
            }
        }

        // Cas particulier : aucun sommet à visiter
        if (cibles.isEmpty()) {
            ArrayList<String> ids = new ArrayList<>();
            ArrayList<String> types = new ArrayList<>();
            if (typeFiltre.equalsIgnoreCase("Tous") || depart.getType().equalsIgnoreCase(typeFiltre)) {
                ids.add(depart.getNom());
                types.add(depart.getType());
            }
            return new Itineraire(ids, types, 0.0);
        }

        // On lance la recherche 15 fois avec 15 graines différentes
        for (int tentative = 0; tentative < 15; tentative++) {

            // 2. GENERATION DE LA BASE GLOUTONNE
            List<Sommet> solutionActuelle = new ArrayList<>();
            List<Sommet> resteAVisiter = new ArrayList<>(cibles);
            Sommet pivot = depart;

            while (!resteAVisiter.isEmpty()) {
                Sommet plusProche = null;
                int minDuree = Integer.MAX_VALUE;

                for (Sommet s : resteAVisiter) {
                    int d = matriceDijkstra[pivot.getIndex()][s.getIndex()];
                    if (d < minDuree) {
                        minDuree = d;
                        plusProche = s;
                    }
                }

                solutionActuelle.add(plusProche);
                resteAVisiter.remove(plusProche);
                pivot = plusProche;
            }

            // 3. METAHEURISTIQUE : RECUIT SIMULE
            List<Sommet> meilleureSolution = new ArrayList<>(solutionActuelle);
            double meilleurCout = calculerCoutCheminOuvert(depart, meilleureSolution, matriceDijkstra);

            double t = 300.0;
            double tMin = 0.001;
            double alpha = 0.998;

            Random random = new Random(tentative);

            while (t > tMin) {
                List<Sommet> voisin = new ArrayList<>(solutionActuelle);
                if (voisin.size() > 1) {
                    int idx1 = random.nextInt(voisin.size());
                    int idx2 = random.nextInt(voisin.size());
                    while (idx1 == idx2) {
                        idx2 = random.nextInt(voisin.size());
                    }
                    Sommet tmp = voisin.get(idx1);
                    voisin.set(idx1, voisin.get(idx2));
                    voisin.set(idx2, tmp);
                }

                double coutActuel = calculerCoutCheminOuvert(depart, solutionActuelle, matriceDijkstra);
                double coutVoisin = calculerCoutCheminOuvert(depart, voisin, matriceDijkstra);

                if (coutVoisin < coutActuel) {
                    solutionActuelle = voisin;
                    if (coutVoisin < meilleurCout) {
                        meilleurCout = coutVoisin;
                        meilleureSolution = new ArrayList<>(voisin);
                    }
                } else {
                    double proba = Math.exp((coutActuel - coutVoisin) / t);
                    if (random.nextDouble() < proba) {
                        solutionActuelle = voisin;
                    }
                }

                t *= alpha;
            }

            // 4. PACKAGING TEMPORAIRE POUR CETTE SEED
            ArrayList<String> idSommetsRes = new ArrayList<>();
            ArrayList<String> typeSommetsRes = new ArrayList<>();

            if (typeFiltre.equalsIgnoreCase("Tous") || depart.getType().equalsIgnoreCase(typeFiltre)) {
                idSommetsRes.add(depart.getNom());
                typeSommetsRes.add(depart.getType());
            }

            for (Sommet s : meilleureSolution) {
                idSommetsRes.add(s.getNom());
                typeSommetsRes.add(s.getType());
            }

            Itineraire itineraireTentative = new Itineraire(idSommetsRes, typeSommetsRes, meilleurCout);

            // 5. COMPARAISON
            if (itineraireTentative.getCout() < scoreMinimalAbsolu) {
                scoreMinimalAbsolu = itineraireTentative.getCout();
                meilleurItineraireGlobal = itineraireTentative;
            }
        }

        return meilleurItineraireGlobal;
    }

    /**
     * Calcule le coût en temps en chaîne ouverte.
     */
    private static double calculerCoutCheminOuvert(Sommet depot, List<Sommet> ordreVisites, int[][] matriceDijkstra) {
        double dureeTotale = 0;
        int idxActuel = depot.getIndex();

        for (Sommet suivant : ordreVisites) {
            dureeTotale += matriceDijkstra[idxActuel][suivant.getIndex()];
            idxActuel = suivant.getIndex();
        }
        return dureeTotale;
    }
}
