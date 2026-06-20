/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Structure;

import java.util.Objects;

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
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Sommet other = (Sommet) obj;
        
        // On compare uniquement l'index
        return this.index == other.index;
    }

    // --- Redéfinition de hashCode (Basée uniquement sur l'index) ---
    @Override
    public int hashCode() {
        return Objects.hash(index);
    }
}
