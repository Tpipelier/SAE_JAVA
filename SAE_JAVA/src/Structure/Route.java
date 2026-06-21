/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Structure;

/**
 *
 * @author compteadmin
 */
public class Route {

    String name;
    double fiabilite;
    int distance;
    int duree;
    private Sommet sDepart;
    private Sommet sArrivee;

    public Route(String name, double fiabilite, int distance, int duree, Sommet sD, Sommet sA) {
        this.name = name;
        this.fiabilite = fiabilite;
        this.distance = distance;
        this.duree = duree;
        this.sDepart = sD;
        this.sArrivee = sA;
    }

    public String getName() {
        return name;
    }

    public double getFiabilite() {
        return fiabilite;
    }

    public int getDistance() {
        return distance;
    }

    public int getDuree() {
        return duree;
    }

    public Sommet getSommetDepart() {
        return sDepart;
    }

    public Sommet getSommetArrivee() {
        return sArrivee;
    }
    
    public void setSommetDepart(Sommet sDepart) {
        this.sDepart = sDepart;
    }

    public void setSommetArrivee(Sommet sArrivee) {
        this.sArrivee = sArrivee;
    }

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
