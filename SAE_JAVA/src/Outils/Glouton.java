/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Outils;


import Structure.Route;
import Structure.Sommet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author walid
 */
public class glouton {

    private final Recherche recherche;

    public glouton(Recherche recherche){
        this.recherche = recherche;
    }

    public List<Sommet> unCamion(Sommet[] tabS, Sommet depart){
        return unCamionParType(tabS, depart, null);
    }
    
    // si on met type à null, il traverse tout les sommet sinon on met un truc du genre : String[]{"M", "O"} et il fera que ces sommet là
    public List<Sommet> unCamionParType(Sommet[] tabS, Sommet depart, String[] types){
        if (types != null && (types.length < 1 || types.length > 2)) {
            throw new IllegalArgumentException("Il faut choisir 1 ou 2 types parmi les 3.");
        }

        List<Sommet> parcours = new ArrayList<>();
        parcours.add(depart);

        List<Sommet> aVisiter = new ArrayList<>();
        for (Sommet s : tabS) {
            if (s != null && s != depart){
                if (types == null || contient(types, s.getType())){
                    aVisiter.add(s);
                }
            }
        }

        Sommet courant = depart;
        while (!aVisiter.isEmpty()) {
            Sommet prochain = plusProche(courant, aVisiter);
            if (prochain == null) {
                break; // plus aucun sommet desservi n'est atteignable
            }
            parcours.add(prochain);
            aVisiter.remove(prochain);
            courant = prochain;
        }

        return parcours;
    }

    private boolean contient(String[] tableau, String valeur){
        for (String t : tableau) {
            if (valeur.equals(t)) {
                return true;
            }
        }
        return false;
    }

    private Sommet plusProche(Sommet courant, List<Sommet> candidats){
        Sommet plusProche = null;
        double meilleurCout = Double.MAX_VALUE;
        for (Sommet candidat : candidats) {
            Recherche.Itineraire it = recherche.plusCourtCheminDuree(courant.getNom(), candidat.getNom());
            if (it != null && it.getCout() < meilleurCout) {
                meilleurCout = it.getCout();
                plusProche = candidat;
            }
        }
        return plusProche;
    }
}
