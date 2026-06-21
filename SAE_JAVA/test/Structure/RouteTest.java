package Structure;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * Classe de test unitaire de la classe {@link Route}.
 * On verifie les accesseurs, les mutateurs et la recherche d'une route par les
 * noms de ses extremites.
 *
 * @author Equipe SAE_JAVA
 */
public class RouteTest {

    private Sommet depart;
    private Sommet arrivee;
    private Route route;

    /**
     * Construit deux sommets et une route les reliant avant chaque test.
     */
    @Before
    public void setUp() {
        depart = new Sommet(0, "S1", "M");
        arrivee = new Sommet(1, "S2", "N");
        route = new Route("R1", 8.5, 40, 30, depart, arrivee);
    }

    /**
     * Verifie l'accesseur du nom de la route.
     */
    @Test
    public void testGetName() {
        assertEquals("R1", route.getName());
    }

    /**
     * Verifie l'accesseur de la fiabilite.
     */
    @Test
    public void testGetFiabilite() {
        assertEquals(8.5, route.getFiabilite(), 0.0001);
    }

    /**
     * Verifie l'accesseur de la distance.
     */
    @Test
    public void testGetDistance() {
        assertEquals(40, route.getDistance());
    }

    /**
     * Verifie l'accesseur de la duree.
     */
    @Test
    public void testGetDuree() {
        assertEquals(30, route.getDuree());
    }

    /**
     * Verifie que le sommet de depart fourni au constructeur est bien renvoye.
     */
    @Test
    public void testGetSommetDepart() {
        assertEquals(depart, route.getSommetDepart());
    }

    /**
     * Verifie que le sommet d'arrivee fourni au constructeur est bien renvoye.
     */
    @Test
    public void testGetSommetArrivee() {
        assertEquals(arrivee, route.getSommetArrivee());
    }

    /**
     * Verifie le mutateur du sommet de depart.
     */
    @Test
    public void testSetSommetDepart() {
        Sommet nouveau = new Sommet(5, "S6", "O");
        route.setSommetDepart(nouveau);
        assertEquals(nouveau, route.getSommetDepart());
    }

    /**
     * Verifie le mutateur du sommet d'arrivee.
     */
    @Test
    public void testSetSommetArrivee() {
        Sommet nouveau = new Sommet(5, "S6", "O");
        route.setSommetArrivee(nouveau);
        assertEquals(nouveau, route.getSommetArrivee());
    }

    /**
     * La recherche par noms doit retrouver une route quel que soit l'ordre des
     * noms passes (la route n'est pas orientee pour cette recherche).
     *
     * @throws Exception si la construction du graphe echoue
     */
    @Test
    public void testTrouverRouteParNoms() throws Exception {
        Graphe g = new Graphe(2);
        g.ajouterSommet(0, depart);
        g.ajouterSommet(1, arrivee);
        g.ajouterRoute(0, 1, route);

        assertEquals(route, route.trouverRouteParNoms(g, "S1", "S2"));
        assertEquals(route, route.trouverRouteParNoms(g, "S2", "S1"));
    }

    /**
     * La recherche par noms renvoie {@code null} quand aucune route ne relie les
     * deux noms demandes.
     *
     * @throws Exception si la construction du graphe echoue
     */
    @Test
    public void testTrouverRouteParNomsInexistante() throws Exception {
        Graphe g = new Graphe(2);
        g.ajouterSommet(0, depart);
        g.ajouterSommet(1, arrivee);
        g.ajouterRoute(0, 1, route);

        assertNull(route.trouverRouteParNoms(g, "S1", "Inconnu"));
    }
}
