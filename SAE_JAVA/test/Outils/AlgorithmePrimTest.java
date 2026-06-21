package Outils;

import Structure.Graphe;
import Structure.Route;
import Structure.Sommet;
import java.io.FileNotFoundException;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * Classe de test unitaire de la classe {@link AlgorithmePrim}.
 *
 * <p>Le graphe de reference comporte trois sommets relies en chaine :
 * S1-S2 (duree 5) et S2-S3 (duree 7). L'arbre couvrant minimum doit donc
 * contenir exactement ces deux routes.</p>
 *
 * @author Equipe SAE_JAVA
 */
public class AlgorithmePrimTest {

    private Graphe graphe;

    /**
     * Construit le graphe en chaine de reference avant chaque test.
     */
    @Before
    public void setUp() {
        graphe = new Graphe(3);
        Sommet s1 = new Sommet(0, "S1", "M");
        Sommet s2 = new Sommet(1, "S2", "M");
        Sommet s3 = new Sommet(2, "S3", "N");
        graphe.ajouterSommet(0, s1);
        graphe.ajouterSommet(1, s2);
        graphe.ajouterSommet(2, s3);
        graphe.ajouterRoute(0, 1, new Route("R0", 8, 10, 5, s1, s2));
        graphe.ajouterRoute(1, 2, new Route("R1", 8, 20, 7, s2, s3));
    }

    /**
     * Pour n sommets connexes, l'ACM doit contenir exactement n-1 routes.
     */
    @Test
    public void testDeterminerACMNombreDeRoutes() {
        AlgorithmePrim prim = new AlgorithmePrim(graphe);
        List<Route> acm = prim.determinerACM();
        assertEquals(2, acm.size());
    }

    /**
     * L'ACM du graphe en chaine doit contenir les deux seules routes existantes.
     */
    @Test
    public void testDeterminerACMContenu() {
        AlgorithmePrim prim = new AlgorithmePrim(graphe);
        List<Route> acm = prim.determinerACM();

        boolean contientR0 = false;
        boolean contientR1 = false;
        for (Route r : acm) {
            if ("R0".equals(r.getName())) {
                contientR0 = true;
            }
            if ("R1".equals(r.getName())) {
                contientR1 = true;
            }
        }
        assertTrue(contientR0);
        assertTrue(contientR1);
    }

    /**
     * Sur un graphe sans sommet (constructeur par defaut), l'ACM est vide.
     *
     * @throws FileNotFoundException jamais ici (aucun fichier lu)
     */
    @Test
    public void testDeterminerACMGrapheVide() throws FileNotFoundException {
        AlgorithmePrim prim = new AlgorithmePrim(new Graphe());
        assertTrue(prim.determinerACM().isEmpty());
    }
}
