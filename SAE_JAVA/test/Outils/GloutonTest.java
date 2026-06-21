package Outils;

import Structure.Sommet;
import java.util.List;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * Classe de test unitaire de la classe {@link Glouton}.
 *
 * <p>On reconstruit un graphe GraphStream connexe (S1-S2-S3) ainsi que le tableau
 * de sommets {@link Sommet} correspondant, puis on verifie que le parcours
 * glouton visite bien tous les centres en partant du depot.</p>
 *
 * @author Equipe SAE_JAVA
 */
public class GloutonTest {

    private Glouton glouton;
    private Sommet[] tabS;
    private Sommet depart;

    /**
     * Construit le graphe GraphStream, le moteur de recherche et le tableau de
     * sommets avant chaque test.
     */
    @Before
    public void setUp() {
        Graph g = new SingleGraph("test");
        ajouterSommet(g, "S1", "M");
        ajouterSommet(g, "S2", "M");
        ajouterSommet(g, "S3", "N");
        ajouterArete(g, "S1", "S2", 5.0);
        ajouterArete(g, "S2", "S3", 7.0);
        ajouterArete(g, "S1", "S3", 20.0);

        Recherche recherche = new Recherche(g);
        glouton = new Glouton(recherche);

        depart = new Sommet(0, "S1", "M");
        tabS = new Sommet[]{
            depart,
            new Sommet(1, "S2", "M"),
            new Sommet(2, "S3", "N")
        };
    }

    /**
     * Ajoute un sommet portant un attribut {@code type} au graphe GraphStream.
     */
    private void ajouterSommet(Graph g, String id, String type) {
        Node n = g.addNode(id);
        n.setAttribute("type", type);
    }

    /**
     * Ajoute une arete non orientee portant l'attribut {@code duree}.
     */
    private void ajouterArete(Graph g, String a, String b, double duree) {
        org.graphstream.graph.Edge e = g.addEdge(a + "-" + b, a, b);
        e.setAttribute("duree", duree);
    }

    /**
     * Le parcours d'un camion doit visiter tous les sommets et commencer par le
     * depot.
     */
    @Test
    public void testUnCamionVisiteTout() {
        List<Sommet> parcours = glouton.unCamion(tabS, depart);
        assertEquals(3, parcours.size());
        assertEquals(depart, parcours.get(0));
        assertTrue(parcours.contains(tabS[1]));
        assertTrue(parcours.contains(tabS[2]));
    }

    /**
     * En partant de S1, le plus proche voisin (en duree) est S2, qui doit donc
     * etre visite avant S3.
     */
    @Test
    public void testOrdreGlouton() {
        List<Sommet> parcours = glouton.unCamion(tabS, depart);
        assertEquals("S2", parcours.get(1).getNom());
        assertEquals("S3", parcours.get(2).getNom());
    }

    /**
     * Restreindre le parcours a un type doit ne conserver que les sommets de ce
     * type (ici les deux maternites S1 et S2).
     */
    @Test
    public void testUnCamionParType() {
        List<Sommet> parcours = glouton.unCamionParType(tabS, depart, new String[]{"M"});
        assertEquals(2, parcours.size());
        for (Sommet s : parcours) {
            assertEquals("M", s.getType());
        }
    }

    /**
     * Demander un nombre de types invalide doit lever une
     * {@link IllegalArgumentException}.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testUnCamionParTypeNombreInvalide() {
        glouton.unCamionParType(tabS, depart, new String[]{"M", "O", "N"});
    }
}
