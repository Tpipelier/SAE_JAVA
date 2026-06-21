package Structure;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * Classe de test unitaire de la classe {@link DistanceVers}.
 * On verifie les accesseurs (dont ceux delegues au sommet) et la chaine
 * produite par {@code toString}.
 *
 * @author Equipe SAE_JAVA
 */
public class DistanceVersTest {

    private Sommet sommet;
    private DistanceVers dv;

    /**
     * Cree un resultat de distance de reference avant chaque test.
     */
    @Before
    public void setUp() {
        sommet = new Sommet(2, "S3", "O");
        dv = new DistanceVers(sommet, 120, 45, 60);
    }

    /**
     * Verifie que le sommet stocke est bien renvoye.
     */
    @Test
    public void testGetSommet() {
        assertEquals(sommet, dv.getSommet());
    }

    /**
     * Le nom est delegue au sommet associe.
     */
    @Test
    public void testGetNom() {
        assertEquals("S3", dv.getNom());
    }

    /**
     * Le type est delegue au sommet associe.
     */
    @Test
    public void testGetType() {
        assertEquals("O", dv.getType());
    }

    /**
     * Verifie l'accesseur de la distance.
     */
    @Test
    public void testGetDistance() {
        assertEquals(120, dv.getDistance());
    }

    /**
     * Verifie l'accesseur de la duree.
     */
    @Test
    public void testGetDuree() {
        assertEquals(45, dv.getDuree());
    }

    /**
     * Verifie l'accesseur de la duree estimee.
     */
    @Test
    public void testGetDureeEstimee() {
        assertEquals(60, dv.getDureeEstimee());
    }

    /**
     * La representation textuelle ne doit pas etre nulle et doit contenir le nom
     * du sommet.
     */
    @Test
    public void testToStringNonNull() {
        assertNotNull(dv.toString());
        assertTrue(dv.toString().contains("S3"));
    }
}
