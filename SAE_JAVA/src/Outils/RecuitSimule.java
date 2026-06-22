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
 * Algorithme de Recuit Simule Hybride Multi-Seed pour le defi algo sur la
 * tournee des camions. A partir d'une solution gloutonne de depart, il explore
 * le voisinage par des mouvements <em>2-opt</em> (inversion d'un segment de la
 * tournee) en acceptant temporairement des solutions moins bonnes (selon une
 * temperature decroissante) afin d'echapper aux minima locaux. La recherche est
 * relancee avec plusieurs graines pour retenir le meilleur resultat.
 *
 * <p>A chaque palier de temperature, le nombre de mouvements essayes est
 * proportionnel au nombre de centres (chaine de Markov) afin que l'effort
 * d'exploration s'adapte a la taille du graphe. Le cout d'un mouvement 2-opt est
 * evalue de maniere incrementale en O(1), ce qui rend cet effort abordable meme
 * sur de grands graphes (~150 sommets).</p>
 *
 * @author Walid Ferchach
 */
public class RecuitSimule {

    /**
     * Calcule la tournee optimale en s'appuyant sur une matrice de distances
     * pre-calculee.
     *
     * @param graphe          le graphe complet de reference
     * @param matriceDijkstra le tableau {@code [n][n]} des plus courts chemins
     *                        (durees) pre-calcules
     * @param sommetsAVisiter la liste restreinte de sommets (secteur d'un
     *                        camion), ou {@code null} pour filtrer tout le
     *                        graphe
     * @param typeFiltre      le filtre demande ({@code "M"}, {@code "O"},
     *                        {@code "N"} ou {@code "Tous"})
     * @return l'itineraire optimise
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

            // 2. GENERATION DE LA BASE GLOUTONNE (randomisee pour diversifier les graines)
            Random random = new Random(tentative);
            List<Sommet> solutionActuelle = new ArrayList<>();
            List<Sommet> resteAVisiter = new ArrayList<>(cibles);
            Sommet pivot = depart;

            while (!resteAVisiter.isEmpty()) {
                // On repere les deux centres non visites les plus proches du pivot
                Sommet plusProche = null;
                int minDuree = Integer.MAX_VALUE;
                Sommet deuxiemePlusProche = null;
                int deuxiemeDuree = Integer.MAX_VALUE;

                for (Sommet s : resteAVisiter) {
                    int d = matriceDijkstra[pivot.getIndex()][s.getIndex()];
                    if (d < minDuree) {
                        deuxiemeDuree = minDuree;
                        deuxiemePlusProche = plusProche;
                        minDuree = d;
                        plusProche = s;
                    } else if (d < deuxiemeDuree) {
                        deuxiemeDuree = d;
                        deuxiemePlusProche = s;
                    }
                }

                // La graine 0 reste un glouton pur (meilleure base connue) ; les
                // autres prennent parfois le 2e plus proche pour partir d'un point
                // de depart different et mieux exploiter les 15 essais.
                Sommet choisi = plusProche;
                if (tentative > 0 && deuxiemePlusProche != null && random.nextDouble() < 0.3) {
                    choisi = deuxiemePlusProche;
                }

                solutionActuelle.add(choisi);
                resteAVisiter.remove(choisi);
                pivot = choisi;
            }

            // 3. METAHEURISTIQUE : RECUIT SIMULE
            List<Sommet> meilleureSolution = new ArrayList<>(solutionActuelle);
            double meilleurCout = calculerCoutCheminOuvert(depart, meilleureSolution, matriceDijkstra);
            // coutActuel suit le cout de solutionActuelle ; il est maintenu de
            // maniere incrementale (on lui ajoute le delta de chaque mouvement
            // accepte au lieu de tout recalculer).
            double coutActuel = meilleurCout;

            double t = 300.0;
            double tMin = 0.001;
            double alpha = 0.998;
            // Longueur de la chaine de Markov : proportionnelle au nombre de
            // centres, pour que l'effort d'exploration s'adapte a la taille du
            // probleme (sinon, sur un grand graphe, le recuit ne fait rien et
            // renvoie tel quel la solution gloutonne de depart).
            int longueurPalier = Math.max(1, solutionActuelle.size());

            while (t > tMin) {
                for (int pas = 0; pas < longueurPalier; pas++) {
                    if (solutionActuelle.size() < 2) {
                        break;
                    }
                    // Voisinage 2-opt : on tire un segment [i, j] a inverser.
                    int i = random.nextInt(solutionActuelle.size());
                    int j = random.nextInt(solutionActuelle.size());
                    while (i == j) {
                        j = random.nextInt(solutionActuelle.size());
                    }
                    if (i > j) {
                        int tmpIdx = i;
                        i = j;
                        j = tmpIdx;
                    }

                    // Variation de cout de l'inversion, calculee en O(1) :
                    // seules les deux aretes aux extremites du segment changent.
                    double delta = deltaInversion(depart, solutionActuelle, i, j, matriceDijkstra);

                    // Critere de Metropolis : on accepte toute amelioration, et
                    // une degradation avec la probabilite exp(-delta / t).
                    if (delta < 0 || random.nextDouble() < Math.exp(-delta / t)) {
                        inverserSegment(solutionActuelle, i, j); // inversion en place
                        coutActuel += delta;
                        if (coutActuel < meilleurCout) {
                            meilleurCout = coutActuel;
                            meilleureSolution = new ArrayList<>(solutionActuelle);
                        }
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

    // Variation de cout d'une inversion du segment [i, j] de la tournee, en O(1).
    // La matrice des durees etant symetrique, les aretes internes du segment ont
    // la meme somme une fois inversees : seules changent l'arete entrant dans le
    // segment et celle qui en sort.
    private static double deltaInversion(Sommet depot, List<Sommet> ordre, int i, int j, int[][] matriceDijkstra) {
        int pred = (i == 0) ? depot.getIndex() : ordre.get(i - 1).getIndex();
        int debut = ordre.get(i).getIndex();
        int fin = ordre.get(j).getIndex();
        boolean aSuccesseur = j < ordre.size() - 1;
        int succ = aSuccesseur ? ordre.get(j + 1).getIndex() : -1;

        double avant = matriceDijkstra[pred][debut] + (aSuccesseur ? matriceDijkstra[fin][succ] : 0);
        double apres = matriceDijkstra[pred][fin] + (aSuccesseur ? matriceDijkstra[debut][succ] : 0);
        return apres - avant;
    }

    // Inverse en place le segment [i, j] de la liste.
    private static void inverserSegment(List<Sommet> ordre, int i, int j) {
        while (i < j) {
            Sommet tmp = ordre.get(i);
            ordre.set(i, ordre.get(j));
            ordre.set(j, tmp);
            i++;
            j--;
        }
    }
}
