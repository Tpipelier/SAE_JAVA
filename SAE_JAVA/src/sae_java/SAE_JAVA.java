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
     */
    public static void main(String[] args) throws FileNotFoundException {
        // TODO code application logic here

        FileInputStream file = new FileInputStream("C:/Users/compteadmin/Desktop/SAE_JAVA/data/graphe1.csv");
        Scanner scanner = new Scanner(file);
        Sommet[] tabS = new Sommet[compteSommet("C:/Users/compteadmin/Desktop/SAE_JAVA/data/graphe1.csv")];
        Route[] tabR = new Route[compteRoute("C:/Users/compteadmin/Desktop/SAE_JAVA/data/graphe1.csv")];
        int i = 0;
        while (scanner.hasNext()) {

            String ligne = scanner.nextLine();
            String[] tokens = ligne.split(";");
            if (ligne.compareTo("// Graphe planaire maximal;") != 0 && ligne.compareTo("// 10 sommets") != 0) {
                tabS[i] = new Sommet(tokens[0], tokens[1]);
//                System.out.println(tabS[i].getType()+" "+tabS[i].getNom());
                i++;
            }

//            tab[i] = new Commune(Integer.parseInt(tokens[0]), tokens[1], tokens[2]);
        }
        scanner.close(); // /!\ Ceci ferme 'scanner' ET 'file' !

// --- DEUXIÈME LECTURE ---
// Tu DOIS recréer un nouveau FileInputStream ici
        file = new FileInputStream("C:/Users/compteadmin/Desktop/SAE_JAVA/data/graphe1.csv");
        scanner = new Scanner(file); // Là, ça va marcher !
        System.out.println("open");
        i = 0;
        int k = 0;
        int indexArrivee;
        int j;
        while (scanner.hasNext()) {

            j = 2;
            String ligne = scanner.nextLine();
            String[] tokens = ligne.split(";");
            
            
            if (ligne.compareTo("// Graphe planaire maximal;") != 0 && ligne.compareTo("// 10 sommets") != 0) {
                //System.out.println(tabS[i].getType()+" "+tabS[i].getNom());
                while (j < tokens.length) {
                    indexArrivee=j-2;
                    String[] triplet = tokens[j].split(",");
                    tabR[k] = new Route("R" + j, Double.parseDouble(tokens[0]),Integer.parseInt(tokens[1]),Integer.parseInt(tokens[2]), tabS[i], tabS[indexArrivee]);
                    System.out.println(tabR[k].getName() + " " + tabR[k].getValuation() + " " + tabR[k].getsArrivée() + " " + tabR[k].getsDépart());
                    j++;
                    k++;
                }
                i++;

            }

//            tab[i] = new Commune(Integer.parseInt(tokens[0]), tokens[1], tokens[2]);
        }

        //System.out.println(compteRoute("C:/Users/compteadmin/Desktop/SAE_JAVA/data/graphe1.csv"));
        scanner.close();
    }

    public static int compteRoute(String nomFichier) {
        int cpt = 0;
        try {
            // Le fichier d'entrée
            FileInputStream file = new FileInputStream(nomFichier);
            Scanner scanner = new Scanner(file);

            //renvoie true tant qu'il y a une autre ligne à lire
            while (scanner.hasNext()) {
                String ligne = scanner.nextLine();
                String[] tokens = ligne.split(";");
                cpt = cpt + tokens.length - 2;

            }

            scanner.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 0;
        }
        return cpt;
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

    public static Route[] chargerGraphes(String nom_fichier) throws FileNotFoundException {
        Route[] tab = new Route[compteSommet(nom_fichier)];
        Sommet[] tabS = new Sommet[compteSommet(nom_fichier)];
        int i = 0;

        // Le fichier d'entrée
        FileInputStream file = new FileInputStream(nom_fichier);
        Scanner scanner = new Scanner(file);

        //renvoie true tant qu'il y a une autre ligne à lire
        while (scanner.hasNext()) {
            String ligne = scanner.nextLine();
            System.out.println(ligne);
            String[] tokens = ligne.split(";");
            tabS[i] = new Sommet(tokens[0], tokens[1]);
//            tab[i] = new Route(Integer.parseInt(tokens[0]), tokens[1], tokens[2]);
            i++;
        }
        scanner.close();

        return tab;

    }

}
