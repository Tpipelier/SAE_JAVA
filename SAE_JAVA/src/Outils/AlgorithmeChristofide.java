/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Outils;

import IHM.GrapheVisuel;
import Structure.Graphe;
import Structure.Route;
import Structure.Sommet;
import java.util.ArrayList;
import java.util.List;
import org.graphstream.graph.Graph;

/**
 *
 * @author Theo Pipelier
 */
public class AlgorithmeChristofide {

    List<Sommet> lstSommetDegImpaire;
    List<Route> acm;
    List<Route> lstCouplageRoute;
    private AlgorithmePrim a;
    private GrapheVisuel grapheVisuel;
    private Graphe graphe;

    public AlgorithmeChristofide(GrapheVisuel grapheVisuel, Graphe graphe) {
        lstSommetDegImpaire = new ArrayList();
        lstCouplageRoute = new ArrayList();
        a = new AlgorithmePrim(graphe);
        acm = a.determinerACM();
        this.grapheVisuel = grapheVisuel;
        this.graphe = graphe;
    }

    /**
     * Exécute l'algorithme complet de Christofides combiné à Dijkstra.
     *
     * * @param graphStream Le graphe de la librairie GraphStream nécessaire
     * pour Dijkstra
     * @return La liste ordonnée des noms des centres à visiter
     */
    public List<String> executerChristofide() {
        // Étapes 2 & 3 : Sommets impairs et couplage parfait minimum
        this.remplirLstSommetDegImpaire();
        this.calculerLstCouplageRoute();

        // Étape 4 : Création du multi-graphe (Union de l'ACM et du Couplage)
        List<Route> multiGraphe = new ArrayList<>();
        multiGraphe.addAll(acm);
        multiGraphe.addAll(lstCouplageRoute);

        // Étape 5.A : Extraction du cycle eulérien (Ordre macro des villes)
        List<Sommet> ordreGlobal = this.calculerCycleEulerien(multiGraphe);

        // Étape 5.B : Raccourcis par Dijkstra (Itinéraire réel minute par minute)
        return this.appliquerRaccourcisDijkstra(ordreGlobal, grapheVisuel);
    }

    private void remplirLstSommetDegImpaire() {
        Sommet sommetAVerif;
        int nbRoute;

        for (int i = 0; i < graphe.getNbSommet(); i++) {
            nbRoute = 0; //corespond au degres du sommet i
            sommetAVerif = graphe.getSommet(i);
            for (int j = 0; j < acm.size(); j++) {
                if (acm.get(j).getSommetDepart().equals(sommetAVerif)
                        || acm.get(j).getSommetArrivee().equals(sommetAVerif)) {
                    nbRoute++;
                }
            }
            if (nbRoute % 2 != 0) {
                lstSommetDegImpaire.add(sommetAVerif);
            }
        }
    }

