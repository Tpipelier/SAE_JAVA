package Structure;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * Classe de test unitaire de la classe {@link Sommet}.
 * On verifie les accesseurs ainsi que la redefinition de {@code equals} et
 * {@code hashCode} (qui ne se basent que sur l'index du sommet).
 *
 * @author Equipe SAE_JAVA
 */
public class SommetTest {

    private Sommet sommet;

    /**
     * Instancie un sommet de reference avant chaque test.
     */
    @Before
    public void setUp() {
        sommet = new Sommet(3, "Maternite Nord", "M");
    }

    /**
     * Verifie que {@code getIndex} renvoie l'index fourni au constructeur.
     */
    @Test
    public void testGetIndex() {
        assertEquals(3, sommet.getIndex());
    }

    /**
     * Verifie que {@code getNom} renvoie le nom fourni au constructeur.
     */
    @Test
    public void testGetNom() {
        assertEquals("Maternite Nord", sommet.getNom());
    }

    /**
     * Verifie que {@code getType} renvoie le type fourni au constructeur.
     */
    @Test
    public void testGetType() {
        assertEquals("M", sommet.getType());
    }

    /**
     * Deux sommets ayant le meme index doivent etre consideres egaux, meme si
     * leur nom et leur type different (egalite basee uniquement sur l'index).
     */
    @Test
    public void testEqualsMemeIndex() {
        Sommet autre = new Sommet(3, "Autre nom", "N");
        assertTrue(sommet.equals(autre));
    }

    /**
     * Deux sommets ayant un index different ne doivent pas etre egaux.
     */
    @Test
    public void testEqualsIndexDifferent() {
        Sommet autre = new Sommet(7, "Maternite Nord", "M");
        assertFalse(sommet.equals(autre));
    }

    /**
     * Un sommet n'est jamais egal a {@code null}.
     */
    @Test
    public void testEqualsNull() {
        assertFalse(sommet.equals(null));
    }

    /**
     * Deux sommets egaux doivent renvoyer le meme {@code hashCode}.
     */
    @Test
    public void testHashCodeCoherentAvecEquals() {
        Sommet autre = new Sommet(3, "Autre nom", "O");
        assertEquals(sommet.hashCode(), autre.hashCode());
    }
}
