/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Test;

import IHM.FenetrePrincipale;
import IHM.GrapheVisuel;

import Outils.DiviserGraphe;
import Outils.RecuitSimule;
import Structure.DistanceVers;
import Structure.Graphe;
import Structure.Itineraire;
import Structure.Sommet;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;
import org.graphstream.graph.Graph;

/**
 *
 * @author Theo Pipelier
 */
public class Test {

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException {
        // TODO code application logic here
//        Graphe g=new Graphe();
//        g.genererGrapheAleatoire(20);
//        g.afficheContenuGraphe();
//        
//        
//        
//
//        GrapheVisuel grapheVisuel = new GrapheVisuel("graphe1", g.getTabS(), g.getTabR());
//        grapheVisuel.afficher();
           FenetrePrincipale f = new FenetrePrincipale();
//        Graphe g=new Graphe();
//        g.genererGrapheAleatoire(20);
//        GrapheVisuel grapheVisuel = new GrapheVisuel("Graphe charger", g.getTabS(), g.getTabR());
//        
//        AlgorithmeChristofide ac = new AlgorithmeChristofide(grapheVisuel,g);
//        List<String> lst = ac.executerChristofide();
//        System.out.println("\n"+lst);    

//        try {
//            System.out.println("=== TEST UNITAIRE DU DECOUPEUR ===");
//
//            // 1. On charge le graphe global depuis ton CSV
//            Graphe grapheGlobal = new Graphe();
//            grapheGlobal.chargerGraphes("data/graphe5.csv"); // Change le chemin si besoin
//
//            System.out.println("Nombre total de sommets dans le graphe global : " + grapheGlobal.getNbSommet());
//            System.out.println("--------------------------------------------------");
//
//            // 2. On lance le découpage
//            Graphe[] resultats = DiviserGraphe.diviserEnDeux(grapheGlobal);
//            Graphe zoneC1 = resultats[0];
//            Graphe zoneC2 = resultats[1];
//
//            // 3. On affiche les sommets du Camion 1
//            System.out.println("\n📦 SOMMETS AFFECTÉS AU CAMION 1 (" + zoneC1.getNbSommet() + " villes) :");
//            for (int i = 0; i < zoneC1.getNbSommet(); i++) {
//                Sommet s = zoneC1.getSommet(i);
//                System.out.println("  -> Case " + i + " : " + s.getNom() + " (Index d'origine: " + s.getIndex() + ")");
//            }
//
//            // 4. On affiche les sommets du Camion 2
//            System.out.println("\n📦 SOMMETS AFFECTÉS AU CAMION 2 (" + zoneC2.getNbSommet() + " villes) :");
//            for (int i = 0; i < zoneC2.getNbSommet(); i++) {
//                Sommet s = zoneC2.getSommet(i);
//                System.out.println("  -> Case " + i + " : " + s.getNom() + " (Index d'origine: " + s.getIndex() + ")");
//            }
//
//            System.out.println("\n--------------------------------------------------");
//            System.out.println("Vérification : Le dépôt (Centre 1) doit être à la Case 0 des deux camions.");
//            System.out.println("Aucun autre index d'origine ne doit être commun aux deux listes !");
//
//        } catch (FileNotFoundException e) {
//            System.err.println("Fichier de données introuvable ! Vérifie le chemin d'accès.");
//        } catch (Exception e) {
//            System.err.println("Erreur durant le test : " + e.getMessage());
//            e.printStackTrace();
//        }
// Chemin relatif vers ton fichier dans le projet
        // (Ajuste l'extension .csv ou .txt selon ton vrai fichier)
        // Chemin relatif vers ton fichier dans le projet
        String cheminFichier = "data/graphe1.csv";

        System.out.println("=== DEBUT DU TEST DEFI ALGO (1 CAMION) ===");
        System.out.println("Chargement du fichier : " + cheminFichier);

        try {
            // 1. Initialisation et chargement du graphe global
            Graphe monGraphe = new Graphe();
            monGraphe.chargerGraphes(cheminFichier);

            System.out.println("Graphe chargé avec succès !");
            System.out.println("Nombre de sommets détectés : " + monGraphe.getNbSommet());
            System.out.println("------------------------------------------------");

            // 2. Pré-calcul de la matrice Dijkstra complète (indispensable pour les calculs de l'algo)
            System.out.println("Calcul de la matrice des distances globales via Dijkstra...");
            int n = monGraphe.getNbSommet();
            int[][] maMatriceDijkstra = new int[n][n];
            for (int i = 0; i < n; i++) {
                List<DistanceVers> listDV = monGraphe.distancesCroissantesVersTous(monGraphe.getSommet(i), "Duree");
                for (DistanceVers dv : listDV) {
                    int idxDest = monGraphe.indexDeSommet(dv.getSommet());
                    if (idxDest != -1) {
                        maMatriceDijkstra[i][idxDest] = dv.getDuree();
                    }
                }
            }
            System.out.println("Matrice Dijkstra générée.");
            System.out.println("------------------------------------------------");

            // 3. Paramétrage du filtre ("Tous", "M", "O" ou "N")
            String filtreFiltre = "O";
            System.out.println("Lancement de l'optimisation pour 1 Camion (Filtre: " + filtreFiltre + ")...");

            // Instanciation du Recuit Simulé non-statique
            RecuitSimule rs = new RecuitSimule();

            long startTime = System.currentTimeMillis();

            // On envoie 'null' pour la liste de sommets : l'algorithme va automatiquement 
            // prendre tous les sommets du graphe correspondant au filtre
            Itineraire itineraire1Camion = rs.calculerTourneeOptimale(monGraphe, maMatriceDijkstra, null, filtreFiltre);

            long endTime = System.currentTimeMillis();

            // 4. Affichage des résultats
            System.out.println("------------------------------------------------");
            System.out.println("=== RESULTATS DE LA TOURNEE ===");
            System.out.println("Temps de calcul de l'algo : " + (endTime - startTime) + " ms");
            System.out.println("Durée totale du trajet : " + itineraire1Camion.getCout() + " minutes");

            System.out.println("\nOrdre détaillé des visites (Noms des centres) :");
            System.out.println(itineraire1Camion.getIdSommets());

            System.out.println("\nTypes des centres visités correspondants :");
            System.out.println(itineraire1Camion.getTypes());

            System.out.println("\nAffichage formaté (toString) :");
            System.out.println(itineraire1Camion);

        } catch (FileNotFoundException e) {
            System.err.println("Erreur : Impossible de trouver le fichier. Vérifie son emplacement dans 'data/'.");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Une erreur inattendue est survenue : ");
            e.printStackTrace();
        }

        System.out.println("------------------------------------------------");
        System.out.println("=== FIN DU TEST ===");
    }

}