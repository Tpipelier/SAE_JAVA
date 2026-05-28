/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package sae_java;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author Theo Pipelier
 */
public class SAE_JAVA {

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException {
        // TODO code application logic here
        Graphe g=new Graphe();
        g.genererGrapheAleatoire(4);
        g.afficheContenuGraphe();
        
        
        

        GrapheVisuel grapheVisuel = new GrapheVisuel("graphe1", g.getTabS(), g.getTabR());
        grapheVisuel.afficher();
    }      
    

}
