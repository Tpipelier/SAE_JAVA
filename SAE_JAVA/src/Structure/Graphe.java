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
import java.io.File;
import java.util.Comparator;

/**
 * Represente un graphe de centres de soins (les sommets) relies par des routes
 * (les aretes). Le graphe est stocke sous forme de matrice d'adjacence
 * ({@code tabR}) et d'un tableau de sommets ({@code tabS}). Il peut etre charge
 * depuis un fichier CSV, genere aleatoirement, ou construit manuellement.
 *
 * <p>Cette classe propose aussi des algorithmes de parcours : distances
 * croissantes, plus courts chemins (Dijkstra pondere par la duree), etc.</p>
 *
 * @author Theo Pipelier et Walid Ferchach
 */
public class Graphe {

    String nom;
    int nbSommet;
    Sommet[] tabS;
    Route[][] tabR;
    HashMap<Route, Integer> durees;

    /**
     * Construit un graphe vide. Les tableaux internes ne sont pas alloues ; ils
     * le seront lors d'un appel a {@link #chargerGraphes(String)} ou
     * {@link #genererGrapheAleatoire(int)}.
     *
     * @throws FileNotFoundException jamais levee par ce constructeur (conservee
     *         pour compatibilite)
     */
    public Graphe() throws FileNotFoundException {
        this.nbSommet = 0;
        this.tabS = null;
        this.tabR = null;
        this.durees = null;

    }

    /**
     * Construit un graphe vierge dont les tableaux sont dimensionnes pour
     * accueillir le nombre de sommets donne. Utile pour creer un sous-graphe
     * sans passer par un fichier CSV.
     *
     * @param nbSommet nombre de sommets du graphe
     */
    public Graphe(int nbSommet) {
        this.nbSommet = nbSommet; // On commencera à 0 et on incrémentera à chaque ajout
        this.tabS = new Sommet[nbSommet];
        this.tabR = new Route[nbSommet][nbSommet];
        this.durees = new HashMap<>();
    }

    /**
     * Compte le nombre de sommets decrits dans un fichier CSV, c'est-a-dire le
     * nombre de lignes utiles (les lignes vides et les lignes de commentaire
     * commencant par {@code //} sont ignorees).
     *
     * @param nomFichier chemin du fichier CSV a analyser
     * @return le nombre de sommets (lignes de donnees utiles)
     * @throws FileNotFoundException si le fichier est introuvable
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
     * Renvoie le nom du graphe (generalement le nom du fichier source).
     *
     * @return le nom du graphe
     */
    public String getNom() {
        return this.nom;
    }

    /**
     * Renvoie le nombre de sommets du graphe.
     *
     * @return le nombre de sommets
     */
    public int getNbSommet() {
        return nbSommet;
    }

    /**
     * Renvoie le sommet situe a la position demandee.
     *
     * @param numeroSommet index du sommet (entre 0 et nbSommet-1)
     * @return le sommet correspondant
     */
    public Sommet getSommet(int numeroSommet) {
        return tabS[numeroSommet];
    }

    /**
     * Renvoie la route reliant deux sommets (selon la matrice d'adjacence).
     *
     * @param numeroSommetD index du sommet de depart
     * @param numeroSommetA index du sommet d'arrivee
     * @return la route correspondante, ou {@code null} s'il n'y en a pas
     */
    public Route getRoute(int numeroSommetD, int numeroSommetA) {
        return tabR[numeroSommetD][numeroSommetA];
    }

    /**
     * Renvoie le tableau des sommets du graphe.
     *
     * @return le tableau des sommets
     */
    public Sommet[] getTabS() {
        return tabS;
    }

    /**
     * Renvoie la matrice d'adjacence des routes du graphe.
     *
     * @return la matrice des routes
     */
    public Route[][] getTabR() {
        return tabR;
    }

    /**
     * Renvoie la table associant chaque route a sa duree.
     *
     * @return la table des durees
     */
    public HashMap<Route, Integer> getDurees() {
        return this.durees;
    }

    /**
     * Place un sommet a la position demandee dans le tableau des sommets.
     *
     * @param numeroSommet index ou ranger le sommet
     * @param Sommet       le sommet a ajouter
     */
    public void ajouterSommet(int numeroSommet, Sommet Sommet) {
        this.tabS[numeroSommet] = Sommet;
    }

    /**
     * Ajoute une route entre deux sommets dans la matrice d'adjacence.
     *
     * @param numeroSommetD index du sommet de depart
     * @param numeroSommetA index du sommet d'arrivee
     * @param Route         la route a ajouter
     */
    public void ajouterRoute(int numeroSommetD, int numeroSommetA, Route Route) {
        this.tabR[numeroSommetD][numeroSommetA] = Route;
    }

