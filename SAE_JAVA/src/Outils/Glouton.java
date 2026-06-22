/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Outils;

import Structure.Itineraire;
import Structure.Route;
import Structure.Sommet;
import java.util.ArrayList;
import java.util.List;

/**
 * Calcule, par une heuristique gloutonne du plus proche voisin, l'ordre de
 * visite des centres pour un camion. A chaque etape, on se rend au centre non
 * encore visite le plus proche (en duree), via la classe {@link Recherche}.
 *
 * @author Walid Ferchach
 */
public class Glouton {

    private final Recherche recherche;
    private double coutTotalTrajet;

    /**
     * Construit l'outil glouton.
     *
     * @param recherche le moteur de recherche de plus courts chemins a utiliser
     */
    public Glouton(Recherche recherche) {
        this.recherche = recherche;
    }

    /**
     * Renvoie le cout total (en duree) du dernier parcours calcule.
     *
     * @return le cout total du trajet
     */
    public double getCoutTotalTrajet() {
        return this.coutTotalTrajet;
    }

    /**
     * Calcule l'ordre de visite de tous les sommets en partant du sommet donne.
     *
     * @param tabS   l'ensemble des sommets a visiter
     * @param depart le sommet de depart (le depot)
     * @return l'ordre de visite des sommets
     */
    public List<Sommet> unCamion(Sommet[] tabS, Sommet depart) {
        return unCamionParType(tabS, depart, null);
    }

    /**
     * Calcule l'ordre de visite des sommets en se limitant a certains types de
     * centres. Si {@code types} vaut {@code null}, tous les sommets sont visites.
     *
     * @param tabS   l'ensemble des sommets a visiter
     * @param depart le sommet de depart (le depot)
     * @param types  les types de centres a desservir (1 ou 2 types), ou
     *               {@code null} pour tous les visiter
     * @return l'ordre de visite des sommets retenus
     * @throws IllegalArgumentException si {@code types} ne contient pas 1 ou 2 types
     */
    public List<Sommet> unCamionParType(Sommet[] tabS, Sommet depart, String[] types) {
        if (types != null && (types.length < 1 || types.length > 2)) {
            throw new IllegalArgumentException("Il faut choisir 1 ou 2 types parmi les 3.");
        }

        List<Sommet> parcours = new ArrayList<>();
        if (types == null || contient(types, depart.getType())) {
            parcours.add(depart);
        }

        List<Sommet> aVisiter = new ArrayList<>();
        for (Sommet s : tabS) {
            if (s != null && s != depart) {
                if (types == null || contient(types, s.getType())) {
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

    private boolean contient(String[] tableau, String valeur) {
        for (String t : tableau) {
            if (valeur.equals(t)) {
                return true;
            }
        }
        return false;
    }

    private Sommet plusProche(Sommet courant, List<Sommet> candidats) {
        Sommet plusProche = null;
        double meilleurCout = Double.MAX_VALUE;
        for (Sommet candidat : candidats) {
            Itineraire it = recherche.plusCourtCheminDuree(courant.getNom(), candidat.getNom());
            if (it != null && it.getCout() < meilleurCout) {
                meilleurCout = it.getCout();
                plusProche = candidat;                
            }
        }
        this.coutTotalTrajet += meilleurCout;
        return plusProche;
    }
}
