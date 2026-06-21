package Structure;

import java.io.FileNotFoundException;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * Classe de test unitaire de la classe {@link Graphe}.
 *
 * <p>La majorite des tests s'appuie sur un petit graphe construit a la main dans
 * {@link #setUp()} : trois sommets S1(M), S2(M), S3(N) avec une route S1-S2
 * (distance 10, duree 5) et une route S2-S3 (distance 20, duree 7). Aucune route
 * directe ne relie S1 et S3.</p>
 *
 * <p>Les tests de lecture de fichier ({@code compteSommet} et
 * {@code chargerGraphes}) utilisent le fichier {@code data/graphe1.csv} fourni
 * avec le projet ; ils doivent donc etre lances depuis la racine du projet.</p>
 *
 * @author Equipe SAE_JAVA
 */
public class GrapheTest {

    private Graphe graphe;
    private Sommet s1;
    private Sommet s2;
    private Sommet s3;

    /**
     * Construit le petit graphe de reference avant chaque test.
     */
    @Before
    public void setUp() {
        graphe = new Graphe(3);
        s1 = new Sommet(0, "S1", "M");
        s2 = new Sommet(1, "S2", "M");
        s3 = new Sommet(2, "S3", "N");
        graphe.ajouterSommet(0, s1);
        graphe.ajouterSommet(1, s2);
        graphe.ajouterSommet(2, s3);

        Route r12 = new Route("R0", 8, 10, 5, s1, s2);
        Route r23 = new Route("R1", 8, 20, 7, s2, s3);
        graphe.ajouterRoute(0, 1, r12);
        graphe.ajouterRoute(1, 2, r23);
        graphe.ajouterDuree(r12, 5);
        graphe.ajouterDuree(r23, 7);
    }

    /**
     * Le constructeur dimensionne doit fixer le nombre de sommets.
     */
    @Test
    public void testGetNbSommet() {
        assertEquals(3, graphe.getNbSommet());
    }

    /**
     * {@code getSommet} doit renvoyer le sommet ajoute a la position demandee.
     */
    @Test
    public void testGetSommet() {
        assertEquals(s2, graphe.getSommet(1));
    }

    /**
     * {@code getRoute} doit renvoyer la route ajoutee entre deux sommets.
     */
    @Test
    public void testGetRoute() {
        assertNotNull(graphe.getRoute(0, 1));
        assertEquals("R0", graphe.getRoute(0, 1).getName());
    }

    /**
     * Une case sans route doit valoir {@code null}.
     */
    @Test
    public void testGetRouteAbsente() {
        assertNull(graphe.getRoute(0, 2));
    }

    /**
     * {@code indexDeSommet} renvoie l'indice du sommet recherche (compare sur le
     * nom et le type).
     */
    @Test
    public void testIndexDeSommet() {
        assertEquals(2, graphe.indexDeSommet(new Sommet(99, "S3", "N")));
    }

    /**
     * {@code indexDeSommet} renvoie -1 pour un sommet absent du graphe.
     */
    @Test
    public void testIndexDeSommetInexistant() {
        assertEquals(-1, graphe.indexDeSommet(new Sommet(0, "Inconnu", "M")));
    }

    /**
     * Le nombre de routes effectives correspond aux cases non nulles de la
     * matrice (ici deux routes).
     */
    @Test
    public void testGetNbRoutesEffectives() {
        assertEquals(2, graphe.getNbRoutesEffectives());
    }

    /**
     * Sur un graphe sans matrice (constructeur par defaut), le nombre de routes
     * effectives vaut 0.
     *
     * @throws FileNotFoundException jamais ici (aucun fichier lu)
     */
    @Test
    public void testGetNbRoutesEffectivesGrapheVide() throws FileNotFoundException {
        Graphe vide = new Graphe();
        assertEquals(0, vide.getNbRoutesEffectives());
    }

    /**
     * {@code distancesCroissantes} renvoie les routes incidentes au sommet de
     * depart. Depuis S1, seule la route S1-S2 est incidente.
     */
    @Test
    public void testDistancesCroissantes() {
        List<Route> routes = graphe.distancesCroissantes(s1);
        assertEquals(1, routes.size());
        assertEquals("R0", routes.get(0).getName());
    }

    /**
     * {@code distancesCroissantesVersTous} (tri par defaut sur la distance) doit
     * trouver, depuis S1, S2 puis S3 avec les distances cumulees correctes.
     */
    @Test
    public void testDistancesCroissantesVersTous() {
        List<DistanceVers> res = graphe.distancesCroissantesVersTous(s1, "");
        assertEquals(2, res.size());

        // S2 est le plus proche (distance 10), puis S3 (distance cumulee 30)
        assertEquals("S2", res.get(0).getNom());
        assertEquals(10, res.get(0).getDistance());
        assertEquals(5, res.get(0).getDuree());

        assertEquals("S3", res.get(1).getNom());
        assertEquals(30, res.get(1).getDistance());
        assertEquals(12, res.get(1).getDuree());
    }

    /**
     * Pour un sommet absent du graphe, {@code distancesCroissantesVersTous}
     * renvoie une liste vide.
     */
    @Test
    public void testDistancesCroissantesVersTousSommetInconnu() {
        List<DistanceVers> res = graphe.distancesCroissantesVersTous(
                new Sommet(0, "Inconnu", "M"), "");
        assertTrue(res.isEmpty());
    }

    /**
     * {@code compteSommet} ne compte que les lignes de donnees utiles (ni vides,
     * ni commentaires) : le fichier graphe1.csv decrit 10 sommets.
     *
     * @throws FileNotFoundException si le fichier de donnees est introuvable
     */
    @Test
    public void testCompteSommet() throws FileNotFoundException {
        assertEquals(10, Graphe.compteSommet("data/graphe1.csv"));
    }

    /**
     * {@code chargerGraphes} doit lire les sommets et les routes du fichier CSV.
     *
     * @throws FileNotFoundException si le fichier de donnees est introuvable
     */
    @Test
    public void testChargerGraphes() throws FileNotFoundException {
        Graphe g = new Graphe();
        g.chargerGraphes("data/graphe1.csv");

        assertEquals(10, g.getNbSommet());
        assertEquals("S1", g.getSommet(0).getNom());
        assertEquals("M", g.getSommet(0).getType());
        assertTrue(g.getNbRoutesEffectives() > 0);
    }

    /**
     * Le chargement d'un fichier inexistant doit lever une
     * {@link FileNotFoundException}.
     *
     * @throws FileNotFoundException attendue : c'est le comportement teste
     */
    @Test(expected = FileNotFoundException.class)
    public void testChargerGraphesFichierInexistant() throws FileNotFoundException {
        Graphe g = new Graphe();
        g.chargerGraphes("data/fichier_inexistant.csv");
    }
}