    private void calculerLstCouplageRoute() {
        Sommet sommetD;
        Sommet sommetA;
        Route routeDureeMin;
        while (!lstSommetDegImpaire.isEmpty()) {

            sommetD = lstSommetDegImpaire.remove(0);
            Route meilleurRoute = null;
            double dureeMin = Double.MAX_VALUE;
            int indexMeilleurVoisin = -1;
            for (int j = 0; j < lstSommetDegImpaire.size(); j++) {
                Sommet candidat = lstSommetDegImpaire.get(j);
                Route routeActuelle = graphe.getRoute(sommetD.getIndex(), candidat.getIndex());
                if (routeActuelle != null) {
                    double dureeActuelle = routeActuelle.getDuree();

                    if (dureeActuelle < dureeMin) {
                        dureeMin = dureeActuelle;
                        meilleurRoute = routeActuelle;
                        indexMeilleurVoisin = j; // On garde en memoire où il est dans la liste
                    }
                }
            }
            if (meilleurRoute != null) {
                lstCouplageRoute.add(meilleurRoute);
                // On retire le voisin trouve pour ne plus le reutiliser !
                lstSommetDegImpaire.remove(indexMeilleurVoisin);
            }

        }
    }

//    private List<Sommet> calculerCycleEulerien(List<Route> multiGraphe) {
//        List<Sommet> ordreGlobal = new ArrayList<>();
//
//        // 1. Trouver le point de départ "1" 
//        Sommet sommetActuel = null;
//        int indexSommet = 0;
//        while (sommetActuel == null && indexSommet < graphe.getNbSommet()) {
//            if (graphe.getSommet(indexSommet).getNom().equals("S1")) {
//                sommetActuel = graphe.getSommet(indexSommet);
//            }
//            indexSommet++;
//        }
//        ordreGlobal.add(sommetActuel);
//
//        // 2. Parcours des arêtes du multi-graphe 
//        boolean continuerParcours = true;
//        while (!multiGraphe.isEmpty() && continuerParcours) {
//            Route routeSuivante = null;
//            int indexRoute = 0;
//
//            // On cherche une route connectée au sommet actuel
//            while (routeSuivante == null && indexRoute < multiGraphe.size()) {
//                Route r = multiGraphe.get(indexRoute);
//                if (r.getSommetDepart().equals(sommetActuel)) {
//                    routeSuivante = r;
//                    sommetActuel = r.getSommetArrivee();
//                } else if (r.getSommetArrivee().equals(sommetActuel)) {
//                    routeSuivante = r;
//                    sommetActuel = r.getSommetDepart();
//                }
//                indexRoute++;
//            }
//
//            // Si on a trouvé une route, on avance, sinon on arrête la grande boucle
//            if (routeSuivante != null) {
//                ordreGlobal.add(sommetActuel);
//                multiGraphe.remove(routeSuivante);
//            } else {
//                continuerParcours = false; // Remplace le break du while principal
//            }
//        }
//
//        // 3. Sécurité : Fermeture de la boucle vers le point "1" (sans break)
//        if (sommetActuel != null && !sommetActuel.getNom().equals("S1")) {
//            Sommet depart = null;
//            int k = 0;
//            while (depart == null && k < graphe.getNbSommet()) {
//                if (graphe.getSommet(k).getNom().equals("S1")) {
//                    depart = graphe.getSommet(k);
//                    ordreGlobal.add(depart);
//                }
//                k++;
//            }
//        }
//
//        return ordreGlobal;
//    }
    private List<Sommet> calculerCycleEulerien(List<Route> multiGraphe) {
        List<Sommet> cycleFinal = new ArrayList<>();

        // Sécurité : si le multi-graphe est vide
        if (multiGraphe.isEmpty()) {
            return cycleFinal;
        }

        // 1. Trouver le point de départ : le sommet qui a l'attribut index = 1
        Sommet depart = null;
        for (int i = 0; i < graphe.getNbSommet(); i++) {
            // CORRECTION : On cherche par l'attribut index au lieu du nom
            if (graphe.getSommet(i).getIndex() == 0) {
                depart = graphe.getSommet(i);
                break;
            }
        }

        // Si l'index 1 n'est pas trouvé par sécurité, on prend le premier sommet disponible
        if (depart == null) {
            depart = multiGraphe.get(0).getSommetDepart();
        }

        // Listes de travail pour l'algorithme de Hierholzer
        List<Sommet> cheminCourant = new ArrayList<>();
        cheminCourant.add(depart);

        Sommet sommetActuel = depart;

        while (!cheminCourant.isEmpty()) {
            // On cherche s'il reste une route connectée au sommet actuel
            Route routeSuivante = null;
            for (Route r : multiGraphe) {
                if (r.getSommetDepart().equals(sommetActuel) || r.getSommetArrivee().equals(sommetActuel)) {
                    routeSuivante = r;
                    break;
                }
            }

            if (routeSuivante != null) {
                // On avance : on ajoute le sommet actuel à notre pile de chemin
                cheminCourant.add(sommetActuel);

                // On détermine le prochain sommet
                if (routeSuivante.getSommetDepart().equals(sommetActuel)) {
                    sommetActuel = routeSuivante.getSommetArrivee();
                } else {
                    sommetActuel = routeSuivante.getSommetDepart();
                }

                // On supprime la route consommée du multi-graphe
                multiGraphe.remove(routeSuivante);
            } else {
                // Impasse ou boucle fermée : ce sommet fait définitivement partie du cycle
                cycleFinal.add(0, sommetActuel); // On l'ajoute au début pour inverser à la fin

                // On recule d'un cran dans le chemin courant pour explorer d'autres branches
                sommetActuel = cheminCourant.remove(cheminCourant.size() - 1);
            }
        }

        // CORRECTION SECURISE : On s'assure que le sommet d'index 1 se retrouve bien en premier
        if (!cycleFinal.isEmpty() && cycleFinal.get(0).getIndex() != 0) {
            cycleFinal.add(0, cycleFinal.remove(cycleFinal.size() - 1));
        }

        return cycleFinal;
    }

    private List<String> appliquerRaccourcisDijkstra(List<Sommet> ordreGlobal, Graph graphStream) {
        List<String> trajetReelFinal = new ArrayList<>();
        double dureeTotaleDuVoyage = 0;

        // Utilisation du graphe passé en paramètre pour initialiser le module de recherche
        Recherche outilDijkstra = new Recherche(graphStream);

        for (int i = 0; i < ordreGlobal.size() - 1; i++) {
            String villeDepart = ordreGlobal.get(i).getNom();
            String villeArrivee = ordreGlobal.get(i + 1).getNom();

            Recherche.Itineraire sousTrajet = outilDijkstra.plusCourtCheminDuree(villeDepart, villeArrivee);

            if (sousTrajet != null) {
                dureeTotaleDuVoyage += sousTrajet.getCout();
                ArrayList<String> etapes = sousTrajet.getSommets();

                // On retire le premier sommet de chaque sous-trajet pour éviter 
                // qu'un point de transition soit écrit deux fois d'affilée dans la liste
                if (i > 0 && !etapes.isEmpty()) {
                    etapes.remove(0);
                }
                trajetReelFinal.addAll(etapes);
            }
        }

        System.out.println("Defi Christofides - Duree totale : " + Math.round(dureeTotaleDuVoyage) + " min");
        return trajetReelFinal;
    }
}
