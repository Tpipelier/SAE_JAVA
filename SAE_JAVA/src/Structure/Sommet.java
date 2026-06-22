/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Structure;

import java.util.Objects;

/**
 * Represente un sommet du graphe, c'est-a-dire un centre de soins a desservir.
 * Chaque sommet possede un index unique (sa position dans la matrice du graphe),
 * un nom et un type ({@code "M"} maternite, {@code "O"} bloc operatoire,
 * {@code "N"} centre de nutrition).
 *
 * <p>L'egalite entre deux sommets ne repose que sur leur index.</p>
 *
 * @author Theo Pipelier
 */
public class Sommet {
    private String nom;
    private String type;
    private int index;


    /**
     * Construit un sommet.
     *
     * @param index position du sommet dans le graphe (identifiant unique)
     * @param nom   nom du centre
     * @param type  type du centre : {@code "M"}, {@code "O"} ou {@code "N"}
     */
    public Sommet(int index, String nom,String type) {
        this.index = index;
        this.nom = nom;
        this.type=type;
    }

    /**
     * Renvoie le nom du centre.
     *
     * @return le nom du sommet
     */
    public String getNom() {
        return nom;
    }

    /**
     * Renvoie le type du centre.
     *
     * @return le type du sommet ({@code "M"}, {@code "O"} ou {@code "N"})
     */
    public String getType() {
        return type;
    }


    /**
     * Renvoie l'index (position) du sommet dans le graphe.
     *
     * @return l'index du sommet
     */
    public int getIndex() {
        return index;
    }

    /**
     * Compare ce sommet a un autre objet. Deux sommets sont consideres egaux si
     * et seulement s'ils ont le meme index.
     *
     * @param obj l'objet a comparer
     * @return {@code true} si {@code obj} est un sommet de meme index
     */
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


    /**
     * Renvoie le code de hachage du sommet, calcule uniquement a partir de son
     * index, en coherence avec {@link #equals(Object)}.
     *
     * @return le code de hachage du sommet
     */
    @Override
    public int hashCode() {
        return Objects.hash(index);
    }
}
