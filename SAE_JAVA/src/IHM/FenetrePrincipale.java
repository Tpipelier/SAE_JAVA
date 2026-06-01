/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package IHM;

import java.awt.BorderLayout;
import static java.awt.BorderLayout.WEST;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.graphstream.graph.Graph;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.Viewer;
import sae_java.Graphe;
import sae_java.GrapheVisuel;

/**
 *
 * @author alexi
 */
public class FenetrePrincipale extends JFrame implements ActionListener {

    private JButton boutonChargerCarte;
    private JButton boutonGrapheAleatoire;
    private JButton boutonRavitailler;
    private JButton boutonNotifications;
    private JPanel panneauGlobal;

    /**
     * Constructeur de FenetrePrincipale
     *
     * @param nom nom de la fenetre
     */
    public FenetrePrincipale(String nom) {

        super(nom);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // fermeture
        this.setLocationRelativeTo(null); // Position à l’écran
        this.setVisible(true);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);

        initComposants();

        this.setVisible(true);

    }

    /**
     * Génère les composants de la FenetrePrincipale
     */
    public void initComposants() {

        boutonChargerCarte = new JButton("Charger une carte");
        boutonGrapheAleatoire = new JButton("Créer un graphe aléatoire");
        boutonRavitailler = new JButton("Ravitailler");
        boutonNotifications = new JButton("Notifications");
        panneauGlobal = new JPanel();

        boutonGrapheAleatoire.addActionListener(this);

        this.setContentPane(panneauGlobal);
        panneauGlobal.setLayout(new BorderLayout());

        appliquerStyleBouton(boutonChargerCarte);
        appliquerStyleBouton(boutonGrapheAleatoire);
        appliquerStyleBouton(boutonRavitailler);
        appliquerStyleBouton(boutonNotifications);

        JPanel panneauDeroulant = new JPanel();
        JPanel vide = new JPanel();

        panneauDeroulant.setLayout(new GridBagLayout());
        GridBagConstraints gb = new GridBagConstraints();
        gb.anchor = GridBagConstraints.NORTHWEST;

        gb.gridx = 0;
        gb.gridy = 0;
        gb.weighty = 0;
        panneauDeroulant.add(boutonChargerCarte, gb);
        gb.gridx = 0;
        gb.gridy = 1;
        panneauDeroulant.add(boutonGrapheAleatoire, gb);
        gb.gridx = 0;
        gb.gridy = 2;
        panneauDeroulant.add(boutonRavitailler, gb);
        gb.gridx = 0;
        gb.gridy = 3;
        gb.weighty = 1;
        gb.fill = GridBagConstraints.BOTH;
        panneauDeroulant.add(vide, gb);
        gb.gridx = 0;
        gb.gridy = 4;
        gb.weighty = 0;
        panneauDeroulant.add(boutonNotifications, gb);

        panneauDeroulant.setPreferredSize(new Dimension(200, 0));
        panneauGlobal.add(panneauDeroulant, WEST);

        vide.setBackground(new Color(0x3C3C3C));//Choix de la couleur de fond du panneau déroulant, il sera gris foncé
        panneauDeroulant.setBackground(new Color(0x3C3C3C));//Choix de la couleur de fond du panneau déroulant, il sera gris foncé

    }

    private void appliquerStyleBouton(JButton bouton) {
        bouton.setBackground(new Color(0x3C3C3C));
        bouton.setForeground(Color.WHITE);
        bouton.setFocusPainted(false);
    }

    public void afficherGraphe(GrapheVisuel g) {

        Viewer viewer = new Viewer((Graph) g, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
        viewer.enableAutoLayout();
        View view = viewer.addDefaultView(false);   // false indicates "no JFrame".
        panneauGlobal.add((Component) view, BorderLayout.CENTER);

        panneauGlobal.revalidate(); // Recalcule la mise en page
        panneauGlobal.repaint();    // Redessine les composants
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == boutonGrapheAleatoire) {
            try {
                int nbSommets = Integer.parseInt(JOptionPane.showInputDialog(panneauGlobal,
                        "Combien de sommets voulez vous sur votre graphe ?",
                        "Création d'un graphe aléatoire",
                        JOptionPane.PLAIN_MESSAGE));
                Graphe g;
                g = new Graphe();
                g.genererGrapheAleatoire(nbSommets);
                GrapheVisuel grapheVisuel = new GrapheVisuel("nom du graphe", g.getTabS(), g.getTabR());
                afficherGraphe(grapheVisuel);
            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(panneauGlobal,
                        "le texte entrée est invalide",
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panneauGlobal,
                        "le texte entrée est invalide",
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
