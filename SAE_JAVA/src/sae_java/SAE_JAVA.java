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
        FileInputStream file = new FileInputStream("./data/graphe1.csv");
        Scanner scanner = new Scanner(file);
        Sommet[] tabS = new Sommet[nbSommet];

        Route[][] tabR = new Route[10][10];
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
                        System.out.println(tabS[i].getType() + " " + triplet[0] + " " + triplet[1] + " " + triplet[2]);
                        
                        System.out.println(tabR[i][k].getName() + " fiabilite : " + tabR[i][k].getFiabilité()+ " distance : " + tabR[i][k].getDistance()+ " duree : " + tabR[i][k].getDurée()+ " sommet depart : " + tabR[i][k].getsDépart().getNom()+ " sommet arrivee : " + tabR[i][k].getsArrivée());
                    
                    } else {
                            System.out.println("0");
                    }
                    k++;
                    numRoute++;

                }

                i++;

            }
        }
        scanner.close();

//        file = new FileInputStream("./data/graphe1.csv");
//        scanner = new Scanner(file); // Là, ça va marcher !
//        System.out.println("open");
//        i = 0;
//        int k = 0;
//        int indexArrivee;
//        int j;
//        while (scanner.hasNext()) {
//
//            j = 2;
//            String ligne = scanner.nextLine();
//            String[] tokens = ligne.split(";");
//            System.out.println(tokens[j]);
//
//            if (ligne.compareTo("// Graphe planaire maximal;") != 0 && ligne.compareTo("// 10 sommets") != 0) {
//                //System.out.println(tabS[i].getType()+" "+tabS[i].getNom());
//                while (j < tokens.length) {
//                    indexArrivee = j - 2;
//
//                    if (tokens[2].compareTo("0") != 0) {
//                        String[] triplet = tokens[2].split(",");
//                        System.out.println(tabS[i].getType() + " " + triplet[0] + " " + triplet[1] + " " + triplet[2]);
//                        tabR[k] = new Route("R" + j, Double.parseDouble(triplet[0]), Integer.parseInt(triplet[1]), Integer.parseInt(triplet[2]));
//                        System.out.println(tabR[k].getName() + " " + tabR[k].getFiabilité() + " " + tabR[k].getDistance() + " " + tabR[k].getDurée());
//
//                    } else {
//                        System.out.println("0 ");
//                    }
//
//                    j++;
//                    k++;
//                }
//                i++;
//
//            }
//
//        }
        ////            tab[i] = new Commune(Integer.parseInt(tokens[0]), tokens[1], tokens[2]);
//        }
//
//        //System.out.println(compteRoute("C:/Users/compteadmin/Desktop/SAE_JAVA/data/graphe1.csv"));
//        scanner.close();
//    }


//    public static int compteSommet(String nomFichier) {
//        int cpt = 0;
//        try {
//            FileInputStream file = new FileInputStream(nomFichier);
//            Scanner scanner = new Scanner(file);
//
//            while (scanner.hasNext()) {
//                String ligne = scanner.nextLine().trim();
//                // On ne compte la ligne que si elle n'est pas vide et ne commence pas par //
//                if (!ligne.isEmpty() && !ligne.startsWith("//")) {
//                    cpt++;
//                }
//            }
//            scanner.close();
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            return 0;
//        }
//        return cpt;
    

//    public static int compteRoute(String nomFichier) {
//        int totalRoutes = 0;
//        try {
//            FileInputStream file = new FileInputStream(nomFichier);
//            Scanner scanner = new Scanner(file);
//
//            while (scanner.hasNext()) {
//                String ligne = scanner.nextLine();
//                // On ignore les commentaires et lignes vides
//                if (!ligne.isEmpty() && !ligne.startsWith("//")) {
//                    String[] tokens = ligne.split(";");
//                    // Dans ton CSV, les routes commencent à partir du 3ème élément (index 2)
//                    // Donc le nombre de routes sur cette ligne est : (nombre de tokens) - 2
//                    if (tokens.length > 2) {
//                        totalRoutes += (tokens.length - 2);
//                    }
//                }
//            }
//            scanner.close();
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            return 0;
//        }
//        return totalRoutes;
//    }

//    public static int compteRoute(String nomFichier) {
//        int cpt = 0;
//        cpt=compteSommet(nomFichier)*(compteSommet(nomFichier)-1);
//        cpt=cpt/2;
//        return cpt;
//    }
//
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

    public static int compteRoute(String nomFichier) {
        int cpt = 0;
        int i;
        try {
            // Le fichier d'entrée
            FileInputStream file = new FileInputStream(nomFichier);
            Scanner scanner = new Scanner(file);

            //renvoie true tant qu'il y a une autre ligne à lire
            while (scanner.hasNext()) {
                i = 1;
                String ligne = scanner.nextLine();
                String[] tokens = ligne.split(";");
                while (i < tokens.length) {
                    i++;
                    cpt++;

                }
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
