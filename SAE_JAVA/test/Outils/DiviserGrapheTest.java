package Outils;

import Structure.Graphe;
import Structure.Route;
import Structure.Sommet;
import java.util.HashSet;
import java.util.Set;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * Classe de test unitaire de la classe {@link DiviserGraphe}.
 *
 * <p>On verifie le decoupage d'un graphe de quatre sommets en deux sous-graphes :
 * chaque sous-graphe doit contenir le depot (sommet 0) et la reunion des autres
 * sommets des deux sous-graphes doit couvrir, sans doublon, l'ensemble des
 * sommets a desservir.</p>
 *
 * @author Equipe SAE_JAVA
 */
public class DiviserGrapheTest {

    private Graphe graphe;
    private Sommet depot;

    /**
     * Construit un graphe connexe de quatre sommets avant chaque test.
     */
    @Before
    public void setUp() {
        graphe = new Graphe(4);
        depot = new Sommet(0, "S1", "M");
        Sommet s2 = new Sommet(1, "S2", "M");
        Sommet s3 = new Sommet(2, "S3", "N");
        Sommet s4 = new Sommet(3, "S4", "O");
        graphe.ajouterSommet(0, depot);
        graphe.ajouterSommet(1, s2);
        graphe.ajouterSommet(2, s3);
        graphe.ajouterSommet(3, s4);

        // Chaine connexe : S1-S2-S3-S4 + un raccourci S1-S4
        graphe.ajouterRoute(0, 1, new Route("R0", 8, 10, 10, depot, s2));
        graphe.ajouterRoute(1, 2, new Route("R1", 8, 10, 10, s2, s3));
        graphe.ajouterRoute(2, 3, new Route("R2", 8, 10, 10, s3, s4));
        graphe.ajouterRoute(0, 3, new Route("R3", 8, 50, 50, depot, s4));
    }

    /**
     * Le decoupage doit produire exactement deux sous-graphes.
     */
    @Test
    public void testDiviserEnDeuxRenvoieDeuxGraphes() {
        Graphe[] resultats = DiviserGraphe.diviserEnDeux(graphe);
        assertEquals(2, resultats.length);
    }

    /**
     * Chaque sous-graphe doit contenir le depot en premiere position.
     */
    @Test
    public void testDepotPresentDansLesDeuxGraphes() {
        Graphe[] resultats = DiviserGraphe.diviserEnDeux(graphe);
        assertEquals(depot, resultats[0].getSommet(0));
        assertEquals(depot, resultats[1].getSommet(0));
    }

    /**
     * La reunion des sommets desservis (hors depot) des deux sous-graphes doit
     * couvrir exactement les trois sommets a livrer, sans doublon.
     */
    @Test
    public void testRepartitionComplete() {
        Graphe[] resultats = DiviserGraphe.diviserEnDeux(graphe);

        Set<String> desservis = new HashSet<>();
        int total = 0;
        for (Graphe sousGraphe : resultats) {
            for (int i = 1; i < sousGraphe.getNbSommet(); i++) {
                desservis.add(sousGraphe.getSommet(i).getNom());
                total++;
            }
        }

        // 3 sommets a desservir, sans repetition
        assertEquals(3, total);
        assertEquals(3, desservis.size());
        assertTrue(desservis.contains("S2"));
        assertTrue(desservis.contains("S3"));
        assertTrue(desservis.contains("S4"));
    }
}
