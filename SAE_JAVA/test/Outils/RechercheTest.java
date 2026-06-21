package Outils;

import Structure.Itineraire;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * Classe de test unitaire de la classe {@link Recherche}.
 *
 * <p>Un petit graphe GraphStream est construit a la main, avec sur chaque arete
 * l'attribut {@code duree} utilise par Dijkstra. Topologie :
 * S1-S2 (duree 5), S2-S3 (duree 7) et S1-S3 (duree 20). Le sommet S4 est isole.
 * Le plus court chemin de S1 a S3 passe donc par S2 (cout 12).</p>
 *
 * @author Equipe SAE_JAVA
 */
public class RechercheTest {

    private Recherche recherche;

    /**
     * Construit le graphe GraphStream de reference avant chaque test.
     */
    @Before
    public void setUp() {
        Graph g = new SingleGraph("test");
        ajouterSommet(g, "S1", "M");
        ajouterSommet(g, "S2", "M");
        ajouterSommet(g, "S3", "N");
        ajouterSommet(g, "S4", "O"); // isole

        ajouterArete(g, "S1", "S2", 5.0);
        ajouterArete(g, "S2", "S3", 7.0);
        ajouterArete(g, "S1", "S3", 20.0);

        recherche = new Recherche(g);
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
     * Le plus court chemin de S1 a S3 doit emprunter S2 pour un cout de 12.
     */
    @Test
    public void testPlusCourtCheminDuree() {
        Itineraire it = recherche.plusCourtCheminDuree("S1", "S3");
        assertNotNull(it);
        assertEquals(12.0, it.getCout(), 0.0001);
        assertEquals(3, it.getIdSommets().size());
        assertEquals("S1", it.getIdSommets().get(0));
        assertEquals("S2", it.getIdSommets().get(1));
        assertEquals("S3", it.getIdSommets().get(2));
    }

    /**
     * Le chemin d'un sommet vers lui-meme a un cout nul.
     */
    @Test
    public void testPlusCourtCheminVersSoiMeme() {
        Itineraire it = recherche.plusCourtCheminDuree("S1", "S1");
        assertNotNull(it);
        assertEquals(0.0, it.getCout(), 0.0001);
    }

    /**
     * Vers un sommet inatteignable (isole), la recherche renvoie {@code null}.
     */
    @Test
    public void testPlusCourtCheminInatteignable() {
        assertNull(recherche.plusCourtCheminDuree("S1", "S4"));
    }

    /**
     * Un nom de centre inconnu provoque une {@link IllegalArgumentException}.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testPlusCourtCheminSommetInconnu() {
        recherche.plusCourtCheminDuree("S1", "Inconnu");
    }
}
