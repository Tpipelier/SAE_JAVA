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
 * @author compteadmin
 */
public class SAE_JAVA {

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException {
        // TODO code application logic here
        int nbSommet = compteSommet("./data/graphe1.csv");
        int nbRoute = nbSommet * nbSommet;
        Sommet[] tabS = new Sommet[nbSommet];
        Route[][] tabR = new Route[nbSommet][nbSommet];
        chargerGraphes("./data/graphe1.csv",tabS,tabR);
        for(int i=0;i<nbSommet;i++){
            for(int j=0;j<nbSommet;j++){
                if(tabR[i][j]!=null){
                    System.out.println("Nom : "+tabR[i][j].getName()+" sa fiabilite est de :"+tabR[i][j].getFiabilité()+" sa distance du sommet "+tabS[i].getNom()+" au sommet :"+tabS[j].getNom()+ " est de : "+tabR[i][j].getDistance()+" la duree du trajet est de : "+tabR[i][j].getDurée());}
            }
        }
        
        
    }      
    public static int compteSommet(String nomFichier) {
        int cpt = 0;
        try {
            // Le fichier d'entrée
            FileInputStream file = new FileInputStream(nomFichier);
            Scanner scanner = new Scanner(file);

            //renvoie true tant qu'il y a une autre ligne à lire
            while (scanner.hasNext()) {
                cpt++;
                scanner.nextLine();
            }
            scanner.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 0;
        }
        
        return cpt;
        
    }


    public static void chargerGraphes(String file,Sommet[] tabS,Route[][] tabR) throws FileNotFoundException {        
        
        
        
        // Le fichier d'entrée
        FileInputStream fichier = new FileInputStream(file);
        Scanner scanner = new Scanner(fichier);

        //renvoie true tant qu'il y a une autre ligne à lire
        int i = 0;
        int k;
        int numRoute=0;
        while (scanner.hasNext()) {
            
            String ligne = scanner.nextLine();
            String[] tokens = ligne.split(";");
            if (ligne.compareTo("// Graphe planaire maximal;") != 0 && ligne.compareTo("// 10 sommets") != 0) {
                tabS[i] = new Sommet(tokens[0], tokens[1]);
                k=0;
                for (int j = 2; j < tokens.length; j++) {
                    
                    if (tokens[j].compareTo("0")!= 0) {
                        
                        String[] triplet = tokens[j].split(",");
                        tabR[i][k] = new Route("R" + numRoute, Double.parseDouble(triplet[0]), Integer.parseInt(triplet[1]), Integer.parseInt(triplet[2]),tabS[i],"S"+k);
                        
                
                    
                    } else {
                        
                    }
                    k++;
                    numRoute++;

                }

                i++;

            }
        }
        scanner.close();

        

    }

}