    /**
     * Associe une duree a une route dans la table des durees.
     *
     * @param R     la route concernee
     * @param duree la duree a associer (en minutes)
     */
    public void ajouterDuree(Route R, int duree) {
        this.durees.put(R, duree);
    }

    /**
     * Charge un graphe depuis un fichier CSV : lit d'abord les sommets, puis les
     * routes (donnees au format {@code fiabilite,distance,duree}). Les tableaux
     * internes sont alloues a la bonne taille.
     *
     * @param nomFichier chemin du fichier CSV a charger
     * @throws FileNotFoundException si le fichier est introuvable
     */
    public void chargerGraphes(String nomFichier) throws FileNotFoundException {
        this.nom = new File(nomFichier).getName();
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

    /**
     * Affiche sur la sortie standard le detail de toutes les routes du graphe
     * (nom, fiabilite, sommets relies, distance et duree).
     */
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

    /**
     * Genere aleatoirement un graphe connexe du nombre de sommets demande.
     * Chaque sommet recoit un type tire au hasard ; un arbre couvrant garantit la
     * connexite, puis des aretes supplementaires sont ajoutees avec une faible
     * probabilite.
     *
     * @param nbSommets nombre de sommets du graphe a generer
     */
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
            ajouterSommet(i, new Sommet(i, "S" + (i + 1), type));
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

                if (random.nextInt(5) == 0) {
                    int fiabilite = (random.nextInt(9) + 2);
                    int distance = random.nextInt(41) + 10;
                    int duree = random.nextInt(111) + 10;
                    ajouterRoute(i, j, new Route("R" + numRoute, fiabilite, distance, duree, this.getSommet(i), this.getSommet(i + 1)));
                    ajouterDuree(tabR[i][j], duree);
                    numRoute++;

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
     * Renvoie l'indice d'un sommet dans le tableau des sommets. La comparaison se
     * fait sur le nom et le type du sommet.
     *
     * @param s le sommet recherche
     * @return l'indice du sommet, ou {@code -1} s'il n'existe pas dans le graphe
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
     * Renvoie les routes incidentes au sommet donne (dans un sens ou dans
     * l'autre), triees par distance croissante.
     *
     * @param depart le sommet dont on veut les routes incidentes
     * @return la liste des routes triees par distance croissante (vide si le
     *         sommet est absent du graphe)
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
                if (routes.get(k).getDistance() > routes.get(k + 1).getDistance()) {
                    Route temp = routes.get(k);
                    routes.set(k, routes.get(k + 1));
                    routes.set(k + 1, temp);
                }
            }
        }
        return routes;
    }

    /**
     * Calcule, depuis le sommet donne, les plus courts chemins (Dijkstra
     * pondere par la duree des routes) vers tous les autres sommets atteignables,
     * puis renvoie ces resultats tries selon le critere demande.
     *
     * @param depart   le sommet source
     * @param typeTrie critere de tri : {@code "Duree"}, {@code "DureeEstimee"} ou
     *                 toute autre valeur pour un tri par distance
     * @return la liste des resultats (nom, type, distance, duree, duree estimee),
     *         triee ; vide si le sommet source est absent du graphe
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
        } else if (typeTrie.equals("DureeEstimee")) {
            for (int i = 0; i < resultats.size(); i++) {
                for (int j = 0; j < resultats.size() - 1 - i; j++) {
                    if (resultats.get(j).getDureeEstimee() > resultats.get(j + 1).getDureeEstimee()) {
                        DistanceVers tmp = resultats.get(j);
                        resultats.set(j, resultats.get(j + 1));
                        resultats.set(j + 1, tmp);
                    }
                }
            }
        } else {
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
    
    

    /**
     * Renvoie une representation textuelle des cinq sommets les plus proches du
     * sommet de depart (ou moins s'il y en a moins de cinq).
     *
     * @param depart le sommet source
     * @return la liste (au plus 5) des sommets les plus proches sous forme de
     *         chaines
     */
    public List<String> top5Texte(Sommet depart) {
        List<DistanceVers> resultats = distancesCroissantesVersTous(depart,"");
        List<String> top = new ArrayList<>();

        int limite = 5;
        if (resultats.size() < 5) {
            limite = resultats.size();
        }

        for (int i = 0; i < limite; i++) {
            DistanceVers d = resultats.get(i);
            top.add(d.getNom() + d.getType() + d.getDistance());
        }

        return top;
    }

    /**
     * Compte le nombre de routes reellement presentes dans la matrice
     * d'adjacence (cases non nulles).
     *
     * @return le nombre de routes effectives du graphe
     */
    public int getNbRoutesEffectives() {
        int cpt = 0;
        if (this.tabR == null) {
            return 0;
        }

        for (int i = 0; i < this.nbSommet; i++) {
            for (int j = 0; j < this.nbSommet; j++) {
                if (this.tabR[i][j] != null) {
                    cpt++;
                }
            }
        }
        return cpt;
    }

}
