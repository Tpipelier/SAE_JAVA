/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Structure;

import java.util.ArrayList;

/**
 * Resultat d'une recherche de chemin : la liste ordonnee des centres traverses,
 * la liste de leurs types et le cout total du trajet.
 *
 * @author Walid Ferchach
 */
public class Itineraire {

    private final ArrayList<String> idSommet;
    private final ArrayList<String> typeSommet;
    private final double cout;

    /**
     * Construit un itineraire.
     *
     * @param idSommet   liste ordonnee des noms des sommets traverses
     * @param typeSommet liste des types des sommets traverses (meme ordre)
     * @param cout       cout total du trajet
     */
    public Itineraire(ArrayList<String> idSommet, ArrayList<String> typeSommet,double cout) {
        this.idSommet = idSommet;
        this.typeSommet = typeSommet;
        this.cout = cout;
    }

    /**
     * Renvoie la liste ordonnee des noms des sommets traverses.
     *
     * @return les identifiants des sommets de l'itineraire
     */
    public ArrayList<String> getIdSommets() {
        return idSommet;
    }

    /**
     * Renvoie la liste des types des sommets traverses.
     *
     * @return les types des sommets de l'itineraire
     */
    public ArrayList<String> getTypes(){
        return typeSommet;
    }

    /**
     * Renvoie le cout total du trajet.
     *
     * @return le cout total de l'itineraire
     */
    public double getCout() {
        return cout;
    }

    /**
     * Renvoie une representation textuelle de l'itineraire (sommets et cout
     * arrondi).
     *
     * @return une chaine decrivant l'itineraire
     */
    @Override
    public String toString() {
        return idSommet + " (" + Math.round(cout) + " min)";
    }
}
