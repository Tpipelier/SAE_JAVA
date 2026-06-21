/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Outils;

import IHM.GrapheVisuel;
import Structure.DistanceVers;
import Structure.Graphe;
import Structure.Itineraire;
import Structure.Route;
import Structure.Sommet;
import java.util.ArrayList;
import java.util.List;
import org.graphstream.graph.Graph;

/**
 * Implemente une variante de l'algorithme de Christofides pour approcher une
 * tournee de cout minimal. Il combine un arbre couvrant minimum (Prim), un
 * couplage des sommets de degre impair, un parcours eulerien (Hierholzer) puis
 * des raccourcis calcules par Dijkstra.
 *
 * @author Theo Pipelier
 */
public class AlgorithmeChristofide {

    List<Sommet> lstSommetACorriger;
    List<Route> acm;
    List<Route> lstCouplageRoute;
    List<String> typesChoisis;
    private AlgorithmePrim algoPrim;
    private GrapheVisuel grapheVisuel;
    private Graphe graphe;
    private double duree;

    /**
     * Construit l'algorithme : filtre le graphe selon les types choisis et
     * calcule immediatement l'arbre couvrant minimum servant de base.
     *
     * @param grapheVisuel le graphe GraphStream (pour les calculs Dijkstra)
     * @param graphe       le graphe a parcourir
     * @param typesChoisis les types de centres a desservir ({@code null} ou vide
     *                     pour tous)
     */
    public AlgorithmeChristofide(GrapheVisuel grapheVisuel, Graphe graphe, List<String> typesChoisis) {
        lstSommetACorriger = new ArrayList<>();
        lstCouplageRoute = new ArrayList<>();
        this.typesChoisis = typesChoisis;
        this.grapheVisuel = grapheVisuel;
        this.graphe = genererGrapheFiltre(graphe, typesChoisis);
        algoPrim = new AlgorithmePrim(this.graphe);
        acm = algoPrim.determinerACM();
        this.duree = 0;
    }

    private Graphe genererGrapheFiltre(Graphe global, List<String> typesChoisis) {
        // Si l'utilisateur n'a rien coché (ou liste vide), on travaille sur le graphe entier
        if (typesChoisis == null || typesChoisis.isEmpty()) {
            return global;
        }

        List<Sommet> sommetsValides = new ArrayList<>();

        Sommet depot = global.getSommet(0);
        sommetsValides.add(depot);

        for (int i = 1; i < global.getNbSommet(); i++) {
            Sommet s = global.getSommet(i);

            if (typesChoisis.contains(s.getType())) {
                sommetsValides.add(s);
            }
        }

        Graphe grapheFiltre = new Graphe(sommetsValides.size());
        for (int i = 0; i < sommetsValides.size(); i++) {

            grapheFiltre.ajouterSommet(i, sommetsValides.get(i));
        }

        // On relie chaque paire de sommets retenus par le PLUS COURT CHEMIN (en duree)
        // calcule sur le graphe complet. Le sous-graphe est ainsi toujours complet et
        // connexe : l'ACM couvre tous les centres souhaites, meme ceux qui ne sont pas
        // relies par une route directe.
        int n = grapheFiltre.getNbSommet();
        int numRoute = 0;
        for (int i = 0; i < n; i++) {
            Sommet s1 = grapheFiltre.getSommet(i);

            // Durees des plus courts chemins depuis s1 vers tous les autres sommets
            List<DistanceVers> plusCourtsChemins = global.distancesCroissantesVersTous(s1, "Duree");

            for (int j = i + 1; j < n; j++) {
                Sommet s2 = grapheFiltre.getSommet(j);

                // Recherche de la duree du plus court chemin s1 -> s2
                DistanceVers infoVersS2 = null;
                for (int k = 0; k < plusCourtsChemins.size(); k++) {
                    if (plusCourtsChemins.get(k).getSommet().equals(s2)) {
                        infoVersS2 = plusCourtsChemins.get(k);
                    }
                }

                if (infoVersS2 != null) { // s2 est atteignable depuis s1
                    Route routeVirtuelle = new Route(
                            "RF" + numRoute,
                            10, // fiabilite non utilisee par Christofides
                            infoVersS2.getDistance(),
                            infoVersS2.getDuree(),
                            s1, s2);
                    numRoute++;

                    grapheFiltre.ajouterRoute(i, j, routeVirtuelle);
                    grapheFiltre.ajouterRoute(j, i, routeVirtuelle);
                    grapheFiltre.ajouterDuree(routeVirtuelle, infoVersS2.getDuree());
                }
            }
        }

        return grapheFiltre;
    }

    /**
     * Execute l'algorithme complet de Christofides combine a Dijkstra et renvoie
     * l'ordre de visite des centres.
     *
     * @return la liste ordonnee des noms des centres a visiter
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

            Itineraire sousTrajet = outilDijkstra.plusCourtCheminDuree(villeDepart, villeArrivee);

            if (sousTrajet != null) {
                duree += sousTrajet.getCout();

                if (i > 0 && !sousTrajet.getIdSommets().isEmpty()) {
                    sousTrajet.getIdSommets().remove(0);
                    sousTrajet.getTypes().remove(0);
                }
                for (int j = 0; j < sousTrajet.getIdSommets().size(); j++) {
                    if (typesChoisis.contains(sousTrajet.getTypes().get(j))) {
                        String nomSommet = sousTrajet.getIdSommets().get(j);
                        
                        if (!trajetReelFinal.contains(nomSommet)) {
                            trajetReelFinal.add(nomSommet);
                        }
                    }

                }
            }
        }
        return trajetReelFinal;
    }

    /**
     * Renvoie la duree totale du dernier trajet calcule par
     * {@link #executerChristofide()}.
     *
     * @return la duree totale du trajet (en minutes)
     */
    public double getDuree() {
        return duree;
    }
}