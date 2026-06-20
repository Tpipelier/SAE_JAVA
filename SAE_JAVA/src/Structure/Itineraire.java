/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Structure;

import java.util.ArrayList;

/**
 *
 * @author Walid Ferchach
 * Résultat d'une recherche : les centres traversés et le coût total.
 */
public class Itineraire {
    
    private final ArrayList<String> idSommet;
    private final ArrayList<String> typeSommet;
    private final double cout;

    public Itineraire(ArrayList<String> idSommet, ArrayList<String> typeSommet,double cout) {
        this.idSommet = idSommet;
        this.typeSommet = typeSommet;
        this.cout = cout;
    }

    public ArrayList<String> getIdSommets() {
        return idSommet;
    }

    public ArrayList<String> getTypes(){
        return typeSommet;
    }
    
    public double getCout() {
        return cout;
    }

    @Override
    public String toString() {
        return idSommet + " (" + Math.round(cout) + " min)";
    }
}
