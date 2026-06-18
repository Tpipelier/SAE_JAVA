/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Structure;

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
        List<Sommet> aVisiter = new ArrayList<>();
        for (Sommet s : tabS) {
            if (s != null && s != depart) {
                aVisiter.add(s);
            }
        }

        List<Sommet> parcours = new ArrayList<>();
        parcours.add(depart);
        Sommet courant = depart;

        while (!aVisiter.isEmpty()) {
            Sommet prochain = plusProche(courant, aVisiter);
            parcours.add(prochain);
            aVisiter.remove(prochain);
            courant = prochain;
        }

        return parcours;
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
