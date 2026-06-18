/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Structure;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import Structure.Sommet;
import Structure.Route;

/**
 *
 * @author Theo Pipelier
 */
public class Graphe {

    int nbSommet;
    Sommet[] tabS;
    Route[][] tabR;
    HashMap<Route, Integer> durees;

    /**
     *
     * @throws FileNotFoundException
     */
    public Graphe() throws FileNotFoundException {
        this.nbSommet = 0;
        this.tabS = null;
        this.tabR = null;
        this.durees = null;

    }

    /**
     *
     * @param nomFichier
     * @return cpt
     * @throws FileNotFoundException
     */
    public static int compteSommet(String nomFichier) throws FileNotFoundException {
        int cpt = 0;

        // Le fichier d'entree
        FileInputStream file = new FileInputStream(nomFichier);
        Scanner scanner = new Scanner(file);

        //renvoie true tant qu'il y a une autre ligne à lire
        while (scanner.hasNext()) {
            String ligne = scanner.nextLine();
            // On ne compte que les lignes de donnees utiles (pas les commentaires)
            if (!ligne.isEmpty() && !ligne.startsWith("//")) {
                cpt++;
            }
        }
        scanner.close();

        return cpt;

    }

    /**
     *
     * @return int
     */
    public int getNbSommet() {
        return nbSommet;
    }

    /**
     *
     * @param numeroSommet
     * @return Sommet
     */
    public Sommet getSommet(int numeroSommet) {
        return tabS[numeroSommet];
    }

    /**
     *
     * @param numeroSommetD
     * @param numeroSommetA
     * @return Route
     */
    public Route getRoute(int numeroSommetD, int numeroSommetA) {
        return tabR[numeroSommetD][numeroSommetA];
    }

    /**
     *
     * @return Sommet[]
     */
    public Sommet[] getTabS() {
        return tabS;
    }

    /**
     *
     * @return Route[][]
     */
    public Route[][] getTabR() {
        return tabR;
    }

    /**
     *
     * @return HashMap
     */
    public HashMap<Route, Integer> getDurees() {
        return this.durees;
    }

    /**
     *
     * @param numeroSommet
     * @param Sommet
     */
    public void ajouterSommet(int numeroSommet, Sommet Sommet) {
        this.tabS[numeroSommet] = Sommet;
    }

    /**
     *
     * @param numeroSommetD
     * @param numeroSommetA
     * @param Route
     */
    public void ajouterRoute(int numeroSommetD, int numeroSommetA, Route Route) {
        this.tabR[numeroSommetD][numeroSommetA] = Route;
    }

    /**
     *
     * @param R
     * @param duree
     */
    public void ajouterDuree(Route R, int duree) {
        this.durees.put(R, duree);
    }

