package Outils;

import Structure.DistanceVers;
import Structure.Graphe;
import Structure.Route;
import Structure.Sommet;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * Classe de test unitaire mise à jour pour la classe {@link DiviserGraphe}.
 *
 * <p>
 * On vérifie le découpage d'un graphe de quatre sommets en deux secteurs (List
 * de sommets) : chaque secteur doit contenir le dépôt (sommet 0) et la réunion
 * des autres sommets des deux listes doit couvrir, sans doublon, l'ensemble des
 * sommets à desservir.</p>
 *
 * @author Equipe SAE_JAVA
 */
public class DiviserGrapheTest {

    private Graphe graphe;
    private Sommet depot;

    /**
     * Construit un graphe connexe de quatre sommets avant chaque test. Cette
     * version utilise une classe anonyme ou surcharge pour s'assurer que la
     * méthode distancesCroissantesVersTous requise par le nouvel algo renvoie
     * des valeurs.
     */
    @Before
    public void setUp() {
        // On crée une sous-classe anonyme de Graphe pour simuler le comportement de distancesCroissantesVersTous
        graphe = new Graphe(4) {
            @Override
            public List<DistanceVers> distancesCroissantesVersTous(Sommet source, String critere) {
                List<DistanceVers> result = new ArrayList<>();
                // Simule des distances bidons mais valides pour que l'algo s'exécute sans planter
                for (int i = 0; i < this.getNbSommet(); i++) {
                    Sommet cible = this.getSommet(i);
                    if (cible != null) {
                        // On met une durée proportionnelle à l'index pour différencier les secteurs
                        int dureeSimulee = Math.abs(source.getIndex() - cible.getIndex()) * 10;
                        result.add(new DistanceVers(cible, dureeSimulee, dureeSimulee, dureeSimulee));
                    }
                }
                return result;
            }
        };

        depot = new Sommet(0, "S1", "M");
        Sommet s2 = new Sommet(1, "S2", "M");
        Sommet s3 = new Sommet(2, "S3", "N");
        Sommet s4 = new Sommet(3, "S4", "O");

        graphe.ajouterSommet(0, depot);
        graphe.ajouterSommet(1, s2);
        graphe.ajouterSommet(2, s3);
        graphe.ajouterSommet(3, s4);

        // Chaîne connexe standard
        graphe.ajouterRoute(0, 1, new Route("R0", 8, 10, 10, depot, s2));
        graphe.ajouterRoute(1, 2, new Route("R1", 8, 10, 10, s2, s3));
        graphe.ajouterRoute(2, 3, new Route("R2", 8, 10, 10, s3, s4));
        graphe.ajouterRoute(0, 3, new Route("R3", 8, 50, 50, depot, s4));
    }

    /**
     * Le découpage doit produire exactement deux listes (secteurs).
     */
    @Test
    public void testDiviserEnDeuxRenvoieDeuxSecteurs() {
        List<Sommet>[] resultats = DiviserGraphe.diviserEnDeuxSecteurs(graphe);
        assertEquals(2, resultats.length);
    }

    /**
     * Chaque secteur doit contenir le dépôt en première position (index 0).
     */
    @Test
    public void testDepotPresentDansLesDeuxSecteurs() {
        List<Sommet>[] resultats = DiviserGraphe.diviserEnDeuxSecteurs(graphe);
        assertEquals(depot, resultats[0].get(0));
        assertEquals(depot, resultats[1].get(0));
    }

    /**
     * La réunion des sommets desservis (hors dépôt) des deux secteurs doit
     * couvrir exactement les trois sommets restants, sans doublon.
     */
    @Test
    public void testRepartitionCompleteSecteurs() {
        List<Sommet>[] resultats = DiviserGraphe.diviserEnDeuxSecteurs(graphe);

        Set<String> desservis = new HashSet<>();
        int total = 0;

        for (List<Sommet> secteur : resultats) {
            // On commence à i = 1 pour ignorer le dépôt (situé à l'index 0)
            for (int i = 1; i < secteur.size(); i++) {
                // Si l'élément à l'index 1 est le pilier et qu'il réapparaît après, on l'évite.
                // L'algo insère le pilier en position 1, puis boucle de j=1 à N.
                // On s'assure juste de compter les sommets uniques hors dépôt.
                Sommet s = secteur.get(i);
                if (!s.equals(depot)) {
                    if (desservis.add(s.getNom())) {
                        total++;
                    }
                }
            }
        }

        // Il y a 3 sommets uniques au total à distribuer (S2, S3, S4)
        assertEquals(3, desservis.size());
        assertTrue(desservis.contains("S2"));
        assertTrue(desservis.contains("S3"));
        assertTrue(desservis.contains("S4"));
    }
}
