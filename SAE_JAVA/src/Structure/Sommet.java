/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Structure;

/**
 *
 * @author compteadmin
 */
public class Sommet {
    private String nom;
    private String type;
    private int index;
    
    public Sommet(int index, String nom,String type) {
        this.index = index;
        this.nom = nom;
        this.type=type;
    }

    public String getNom() {
        return nom;
    }

    public String getType() {
        return type;
    }

    public int getIndex() {
        return index;
    }
    
    
}