    /**
     *
     * @param nomFichier
     * @throws FileNotFoundException
     */
    public void chargerGraphes(String nomFichier) throws FileNotFoundException {
        this.nbSommet = compteSommet(nomFichier);
        this.tabS = new Sommet[nbSommet];
        this.tabR = new Route[nbSommet][nbSommet];
        this.durees = new HashMap<>();

        // Le fichier d'entree
        FileInputStream fichier = new FileInputStream(nomFichier);
        Scanner scannerTabS = new Scanner(fichier);

        int i = 0;
        while (scannerTabS.hasNext()) {

            String ligne = scannerTabS.nextLine();
            try {
                if (!ligne.startsWith("//")) {
                    String[] tokens = ligne.split(";");
                    ajouterSommet(i, new Sommet(i, tokens[0].trim(), tokens[1].trim()));

                    i++;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                System.err.println("Erreur : La ligne du fichier CSV ne contient pas assez d'elements pour creer le sommet à l'index " + i);
            }
        }
        scannerTabS.close();
        FileInputStream fileTabR = new FileInputStream(nomFichier);
        Scanner scannerTabR = new Scanner(fileTabR);
        i = 0;
        int numRoute = 0;
        while (scannerTabR.hasNext()) {

            String ligne = scannerTabR.nextLine();

            if (!ligne.startsWith("//")) {

                String[] tokens = ligne.split(";");

                for (int j = 2; j < tokens.length; j++) {
                    if (tokens[j].compareTo("0") != 0 && tokens[j].compareTo("") != 0) {
                        try {
                            String[] triplet = tokens[j].trim().split(",");
                            ajouterRoute(i, j - 2, new Route("R" + numRoute, Double.parseDouble(triplet[0].trim()), Integer.parseInt(triplet[1].trim()), Integer.parseInt(triplet[2].trim()), tabS[i], tabS[j - 2]));
                            ajouterDuree(tabR[i][j - 2], Integer.parseInt(triplet[2].trim()));

                        } catch (NumberFormatException e) {
                            System.out.println("Route corrompue sur la ligne suivante : " + ligne + " (Raison : " + e.getMessage() + ")");
                        }

                    }

                    numRoute++;

                }

                i++;

            }
        }
        scannerTabR.close();

    }

    public void afficheContenuGraphe() {
        String ligne = "";
        for (int i = 0; i < this.nbSommet; i++) {
            for (int j = 0; j < this.nbSommet; j++) {
                if (this.tabR[i][j] != null) {
                    ligne = "Nom : " + this.tabR[i][j].getName() + " sa fiabilite est de :" + this.tabR[i][j].getFiabilite() + " sa distance du sommet " + this.tabS[i].getNom() + " au sommet :" + this.tabS[j].getNom() + " est de : " + this.tabR[i][j].getDistance() + " la duree du trajet est de : " + this.tabR[i][j].getDuree() + "\n";
                    System.out.println(ligne);
                }
            }
        }

    }

    public void genererGrapheAleatoire(int nbSommets) {
        this.nbSommet = nbSommets;
        this.tabS = new Sommet[nbSommet];
        this.tabR = new Route[nbSommet][nbSommet];
        this.durees = new HashMap<>();
        Random random = new Random();
        int numRoute = 0;

        // 1. Creation des sommets avec un type aleatoire
        for (int i = 0; i < nbSommets; i++) {
            int tirage = random.nextInt(5);
            String type;
            if (tirage < 3) {
                type = "M";
            } else if (tirage == 3) {
                type = "O";
            } else {
                type = "N";
            }
            ajouterSommet(i, new Sommet(i,"S" + (i + 1), type));
        }

        // 2. Arbre couvrant : garantit que le graphe est CONNEXE (aucun sommet isole)
        //    Chaque sommet i (>=1) est relie à un sommet dejà place (j < i).
        for (int i = 1; i < nbSommets; i++) {
            int j = random.nextInt(i); // j est forcement < i
            numRoute = creerRoute(j, i, random, numRoute); // j < i => respecte tabR[min][max]
        }

        // 3. Arêtes supplementaires avec probabilite 1/5 (sans ecraser celles de l'arbre)
        for (int i = 0; i < nbSommets; i++) {
            for (int j = i + 1; j < nbSommets; j++) {
//<<<<<<< HEAD
                if (random.nextInt(5) == 0) {
                    int fiabilite = (random.nextInt(9) + 2);
                    int distance = random.nextInt(41) + 10;
                    int duree = random.nextInt(111) + 10;
                    ajouterRoute(i, j, new Route("R" + numRoute, fiabilite, distance, duree, this.getSommet(i), this.getSommet(i + 1)));
                    ajouterDuree(tabR[i][j], duree);
                    numRoute++;
//=======
//                if (tabR[i][j] == null && random.nextInt(5) == 0) {
//                    numRoute = creerRoute(i, j, random, numRoute);
//>>>>>>> origin/walid
                }
            }
        }
    }

// Helper : cree une route entre i et j (avec i < j) et renvoie le prochain numero de route
    private int creerRoute(int i, int j, Random random, int numRoute) {
        double fiabilite = random.nextInt(9) + 2;   // echelle 2 à 10
        int distance = random.nextInt(41) + 10;
        int duree = random.nextInt(111) + 10;
        ajouterRoute(i, j, new Route("R" + numRoute, fiabilite, distance, duree, this.getSommet(i), this.getSommet(j)));
        ajouterDuree(tabR[i][j], duree);
        return numRoute + 1;
    }

    /**
     * Renvoie l'indice d'un sommet dans tabS (-1 s'il n'existe pas).
     */
    public int indexDeSommet(Sommet s) {
        for (int i = 0; i < this.nbSommet; i++) {
            if (this.tabS[i].getNom().equals(s.getNom()) && this.tabS[i].getType().equals(s.getType())) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Renvoie les routes partant du sommet donne (vers les maternites, blocs
     * operatoires et centres de nutrition), triees par distance croissante.
     */
    public List<Route> distancesCroissantes(Sommet depart) {
        List<Route> routes = new ArrayList<>();
        int idx = indexDeSommet(depart);
        if (idx == -1) {
            return routes;
        }
        for (int j = 0; j < this.nbSommet; j++) {
            Route r = this.tabR[idx][j];
            if (r == null) {
                r = this.tabR[j][idx];
            }
            if (r != null) {
                routes.add(r);
            }
        }
        // Tri à bulles par distance croissante
        for (int i = 0; i < routes.size() - 1; i++) {
            for (int k = 0; k < routes.size() - 1 - i; k++) {
                if (routes.get(k).getDuree() > routes.get(k + 1).getDuree()) {
                    Route temp = routes.get(k);
                    routes.set(k, routes.get(k + 1));
                    routes.set(k + 1, temp);
                }
            }
        }
        return routes;
    }

    /**
     * Petit resultat associant un sommet d'arrivee a la distance la plus courte
     * depuis le sommet de depart. Donne acces au nom et au type.
     */
    public static class DistanceVers {

        private final Sommet sommet;
        private final int distance;
        private final int duree;
        private final int dureeEstimee;

        public DistanceVers(Sommet sommet, int distance, int duree, int dureeEstimee) {
            this.sommet = sommet;
            this.distance = distance;
            this.duree = duree;
            this.dureeEstimee = dureeEstimee;
        }

        public Sommet getSommet() {
            return sommet;
        }

        public String getNom() {
            return sommet.getNom();
        }

        public String getType() {
            return sommet.getType();
        }

        public int getDistance() {
            return distance;
        }

        public int getDuree() {
            return duree;
        }

        public int getDureeEstimee() {
            return dureeEstimee;
        }

        @Override
        public String toString() {
            return sommet.getNom() + " (" + sommet.getType() + ") : " + distance + "km" + " : " + duree + "min" + " : " + dureeEstimee + "min estimee\n";
        }
    }

    /**
     * Calcule, depuis le sommet donne, la distance la plus courte (Dijkstra
     * pondere par la distance des routes) vers tous les autres sommets, puis
     * renvoie ces resultats (nom, type et distance) tries par ordre croissant.
     */
    public List<DistanceVers> distancesCroissantesVersTous(Sommet depart, String typeTrie) {
        List<DistanceVers> resultats = new ArrayList<>();
        int source = indexDeSommet(depart);
        if (source == -1) {
            return resultats;
        }

        int[] dist = new int[this.nbSommet];
        int[] duree = new int[this.nbSommet];
        int[] dureeEstimee = new int[this.nbSommet];
        boolean[] visite = new boolean[this.nbSommet];
        for (int i = 0; i < this.nbSommet; i++) {
            dist[i] = Integer.MAX_VALUE;
            duree[i] = Integer.MAX_VALUE;
            dureeEstimee[i] = Integer.MAX_VALUE;
        }
        dist[source] = 0;
        duree[source] = 0;
        dureeEstimee[source] = 0;

        for (int n = 0; n < this.nbSommet; n++) {
            // On choisit le sommet non visite le plus proche
            int u = -1;
            for (int i = 0; i < this.nbSommet; i++) {
                if (!visite[i] && (u == -1 || duree[i] < duree[u])) {
                    u = i;
                }
            }
            visite[u] = true;

            // Mise a jour des voisins de u
            for (int v = 0; v < this.nbSommet; v++) {
                Route r = (this.tabR[u][v] != null) ? this.tabR[u][v] : this.tabR[v][u];
                if (r != null && duree[u] + r.getDuree() < duree[v]) {
                    dist[v] = dist[u] + r.getDistance();
                    duree[v] = duree[u] + r.getDuree();
                    dureeEstimee[v] = (int) (dureeEstimee[u] + r.getDuree() * (20 - r.getFiabilite()) / 10);
                }
            }
        }

        // On garde les autres sommets atteignables (avec leur nom et leur type)
        for (int i = 0; i < this.nbSommet; i++) {
            if (i != source && duree[i] != Integer.MAX_VALUE) {
                resultats.add(new DistanceVers(this.tabS[i], dist[i], duree[i], dureeEstimee[i]));
            }
        }

        // Tri par distance croissante (tri a bulles)
        if (typeTrie.equals("Duree")) {
            for (int i = 0; i < resultats.size(); i++) {
                for (int j = 0; j < resultats.size() - 1 - i; j++) {
                    if (resultats.get(j).getDuree() > resultats.get(j + 1).getDuree()) {
                        DistanceVers tmp = resultats.get(j);
                        resultats.set(j, resultats.get(j + 1));
                        resultats.set(j + 1, tmp);
                    }
                }
            }
        }else if (typeTrie.equals("DureeEstimee")) {
            for (int i = 0; i < resultats.size(); i++) {
                for (int j = 0; j < resultats.size() - 1 - i; j++) {
                    if (resultats.get(j).getDureeEstimee() > resultats.get(j + 1).getDureeEstimee()) {
                        DistanceVers tmp = resultats.get(j);
                        resultats.set(j, resultats.get(j + 1));
                        resultats.set(j + 1, tmp);
                    }
                }
            }
        }else {
            for (int i = 0; i < resultats.size(); i++) {
                for (int j = 0; j < resultats.size() - 1 - i; j++) {
                    if (resultats.get(j).getDistance() > resultats.get(j + 1).getDistance()) {
                        DistanceVers tmp = resultats.get(j);
                        resultats.set(j, resultats.get(j + 1));
                        resultats.set(j + 1, tmp);
                    }
                }
            }
        }
        return resultats;
    }

}
