package Outils;

import Structure.Graphe;
import Structure.Itineraire;
import Structure.Sommet;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * Classe de test unitaire de la classe {@link RecuitSimule}.
 *
 * <p>On construit un petit graphe de quatre centres (un depot S0 et trois
 * centres a desservir) accompagne de sa matrice des durees des plus courts
 * chemins. Cette matrice est volontairement concue comme un <em>piege</em> pour
 * l'heuristique du plus proche voisin : partir vers le centre le plus proche du
 * depot (S1, a une duree de 1) force ensuite a emprunter l'arete tres couteuse
 * S2-S3 (duree 10), pour un cout total de 13. La tournee optimale sacrifie cette
 * premiere arete bon marche et atteint un cout de 6 (S0-S2-S1-S3).</p>
 *
 * <p>Les tests verifient donc a la fois la validite des tournees produites
 * (completude, absence de doublon, depot en tete), le respect du filtrage par
 * type, le cas ou aucun centre n'est a visiter, le determinisme de l'algorithme
 * et, surtout, sa capacite a trouver l'optimum sur ce piege.</p>
 *
 * @author Equipe SAE_JAVA
 */
public class RecuitSimuleTest {

    /** Cout de la tournee optimale sur le graphe piege (S0-S2-S1-S3). */
    private static final double COUT_OPTIMAL = 6.0;
    /** Cout produit par l'heuristique gloutonne (plus proche voisin) sur ce piege. */
    private static final double COUT_GLOUTON = 13.0;

    private RecuitSimule recuit;
    private Graphe graphe;
    private int[][] matrice;
    private Sommet s0;
    private Sommet s1;
    private Sommet s2;
    private Sommet s3;

    /**
     * Construit, avant chaque test, l'algorithme, le graphe piege a quatre
     * centres (tous de type {@code "M"}) et sa matrice des durees symetrique.
     */
    @Before
    public void setUp() {
        recuit = new RecuitSimule();

        graphe = new Graphe(4);
        s0 = new Sommet(0, "S0", "M");
        s1 = new Sommet(1, "S1", "M");
        s2 = new Sommet(2, "S2", "M");
        s3 = new Sommet(3, "S3", "M");
        graphe.ajouterSommet(0, s0);
        graphe.ajouterSommet(1, s1);
        graphe.ajouterSommet(2, s2);
        graphe.ajouterSommet(3, s3);

        // Matrice des durees (symetrique). S1 est l'appat : tres proche du depot
        // mais l'arete S2-S3 coute 10, ce qui piege le plus proche voisin.
        matrice = new int[4][4];
        poserDuree(0, 1, 1);
        poserDuree(0, 2, 2);
        poserDuree(0, 3, 2);
        poserDuree(1, 2, 2);
        poserDuree(1, 3, 2);
        poserDuree(2, 3, 10);
    }

    /**
     * Renseigne la duree entre deux centres dans les deux sens (matrice
     * symetrique).
     */
    private void poserDuree(int i, int j, int duree) {
        matrice[i][j] = duree;
        matrice[j][i] = duree;
    }

    /**
     * Sur le graphe piege, le recuit doit atteindre la tournee optimale, de cout
     * {@value #COUT_OPTIMAL}.
     */
    @Test
    public void testRecuitTrouveOptimum() {
        Itineraire it = recuit.calculerTourneeOptimale(graphe, matrice, null, "Tous");
        assertEquals(COUT_OPTIMAL, it.getCout(), 0.0001);
    }

    /**
     * Le recuit doit faire strictement mieux que l'heuristique gloutonne de
     * depart (cout {@value #COUT_GLOUTON}) : c'est tout l'interet de la
     * metaheuristique.
     */
    @Test
    public void testRecuitMeilleurQueGlouton() {
        Itineraire it = recuit.calculerTourneeOptimale(graphe, matrice, null, "Tous");
        assertTrue("Le recuit (" + it.getCout() + ") doit etre meilleur que le glouton (" + COUT_GLOUTON + ")",
                it.getCout() < COUT_GLOUTON);
    }

    /**
     * La tournee doit couvrir le depot et les trois centres, soit quatre
     * sommets, et commencer par le depot.
     */
    @Test
    public void testTourneeComplete() {
        Itineraire it = recuit.calculerTourneeOptimale(graphe, matrice, null, "Tous");
        List<String> ids = it.getIdSommets();
        assertEquals(4, ids.size());
        assertEquals("S0", ids.get(0));
    }

    /**
     * La tournee ne doit visiter aucun centre deux fois.
     */
    @Test
    public void testTourneeSansDoublon() {
        Itineraire it = recuit.calculerTourneeOptimale(graphe, matrice, null, "Tous");
        List<String> ids = it.getIdSommets();
        Set<String> uniques = new HashSet<>(ids);
        assertEquals(ids.size(), uniques.size());
    }

    /**
     * L'algorithme utilisant des graines fixes, deux appels identiques doivent
     * produire exactement le meme cout.
     */
    @Test
    public void testDeterminisme() {
        Itineraire premier = recuit.calculerTourneeOptimale(graphe, matrice, null, "Tous");
        Itineraire second = recuit.calculerTourneeOptimale(graphe, matrice, null, "Tous");
        assertEquals(premier.getCout(), second.getCout(), 0.0001);
    }

    /**
     * Avec une liste de sommets explicite (cas deux camions), seuls ces sommets
     * (plus le depot) doivent apparaitre dans la tournee.
     */
    @Test
    public void testListeFournieNeVisiteQueCesSommets() {
        List<Sommet> aVisiter = new ArrayList<>();
        aVisiter.add(s0);
        aVisiter.add(s1);
        Itineraire it = recuit.calculerTourneeOptimale(graphe, matrice, aVisiter, "Tous");
        List<String> ids = it.getIdSommets();
        assertEquals(2, ids.size());
        assertTrue(ids.contains("S0"));
        assertTrue(ids.contains("S1"));
        assertFalse(ids.contains("S2"));
        assertFalse(ids.contains("S3"));
    }

    /**
     * Le filtrage par type ne doit conserver que les centres du type demande.
     * Ici, en filtrant sur {@code "O"} alors qu'aucun centre n'est de ce type
     * (et que le depot est une maternite), la tournee doit etre vide et de cout
     * nul.
     */
    @Test
    public void testAucunCentreCorrespondant() {
        Itineraire it = recuit.calculerTourneeOptimale(graphe, matrice, null, "O");
        assertTrue(it.getIdSommets().isEmpty());
        assertEquals(0.0, it.getCout(), 0.0001);
    }

    /**
     * En ne demandant qu'un seul type, tous les centres de la tournee doivent
     * etre de ce type. On reconstruit ici un graphe melangeant deux types.
     */
    @Test
    public void testFiltreParTypeNeGardeQueLeBonType() {
        Graphe g = new Graphe(3);
        Sommet depot = new Sommet(0, "C0", "M");
        Sommet materniteA = new Sommet(1, "C1", "M");
        Sommet bloc = new Sommet(2, "C2", "O");
        g.ajouterSommet(0, depot);
        g.ajouterSommet(1, materniteA);
        g.ajouterSommet(2, bloc);

        int[][] mat = new int[3][3];
        mat[0][1] = mat[1][0] = 1;
        mat[0][2] = mat[2][0] = 1;
        mat[1][2] = mat[2][1] = 1;

        Itineraire it = recuit.calculerTourneeOptimale(g, mat, null, "M");
        List<String> types = it.getTypes();
        assertFalse(types.isEmpty());
        for (String type : types) {
            assertEquals("M", type);
        }
        assertFalse(it.getIdSommets().contains("C2"));
    }
}
