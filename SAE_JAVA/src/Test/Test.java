/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Test;

import IHM.FenetrePrincipale;
import IHM.GrapheVisuel;
import Outils.AlgorithmeChristofide;
import Outils.DiviserGraphe;
import Structure.Graphe;
import Structure.Sommet;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;
import org.graphstream.graph.Graph;

/**
 *
 * @author Theo Pipelier
 */
public class Test {

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException {
        // TODO code application logic here
//        Graphe g=new Graphe();
//        g.genererGrapheAleatoire(20);
//        g.afficheContenuGraphe();
//        
//        
//        
//
//        GrapheVisuel grapheVisuel = new GrapheVisuel("graphe1", g.getTabS(), g.getTabR());
//        grapheVisuel.afficher();
        FenetrePrincipale f = new FenetrePrincipale();
//        Graphe g=new Graphe();
//        g.genererGrapheAleatoire(20);
//        GrapheVisuel grapheVisuel = new GrapheVisuel("Graphe charger", g.getTabS(), g.getTabR());
//        
//        AlgorithmeChristofide ac = new AlgorithmeChristofide(grapheVisuel,g);
//        List<String> lst = ac.executerChristofide();
//        System.out.println("\n"+lst);    

//        try {
//            System.out.println("=== TEST UNITAIRE DU DECOUPEUR ===");
//
//            // 1. On charge le graphe global depuis ton CSV
//            Graphe grapheGlobal = new Graphe();
//            grapheGlobal.chargerGraphes("data/graphe5.csv"); // Change le chemin si besoin
//
//            System.out.println("Nombre total de sommets dans le graphe global : " + grapheGlobal.getNbSommet());
//            System.out.println("--------------------------------------------------");
//
//            // 2. On lance le découpage
//            Graphe[] resultats = DiviserGraphe.diviserEnDeux(grapheGlobal);
//            Graphe zoneC1 = resultats[0];
//            Graphe zoneC2 = resultats[1];
//
//            // 3. On affiche les sommets du Camion 1
//            System.out.println("\n📦 SOMMETS AFFECTÉS AU CAMION 1 (" + zoneC1.getNbSommet() + " villes) :");
//            for (int i = 0; i < zoneC1.getNbSommet(); i++) {
//                Sommet s = zoneC1.getSommet(i);
//                System.out.println("  -> Case " + i + " : " + s.getNom() + " (Index d'origine: " + s.getIndex() + ")");
//            }
//
//            // 4. On affiche les sommets du Camion 2
//            System.out.println("\n📦 SOMMETS AFFECTÉS AU CAMION 2 (" + zoneC2.getNbSommet() + " villes) :");
//            for (int i = 0; i < zoneC2.getNbSommet(); i++) {
//                Sommet s = zoneC2.getSommet(i);
//                System.out.println("  -> Case " + i + " : " + s.getNom() + " (Index d'origine: " + s.getIndex() + ")");
//            }
//
//            System.out.println("\n--------------------------------------------------");
//            System.out.println("Vérification : Le dépôt (Centre 1) doit être à la Case 0 des deux camions.");
//            System.out.println("Aucun autre index d'origine ne doit être commun aux deux listes !");
//
//        } catch (FileNotFoundException e) {
//            System.err.println("Fichier de données introuvable ! Vérifie le chemin d'accès.");
//        } catch (Exception e) {
//            System.err.println("Erreur durant le test : " + e.getMessage());
//            e.printStackTrace();
//        }
    }

}
