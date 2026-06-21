/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Structure;

/**
 * Represente une route (arete) reliant deux sommets du graphe. Une route porte
 * un nom, une fiabilite, une distance (en km) et une duree de trajet (en min),
 * ainsi que ses sommets de depart et d'arrivee.
 *
 * @author Equipe SAE_JAVA
 */
public class Route {

    String name;
    double fiabilite;
    int distance;
    int duree;
    private Sommet sDepart;
    private Sommet sArrivee;

    /**
     * Construit une route reliant deux sommets.
     *
     * @param name      nom de la route
     * @param fiabilite fiabilite de la route
     * @param distance  distance du trajet en kilometres
     * @param duree     duree du trajet en minutes
     * @param sD        sommet de depart
     * @param sA        sommet d'arrivee
     */
    public Route(String name, double fiabilite, int distance, int duree, Sommet sD, Sommet sA) {
        this.name = name;
        this.fiabilite = fiabilite;
        this.distance = distance;
        this.duree = duree;
        this.sDepart = sD;
        this.sArrivee = sA;
    }

    /**
     * Renvoie le nom de la route.
     *
     * @return le nom de la route
     */
    public String getName() {
        return name;
    }

    /**
     * Renvoie la fiabilite de la route.
     *
     * @return la fiabilite de la route
     */
    public double getFiabilite() {
        return fiabilite;
    }

    /**
     * Renvoie la distance du trajet.
     *
     * @return la distance en kilometres
     */
    public int getDistance() {
        return distance;
    }

    /**
     * Renvoie la duree du trajet.
     *
     * @return la duree en minutes
     */
    public int getDuree() {
        return duree;
    }

    /**
     * Renvoie le sommet de depart de la route.
     *
     * @return le sommet de depart
     */
    public Sommet getSommetDepart() {
        return sDepart;
    }

    /**
     * Renvoie le sommet d'arrivee de la route.
     *
     * @return le sommet d'arrivee
     */
    public Sommet getSommetArrivee() {
        return sArrivee;
    }

    /**
     * Modifie le sommet de depart de la route.
     *
     * @param sDepart le nouveau sommet de depart
     */
    public void setSommetDepart(Sommet sDepart) {
        this.sDepart = sDepart;
    }

    /**
     * Modifie le sommet d'arrivee de la route.
     *
     * @param sArrivee le nouveau sommet d'arrivee
     */
    public void setSommetArrivee(Sommet sArrivee) {
        this.sArrivee = sArrivee;
    }

    /**
     * Recherche, dans un graphe, la route reliant deux centres designes par leur
     * nom. La recherche n'est pas orientee : l'ordre des noms n'a pas
     * d'importance.
     *
     * @param global le graphe dans lequel chercher
     * @param nom1   nom d'un des deux centres
     * @param nom2   nom de l'autre centre
     * @return la route reliant les deux centres, ou {@code null} si elle n'existe
     *         pas
     */
    public Route trouverRouteParNoms(Graphe global, String nom1, String nom2) {
    int nbSommetsGlobaux = global.getNbSommet();
    
    for (int i = 0; i < nbSommetsGlobaux; i++) {
        for (int j = 0; j < nbSommetsGlobaux; j++) {
            Route r = global.getRoute(i, j);
            
            if (r != null) {
                String dep = r.getSommetDepart().getNom();
                String arr = r.getSommetArrivee().getNom();
                
                // On vérifie si la route relie bien nom1 et nom2 (dans un sens ou dans l'autre)
                if ((dep.equals(nom1) && arr.equals(nom2)) || (dep.equals(nom2) && arr.equals(nom1))) {
                    return r;
                }
            }
        }
    }
    return null; // Aucune route n'existe entre ces deux villes
}
    

}
