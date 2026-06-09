/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package IHM;

import Structure.Graphe;
import java.awt.BorderLayout;
import static java.awt.BorderLayout.EAST;
import static java.awt.BorderLayout.WEST;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import org.graphstream.graph.Graph;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.view.util.DefaultMouseManager;

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
    private Component grapheActuel;
    private JFileChooser fileChooser;
    private ArrayList<JCheckBox> listecb;
    private String[] strcb = {"Maternités","Blocs opératoires","Centres de nutrition","Camions"
            ,"Routes","Colorier les routes dangereuses","Distances","Fiabilité","ID des sommets","Type des sommets"};
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
        fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("./data"));
        listecb = new ArrayList<>();
        for(int i = 0;i< strcb.length;i++){
            JCheckBox cb = new JCheckBox(strcb[i]);
            cb.setBackground(new Color(0x3C3C3C));
            cb.setForeground(Color.WHITE);
            listecb.add(cb);
        }
        
        

        boutonChargerCarte.addActionListener(
                this);
        boutonGrapheAleatoire.addActionListener(
                this);

        this.setContentPane(panneauGlobal);

        panneauGlobal.setLayout(
                new BorderLayout());

        appliquerStyleBouton(boutonChargerCarte);

        appliquerStyleBouton(boutonGrapheAleatoire);

        appliquerStyleBouton(boutonRavitailler);

        appliquerStyleBouton(boutonNotifications);

        JPanel panneauDeroulant = new JPanel();
        JPanel vide = new JPanel();

        panneauDeroulant.setLayout(new GridBagLayout());
        GridBagConstraints gb = new GridBagConstraints();
        gb.anchor = GridBagConstraints.NORTHWEST;
        gb.fill = GridBagConstraints.HORIZONTAL;
        
        gb.insets = new java.awt.Insets(20, 0, 5, 0);//padding
        gb.gridx = 0;
        gb.gridy = 0;
        gb.weighty = 0;
        panneauDeroulant.add(boutonChargerCarte, gb);
        
        gb.insets = new java.awt.Insets(5, 0, 5, 0);//padding
        gb.gridx = 0;
        gb.gridy += 1;
        panneauDeroulant.add(boutonGrapheAleatoire, gb);
        
        gb.insets = new java.awt.Insets(5,0,20,0);//padding
        gb.gridx = 0;
        gb.gridy += 1;
        panneauDeroulant.add(boutonRavitailler, gb);
        
        for(int i = 0;i< listecb.size();i++){
            gb.gridy += 1;
            gb.insets = new java.awt.Insets(0,0,0,0);//padding
            panneauDeroulant.add(listecb.get(i),gb);
        }
        
        
        gb.gridx = 0;
        gb.gridy += 1;
        gb.weighty = 1;
        gb.fill = GridBagConstraints.BOTH;
        panneauDeroulant.add(vide, gb);
        
        gb.insets = new java.awt.Insets(5,0,20,0);//padding
        gb.gridx = 0;
        gb.gridy += 1;
        gb.weighty = 0;
        panneauDeroulant.add(boutonNotifications, gb);

        panneauDeroulant.setPreferredSize(new Dimension(250, 0));
        panneauGlobal.add(panneauDeroulant, EAST);

        vide.setBackground(new Color(0x3C3C3C));//Choix de la couleur de fond du panneau déroulant, il sera gris foncé
        panneauDeroulant.setBackground(new Color(0x3C3C3C));//Choix de la couleur de fond du panneau déroulant, il sera gris foncé

        this.setMinimumSize(new Dimension(1000,500));
    }

    private void appliquerStyleBouton(JButton bouton) {
        bouton.setBackground(new Color(0x3C3C3C));
        bouton.setForeground(Color.WHITE);
        bouton.setFocusPainted(false);
    }

    public void afficherGraphe(GrapheVisuel g) {

        if (grapheActuel != null) {
            panneauGlobal.remove(grapheActuel);
        }

        Viewer viewer = new Viewer((Graph) g, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
        viewer.enableAutoLayout();
        View view = viewer.addDefaultView(false);   // false indicates "no JFrame".

        grapheActuel = (Component) view;
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
                grapheVisuel.setAttribute("ui.stylesheet", "url('css/styleGraphe.css')");
                afficherGraphe(grapheVisuel);
            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(panneauGlobal,
                        "le ficchier est introuvable",
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panneauGlobal,
                        "le texte entrée est invalide",
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
        if (e.getSource() == boutonChargerCarte) {
            try {

                int resultat = fileChooser.showOpenDialog(this);

                if (resultat == JFileChooser.APPROVE_OPTION) {
                    String nomFichier = fileChooser.getSelectedFile().getAbsolutePath();

                    Graphe g;
                    g = new Graphe();
                    g.chargerGraphes(nomFichier);
                    GrapheVisuel grapheVisuel = new GrapheVisuel("nom du graphe", g.getTabS(), g.getTabR());
                    grapheVisuel.setAttribute("ui.stylesheet", "url('css/styleGraphe.css')");
                    afficherGraphe(grapheVisuel);
                }
            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(panneauGlobal,
                        "le ficchier est introuvable",
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
