package Structure;

import java.util.ArrayList;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * Classe de test unitaire de la classe {@link Itineraire}.
 * On verifie les accesseurs de la liste des sommets, des types et du cout, ainsi
 * que la representation textuelle.
 *
 * @author Equipe SAE_JAVA
 */
public class ItineraireTest {

    private ArrayList<String> sommets;
    private ArrayList<String> types;
    private Itineraire itineraire;

    /**
     * Construit un itineraire de reference avant chaque test.
     */
    @Before
    public void setUp() {
        sommets = new ArrayList<>();
        sommets.add("S1");
        sommets.add("S2");

        types = new ArrayList<>();
        types.add("M");
        types.add("N");

        itineraire = new Itineraire(sommets, types, 42.0);
    }

    /**
     * Verifie l'accesseur de la liste des identifiants de sommets.
     */
    @Test
    public void testGetIdSommets() {
        assertEquals(sommets, itineraire.getIdSommets());
    }

    /**
     * Verifie l'accesseur de la liste des types.
     */
    @Test
    public void testGetTypes() {
        assertEquals(types, itineraire.getTypes());
    }

    /**
     * Verifie l'accesseur du cout total.
     */
    @Test
    public void testGetCout() {
        assertEquals(42.0, itineraire.getCout(), 0.0001);
    }

    /**
     * La representation textuelle ne doit pas etre nulle et doit mentionner les
     * sommets traverses.
     */
    @Test
    public void testToStringNonNull() {
        assertNotNull(itineraire.toString());
        assertTrue(itineraire.toString().contains("S1"));
    }
}
