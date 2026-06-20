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

    List<Sommet> lstSommetACorriger;
    List<Route> acm;
    List<Route> lstCouplageRoute;
    private AlgorithmePrim a;
    private GrapheVisuel grapheVisuel;
    private Graphe graphe;
    private double duree;

    public AlgorithmeChristofide(GrapheVisuel grapheVisuel, Graphe graphe) {
        lstSommetACorriger = new ArrayList<>();
        lstCouplageRoute = new ArrayList<>();
        a = new AlgorithmePrim(graphe);
        acm = a.determinerACM();
        this.grapheVisuel = grapheVisuel;
        this.graphe = graphe;
        this.duree = 0;
    }

    /**
     * Exécute l'algorithme complet de Christofides combiné à Dijkstra.
     *
     * * @param graphStream Le graphe de la librairie GraphStream nécessaire
     * pour Dijkstra
     * @return La liste ordonnée des noms des centres à visiter
     */
    public List<String> executerChristofide() {
        // 1. Définir le point de départ automatique (ex: index 0)
        Sommet depart = graphe.getSommet(0);
        Sommet arrivee = this.trouverArriveeAutomatique(depart, acm);

        // Étapes 2 & 3 : Appel de ta nouvelle méthode avec départ et arrivée
        this.identifierSommetsACorriger(depart, arrivee);
        this.calculerLstCouplageRoute(); // (Qui utilisera en interne lstSommetACorriger)

        // Étape 4 : Création du multi-graphe
        List<Route> multiGraphe = new ArrayList<>();
        multiGraphe.addAll(acm);
        multiGraphe.addAll(lstCouplageRoute);

        
        List<Sommet> ordreGlobal = this.calculerCheminEulerien(multiGraphe, depart);

        
        return this.appliquerRaccourcisDijkstra(ordreGlobal, grapheVisuel);
    }

    private Sommet trouverArriveeAutomatique(Sommet depart, List<Route> acm) {
        List<Sommet> file = new ArrayList<>();
        List<Sommet> visites = new ArrayList<>();

        file.add(depart);
        visites.add(depart);

        Sommet dernierSommetAtteint = depart;

        while (!file.isEmpty()) {
            Sommet actuel = file.remove(0);
            dernierSommetAtteint = actuel; // Le dernier sorti de la file sera le plus éloigné

            // Trouver tous les voisins de 'actuel' UNIQUEMENT dans l'ACM
            for (Route r : acm) {
                Sommet voisin = null;
                if (r.getSommetDepart().equals(actuel)) {
                    voisin = r.getSommetArrivee();
                } else if (r.getSommetArrivee().equals(actuel)) {
                    voisin = r.getSommetDepart();
                }

                if (voisin != null && !visites.contains(voisin)) {
                    visites.add(voisin);
                    file.add(voisin);
                }
            }
        }

        return dernierSommetAtteint;
    }

    private void identifierSommetsACorriger(Sommet depart, Sommet arrivee) {
        lstSommetACorriger.clear();

        for (int i = 0; i < graphe.getNbSommet(); i++) {
            Sommet sommetAVerif = graphe.getSommet(i);
            int degreDansACM = 0;

            for (int j = 0; j < acm.size(); j++) {
                if (acm.get(j).getSommetDepart().equals(sommetAVerif)
                        || acm.get(j).getSommetArrivee().equals(sommetAVerif)) {
                    degreDansACM++;
                }
            }

            boolean estExtremite = sommetAVerif.equals(depart) || sommetAVerif.equals(arrivee);

            if (estExtremite) {
                // Le départ et l'arrivée DOIVENT être impairs. 
                // S'ils sont pairs, ils ont besoin d'une correction (on les ajoute).
                if (degreDansACM % 2 == 0) {
                    lstSommetACorriger.add(sommetAVerif);
                }
            } else {
                // Les autres sommets DOIVENT être pairs.
                // S'ils sont impairs, ils ont besoin d'une correction (on les ajoute).
                if (degreDansACM % 2 != 0) {
                    lstSommetACorriger.add(sommetAVerif);
                }
            }
        }
    }

    private void calculerLstCouplageRoute() {
        lstCouplageRoute.clear();

        // 1. Collecter toutes les routes uniques possibles entre les sommets de lstSommetACorriger
        List<Route> toutesLesRoutesPossibles = new ArrayList<>();

        for (int i = 0; i < lstSommetACorriger.size(); i++) {
            Sommet s1 = lstSommetACorriger.get(i);
            for (int j = i + 1; j < lstSommetACorriger.size(); j++) {
                Sommet s2 = lstSommetACorriger.get(j);

                
                Route routeActuelle = graphe.getRoute(graphe.indexDeSommet(s1), graphe.indexDeSommet(s2));
                if (routeActuelle != null) {
                    toutesLesRoutesPossibles.add(routeActuelle);
                }
            }
        }

        // 2. Trier ces routes de la plus courte à la plus longue
        toutesLesRoutesPossibles.sort((r1, r2) -> Double.compare(r1.getDuree(), r2.getDuree()));

        // 3. Associer les sommets en prenant les meilleures routes disponibles
        List<Sommet> sommetsDejaCouples = new ArrayList<>();
        int indexRoute = 0;

        // La boucle tourne tant qu'on a des routes à analyser ET que tous les sommets ne sont pas couplés
        while (indexRoute < toutesLesRoutesPossibles.size() && sommetsDejaCouples.size() < lstSommetACorriger.size()) {
            Route route = toutesLesRoutesPossibles.get(indexRoute);
            Sommet sDepart = route.getSommetDepart();
            Sommet sArrivee = route.getSommetArrivee();

            if (!sommetsDejaCouples.contains(sDepart) && !sommetsDejaCouples.contains(sArrivee)) {
                lstCouplageRoute.add(route);
                sommetsDejaCouples.add(sDepart);
                sommetsDejaCouples.add(sArrivee);
            }

            indexRoute++; // On passe à la route suivante
        }

        
    }

    /**
     * Algorithme de Hierholzer adapté pour extraire un CHEMIN Eulérien.
     */
    private List<Sommet> calculerCheminEulerien(List<Route> multiGraphe, Sommet depart) {
        List<Sommet> cheminFinal = new ArrayList<>();

        if (multiGraphe.isEmpty()) {
            return cheminFinal;
        }

        List<Sommet> cheminCourant = new ArrayList<>();
        cheminCourant.add(depart);

        Sommet sommetActuel = depart;

        while (!cheminCourant.isEmpty()) {
            Route routeSuivante = null;
            int indexRoute = 0;

            while (routeSuivante == null && indexRoute < multiGraphe.size()) {
                Route r = multiGraphe.get(indexRoute);
                if (r.getSommetDepart().equals(sommetActuel) || r.getSommetArrivee().equals(sommetActuel)) {
                    routeSuivante = r;
                }
                indexRoute++;
            }

            if (routeSuivante != null) {
                cheminCourant.add(sommetActuel);

                if (routeSuivante.getSommetDepart().equals(sommetActuel)) {
                    sommetActuel = routeSuivante.getSommetArrivee();
                } else {
                    sommetActuel = routeSuivante.getSommetDepart();
                }

                multiGraphe.remove(routeSuivante);
            } else {
                cheminFinal.add(0, sommetActuel);
                sommetActuel = cheminCourant.remove(cheminCourant.size() - 1);
            }
        }

        // Le bloc de correction forçant le retour à l'index 0 a été supprimé ici
        // pour laisser le chemin ouvert se terminer naturellement à son arrivée.
        return cheminFinal;
    }

    private List<String> appliquerRaccourcisDijkstra(List<Sommet> ordreGlobal, Graph graphStream) {
        List<String> trajetReelFinal = new ArrayList<>();
        duree = 0;

        // Utilisation du graphe passé en paramètre pour initialiser le module de recherche
        Recherche outilDijkstra = new Recherche(graphStream);

        for (int i = 0; i < ordreGlobal.size() - 1; i++) {
            String villeDepart = ordreGlobal.get(i).getNom();
            String villeArrivee = ordreGlobal.get(i + 1).getNom();

            Recherche.Itineraire sousTrajet = outilDijkstra.plusCourtCheminDuree(villeDepart, villeArrivee);

            if (sousTrajet != null) {
                duree += sousTrajet.getCout();
                ArrayList<String> etapes = sousTrajet.getSommets();

                // On retire le premier sommet de chaque sous-trajet pour éviter 
                // qu'un point de transition soit écrit deux fois d'affilée dans la liste
                if (i > 0 && !etapes.isEmpty()) {
                    etapes.remove(0);
                }
                trajetReelFinal.addAll(etapes);
            }
        }
        return trajetReelFinal;
    }
    
    public double getDuree(){
        return duree;
    }
}
