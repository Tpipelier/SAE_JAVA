///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package Structure;
//
//import java.io.FileNotFoundException;
//import java.util.List;
//import IHM.GrapheVisuel;
//
///**
// * Classe principale : teste la classe glouton (tournee gloutonne par type).
// *
// * @author walid
// */
//public class Main {
//
//    public static void main(String[] args) throws FileNotFoundException {
//
//        // 1. Graphe aleatoire connexe de 10 sommets
//        Graphe graphe = new Graphe();
//        graphe.genererGrapheAleatoire(10);
//
//        // 2. Outils de recherche (Dijkstra via GraphStream) et tournee gloutonne
//        GrapheVisuel grapheVisuel = new GrapheVisuel("g", graphe.getTabS(), graphe.getTabR());
//        Recherche recherche = new Recherche(grapheVisuel);
//        glouton g = new glouton(recherche);
//
//        Sommet depart = graphe.getSommet(0);
//
//        // Recapitulatif des sommets et de leur type
//        System.out.println("=== Sommets du graphe ===");
//        for (Sommet s : graphe.getTabS()) {
//            System.out.println("  " + s.getNom() + " (" + s.getType() + ")");
//        }
//        System.out.println("Depart : " + depart.getNom() + " (" + depart.getType() + ")\n");
//
//        // 3. Test 1 : tournee desservant TOUS les sommets
//        afficherTournee("Tous les types", g.unCamion(graphe.getTabS(), depart));
//
//        // 4. Test 2 : tournee desservant UN seul type (maternites)
//        afficherTournee("1 type : M",
//                g.unCamionParType(graphe.getTabS(), depart, new String[]{"M"}));
//
//        // 5. Test 3 : tournee desservant DEUX types (M + O)
//        afficherTournee("2 types : M, O",
//                g.unCamionParType(graphe.getTabS(), depart, new String[]{"M", "O"}));
//
//        // 6. Test 4 : verification de la validation (0 ou 3 types interdits)
//        try {
//            g.unCamionParType(graphe.getTabS(), depart, new String[]{"M", "O", "N"});
//        } catch (IllegalArgumentException e) {
//            System.out.println("Validation OK (3 types refuses) : " + e.getMessage());
//        }
//
//        // 7. Affichage graphique du reseau
//        grapheVisuel.afficher();
//    }
//
//    /** Affiche une tournee : son intitule, le parcours et le nombre d'etapes. */
//    private static void afficherTournee(String titre, List<Sommet> parcours) {
//        System.out.println("=== Tournee [" + titre + "] ===");
//        System.out.print("  Parcours : ");
//        for (int i = 0; i < parcours.size(); i++) {
//            System.out.print(parcours.get(i).getNom() + " (" + parcours.get(i).getType() + ")");
//            if (i < parcours.size() - 1) {
//                System.out.print(" -> ");
//            }
//        }
//        System.out.println();
//        System.out.println("  Sommets desservis : " + parcours.size() + "\n");
//    }
//}
