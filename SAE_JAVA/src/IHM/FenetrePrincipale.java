/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package IHM;

import Outils.AlgorithmeChristofide;
import Outils.DiviserGraphe;
import Structure.Graphe;
import Outils.Recherche;
import Outils.Glouton;
import Structure.Sommet;
import java.awt.BorderLayout;
import static java.awt.BorderLayout.EAST;
import static java.awt.BorderLayout.WEST;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.ui.graphicGraph.GraphicElement;
import org.graphstream.ui.view.Camera;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.view.util.DefaultMouseManager;

/**
 *
 * @author alexi
 */
public class FenetrePrincipale extends JFrame implements ActionListener {

    private GrapheVisuel grapheVisuel;
    private Graphe graphe;
    private JButton boutonChargerCarte;
    private JButton boutonGrapheAleatoire;
    private JButton boutonCalculerItineraire;
    private JButton boutonCalculerItineraireEstimee;
    private JButton boutonCalculerAlgoGlouton;
    private JButton boutonCalculerAlgoChristofide;
    private JButton boutonCalculerAlgo2Camions;
    private JButton boutonExporter1Camion;
    private JButton boutonExporter2Camion;
    private JPanel panneauGlobal;
    private Component grapheActuel;
    private JFileChooser fileChooser;
    private ArrayList<JCheckBox> listecb;
    private String[] strcb = {"Maternités", "Blocs opératoires", "Centres de nutrition",
        "Routes", "Colorier les routes dangereuses", "Distances", "Durée", "Fiabilité", "ID des sommets", "Type des sommets"};
    private JLabel labelM;
    private JLabel labelO;
    private JLabel labelN;
    private JComboBox depart;
    private JComboBox arrivee;
    private JComboBox filtreTypes;
    private JScrollPane panneauDeroulantListe;
    private JTextArea texte;
    private JTextArea texteAlgo;
    private JButton boutonViderCSV;

    /**
     * Constructeur de FenetrePrincipale
     *
     * @param nom nom de la fenetre
     */
    public FenetrePrincipale() {

        super("MEDMAP");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // fermeture
        this.setLocationRelativeTo(null); // Position à l’écran
        this.setVisible(true);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);

        initComposants();
        
        
        ImageIcon icon = new ImageIcon("Images/logo.png");
            this.setIconImage(icon.getImage());

        this.setVisible(true);

    }

    public void viderFichiersRendu() {
        try {
            File fichier1 = new File("rendu/" + "resultats1camionEquipe1Groupe3.csv");

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fichier1))) {
                writer.write("");
            }

            File fichier2 = new File("rendu/" + "resultats2camionsEquipe1Groupe3.csv");

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fichier2))) {
                writer.write("");
            }

        } catch (IOException ex) {
            System.out.println("IOException : Erreur lors de l'export du fichier.");
        }
    }

    /**
     * Génère les composants de la FenetrePrincipale
     */
    public void initComposants() {

        boutonChargerCarte = new JButton("Charger une carte");
        boutonGrapheAleatoire = new JButton("Créer un graphe aléatoire");
        boutonCalculerItineraire = new JButton("Calculer l'itinéraire");
        boutonCalculerItineraireEstimee = new JButton("Calculer l'itinéraire estimée");
        boutonCalculerAlgoGlouton = new JButton("Glouton");
        boutonCalculerAlgoChristofide = new JButton("Christofide 1 Camion");
        boutonCalculerAlgo2Camions = new JButton("Christofide 2 Camions");
        boutonExporter1Camion = new JButton("Exporter avec 1 camion");
        boutonExporter2Camion = new JButton("Exporter avec 2 camion");
        boutonViderCSV = new JButton("Vider les fichiers de rendu");
        panneauDeroulantListe = new JScrollPane();
        panneauGlobal = new JPanel();
        fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("./data"));
        listecb = new ArrayList<>();
        for (int i = 0; i < strcb.length; i++) {
            JCheckBox cb = new JCheckBox(strcb[i]);
            cb.setBackground(new Color(0x3C3C3C));
            cb.setForeground(Color.WHITE);
            cb.addActionListener(this);
            listecb.add(cb);
        }
        labelM = new JLabel("Nombre de maternités : 0");
        labelN = new JLabel("Nombre de centres de nutrition : 0");
        labelO = new JLabel("Nombre de blocs opératoires : 0");
        texte = new JTextArea();
        texte.setEditable(false);
        texte.setBackground(new Color(0xC2C2C2));
        texte.setForeground(Color.BLACK);
        texteAlgo = new JTextArea();
        texteAlgo.setEditable(false);
        texteAlgo.setBackground(new Color(0xC2C2C2));
        texteAlgo.setForeground(Color.BLACK);

        depart = new JComboBox();
        depart.setBackground(new Color(0xC2C2C2));
        depart.setForeground(Color.BLACK);
        arrivee = new JComboBox();
        arrivee.setBackground(new Color(0xC2C2C2));
        arrivee.setForeground(Color.BLACK);
        filtreTypes = new JComboBox();
        filtreTypes.setBackground(new Color(0xC2C2C2));
        filtreTypes.setForeground(Color.BLACK);
        filtreTypes.addItem("Tout les centres");
        filtreTypes.addItem("Blocs opératoires");
        filtreTypes.addItem("Centres de nutrition");
        filtreTypes.addItem("Maternités");

        boutonChargerCarte.addActionListener(this);
        boutonGrapheAleatoire.addActionListener(this);
        boutonCalculerItineraire.addActionListener(this);
        boutonCalculerItineraireEstimee.addActionListener(this);
        boutonCalculerAlgoGlouton.addActionListener(this);
        boutonCalculerAlgoChristofide.addActionListener(this);
        boutonCalculerAlgo2Camions.addActionListener(this);
        boutonExporter1Camion.addActionListener(this);
        boutonExporter2Camion.addActionListener(this);
        boutonViderCSV.addActionListener(this);

        this.setContentPane(panneauGlobal);

        panneauGlobal.setLayout(new BorderLayout());

        appliquerStyleBouton(boutonChargerCarte);
        appliquerStyleBouton(boutonGrapheAleatoire);
        appliquerStyleBouton(boutonCalculerItineraire);
        appliquerStyleBouton(boutonCalculerItineraireEstimee);
        appliquerStyleBouton(boutonCalculerAlgoGlouton);
        appliquerStyleBouton(boutonCalculerAlgoChristofide);
        appliquerStyleBouton(boutonCalculerAlgo2Camions);
        appliquerStyleBouton(boutonExporter1Camion);
        appliquerStyleBouton(boutonExporter2Camion);
        appliquerStyleBouton(boutonViderCSV);
        labelM.setForeground(Color.WHITE);
        labelN.setForeground(Color.WHITE);
        labelO.setForeground(Color.WHITE);

        JPanel panneauDeroulant = new JPanel();
        JPanel panneauDijkstra = new JPanel();
        JPanel vide = new JPanel();
        JSeparator separateur1 = new JSeparator();
        JSeparator separateur2 = new JSeparator();
        JSeparator separateur3 = new JSeparator();
        JSeparator separateur4 = new JSeparator();
        separateur1.setForeground(new Color(0xC2C2C2));
        separateur2.setForeground(new Color(0xC2C2C2));
        separateur3.setForeground(new Color(0xC2C2C2));
        separateur4.setForeground(new Color(0xC2C2C2));

        panneauDijkstra.setLayout(new GridBagLayout());
        GridBagConstraints gbDijkstra = new GridBagConstraints();
        gbDijkstra.insets = new java.awt.Insets(10, 10, 10, 10);
        gbDijkstra.fill = GridBagConstraints.HORIZONTAL;
        gbDijkstra.gridx = 0;
        gbDijkstra.gridy = 0;
        gbDijkstra.weightx = 1;
        panneauDijkstra.add(depart, gbDijkstra);
        gbDijkstra.gridx = 1;
        gbDijkstra.gridy = 0;
        panneauDijkstra.add(arrivee, gbDijkstra);
        gbDijkstra.gridwidth = 2;
        gbDijkstra.gridx = 0;
        gbDijkstra.gridy = 1;
        gbDijkstra.insets = new java.awt.Insets(10, 10, 0, 10);
        panneauDijkstra.add(boutonCalculerItineraire, gbDijkstra);
        gbDijkstra.gridx = 0;
        gbDijkstra.gridy = 2;
        panneauDijkstra.add(boutonCalculerItineraireEstimee, gbDijkstra);
        gbDijkstra.gridwidth = 2;
        gbDijkstra.gridx = 0;
        gbDijkstra.gridy = 3;
        panneauDijkstra.add(texte, gbDijkstra);

        panneauDeroulant.setLayout(new GridBagLayout());
        GridBagConstraints gb = new GridBagConstraints();
        gb.anchor = GridBagConstraints.NORTHWEST;
        gb.fill = GridBagConstraints.HORIZONTAL;

        gb.insets = new java.awt.Insets(20, 0, 5, 0);//padding
        gb.gridx = 0;
        gb.gridy = 0;
        gb.weighty = 0;
        panneauDeroulant.add(boutonChargerCarte, gb);

        gb.insets = new java.awt.Insets(5, 0, 25, 0);//padding
        gb.gridx = 0;
        gb.gridy += 1;
        panneauDeroulant.add(boutonGrapheAleatoire, gb);
        gb.gridx = 0;
        gb.gridy += 1;
        panneauDeroulant.add(separateur1, gb);

        for (int i = 0; i < listecb.size(); i++) {
            gb.gridy += 1;
            gb.insets = new java.awt.Insets(0, 0, 0, 0);//padding
            panneauDeroulant.add(listecb.get(i), gb);
        }

        gb.insets = new java.awt.Insets(25, 0, 0, 0);//padding
        gb.gridy += 1;
        panneauDeroulant.add(separateur2, gb);

        gb.gridy += 1;
        panneauDeroulant.add(labelM, gb);
        gb.insets = new java.awt.Insets(5, 0, 0, 0);//padding
        gb.gridy += 1;
        panneauDeroulant.add(labelN, gb);
        gb.gridy += 1;
        panneauDeroulant.add(labelO, gb);
        gb.insets = new java.awt.Insets(25, 0, 0, 0);//padding
        gb.gridy += 1;
        panneauDeroulant.add(separateur3, gb);
        gb.gridy += 1;
        panneauDeroulant.add(panneauDijkstra, gb);
        gb.insets = new java.awt.Insets(25, 0, 0, 0);//padding
        gb.gridy += 1;
        panneauDeroulant.add(separateur4, gb);

        gb.gridy += 1;
        panneauDeroulant.add(filtreTypes, gb);
        gb.insets = new java.awt.Insets(10, 0, 0, 0);//padding
        gb.gridy += 1;
        panneauDeroulant.add(boutonCalculerAlgoGlouton, gb);
        gb.gridy += 1;
        panneauDeroulant.add(boutonCalculerAlgoChristofide, gb);
        gb.gridy += 1;
        panneauDeroulant.add(boutonCalculerAlgo2Camions, gb);
        gb.gridy += 1;
        panneauDeroulant.add(boutonExporter1Camion, gb);
        gb.gridy += 1;
        panneauDeroulant.add(boutonExporter2Camion, gb);
        gb.insets = new java.awt.Insets(10, 0, 10, 0);//padding
        gb.gridy += 1;
        panneauDeroulant.add(texteAlgo, gb);
        gb.gridy += 1;
        panneauDeroulant.add(boutonViderCSV, gb);

        gb.fill = GridBagConstraints.BOTH;
        gb.weighty = 1;
        panneauDeroulant.add(vide, gb);

        panneauDeroulantListe.setViewportView(panneauDeroulant);
        panneauDeroulantListe.setBorder(null);

        panneauDeroulantListe.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        panneauDeroulantListe.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        panneauDeroulantListe.setPreferredSize(new Dimension(250, 0));
        panneauDeroulantListe.getVerticalScrollBar().setUnitIncrement(16);

        panneauGlobal.add(panneauDeroulantListe, EAST);

        vide.setBackground(new Color(0x3C3C3C));//Choix de la couleur de fond du panneau déroulant, il sera gris foncé
        panneauDeroulant.setBackground(new Color(0x3C3C3C));//Choix de la couleur de fond du panneau déroulant, il sera gris foncé
        panneauDijkstra.setBackground(new Color(0x3C3C3C));//Choix de la couleur de fond du panneau déroulant, il sera gris foncé

        this.setMinimumSize(new Dimension(1000, 500));
    }

    private void appliquerStyleBouton(JButton bouton) {
        bouton.setBackground(new Color(0x3C3C3C));
        bouton.setForeground(Color.WHITE);
        bouton.setFocusPainted(false);
    }

    public Graphe getGraphe() {
        return graphe;
    }

    public void afficherGraphe(GrapheVisuel g) {

        if (grapheActuel != null) {
            panneauGlobal.remove(grapheActuel);
        }

        Viewer viewer = new Viewer((Graph) g, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
        viewer.enableAutoLayout();
        View view = viewer.addDefaultView(false);   // false indicates "no JFrame".
        view.setMouseManager(null);

        //
        Component viewComponent = (Component) view;
        final Point pointDepart = new Point();
        Camera camera = view.getCamera();

        // 1. Écouteur pour le CLIC DROIT (Sélection de sommet)
        viewComponent.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (javax.swing.SwingUtilities.isRightMouseButton(e)) {
                    double x = e.getX();
                    double y = e.getY();

                    GraphicElement element = view.findNodeOrSpriteAt(x, y);
                    if (element != null) {
                        Node n = g.getNode(element.getId());
                        SommetClique sommetClique = new SommetClique(FenetrePrincipale.this, n, graphe);
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                // Si c'est un clic gauche, on enregistre le point de départ du déplacement
                if (SwingUtilities.isLeftMouseButton(e)) {
                    pointDepart.setLocation(e.getPoint());
                }
            }
        });

        // 2. Écouteur pour le glisser de souris (Mouvement dans le graphe)
        viewComponent.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    // Calcul de la distance parcourue par la souris en pixels
                    double deltaX = e.getX() - pointDepart.x;
                    double deltaY = e.getY() - pointDepart.y;

                    // Récupération du centre actuel de la caméra
                    org.graphstream.ui.geom.Point3 centreActuel = camera.getViewCenter();

                    // Facteur d'ajustement : plus on zoom (viewPercent petit), plus le déplacement doit être sensible
                    double facteur = 0.004 * camera.getViewPercent();

                    // Calcul du nouveau centre (GraphStream inverse l'axe Y par rapport à Swing)
                    double nouveauX = centreActuel.x - (deltaX * facteur);
                    double nouveauY = centreActuel.y + (deltaY * facteur);

                    // Appliquer le nouveau centre
                    camera.setViewCenter(nouveauX, nouveauY, 0);

                    // Mettre à jour le point de départ pour le prochain mouvement
                    pointDepart.setLocation(e.getPoint());
                }
            }
        });

        // 2. Écouteur spécifique pour la MOLETTE (ZOOM) -> C'est ça qui manquait !
        viewComponent.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {

                double currentPercent = camera.getViewPercent();

                // e.getWheelRotation() donne -1 pour un défilement vers le haut, 1 vers le bas
                if (e.getWheelRotation() < 0) {
                    // Zoom avant : on diminue le pourcentage de vue
                    camera.setViewPercent(Math.max(0.1, currentPercent - 0.05));
                } else {
                    // Zoom arrière : on augmente le pourcentage de vue
                    camera.setViewPercent(Math.min(5.0, currentPercent + 0.05));
                }
            }
        });
        //

        grapheActuel = viewComponent;
        panneauGlobal.add(viewComponent, BorderLayout.CENTER);

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
                graphe = new Graphe();
                graphe.genererGrapheAleatoire(nbSommets);
                grapheVisuel = new GrapheVisuel("nom du graphe", graphe.getTabS(), graphe.getTabR());
                grapheVisuel.setAttribute("ui.stylesheet", "url('css/styleGraphe.css')");
                afficherGraphe(grapheVisuel);
                int nbrM = 0;
                int nbrN = 0;
                int nbrO = 0;
                depart.removeAllItems();
                arrivee.removeAllItems();
                for (Node sommet : grapheVisuel.getEachNode()) {
                    if (sommet.getAttribute("type").toString().equals("M")) {
                        nbrM++;
                    } else if (sommet.getAttribute("type").toString().equals("N")) {
                        nbrN++;
                    } else if (sommet.getAttribute("type").toString().equals("O")) {
                        nbrO++;
                    }

                    depart.addItem(sommet.getAttribute("id"));
                    arrivee.addItem(sommet.getAttribute("id"));
                }
                labelM.setText("Nombre de maternités : " + nbrM);
                labelN.setText("Nombre de centres de nutrition : " + nbrN);
                labelO.setText("Nombre de blocs opératoires : " + nbrO);
                for (int i = 0; i < 4; i++) {
                    listecb.get(i).setSelected(true);
                }
                for (int i = 4; i < 8; i++) {
                    listecb.get(i).setSelected(false);
                }
                listecb.get(8).setSelected(true);
                listecb.get(9).setSelected(true);
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

                    graphe = new Graphe();
                    graphe.chargerGraphes(nomFichier);
                    grapheVisuel = new GrapheVisuel("Graphe charger", graphe.getTabS(), graphe.getTabR());
                    grapheVisuel.setAttribute("ui.stylesheet", "url('css/styleGraphe.css')");
                    afficherGraphe(grapheVisuel);
                    int nbrM = 0;
                    int nbrN = 0;
                    int nbrO = 0;
                    depart.removeAllItems();
                    arrivee.removeAllItems();
                    for (Node sommet : grapheVisuel.getEachNode()) {
                        if (sommet.getAttribute("type").toString().equals("M")) {
                            nbrM++;
                        } else if (sommet.getAttribute("type").toString().equals("N")) {
                            nbrN++;
                        } else if (sommet.getAttribute("type").toString().equals("O")) {
                            nbrO++;
                        }

                        depart.addItem(sommet.getAttribute("id"));
                        arrivee.addItem(sommet.getAttribute("id"));
                    }
                    labelM.setText("Nombre de maternités : " + nbrM);
                    labelN.setText("Nombre de centres de nutrition : " + nbrN);
                    labelO.setText("Nombre de blocs opératoires : " + nbrO);
                    for (int i = 0; i < 4; i++) {
                        listecb.get(i).setSelected(true);
                    }
                    for (int i = 4; i < 8; i++) {
                        listecb.get(i).setSelected(false);
                    }
                    listecb.get(8).setSelected(true);
                    listecb.get(9).setSelected(true);
                }
            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(panneauGlobal,
                        "le fichier est introuvable",
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
        if (e.getSource() == listecb.get(0)) {
            if (!listecb.get(0).isSelected()) {
                for (Node sommet : grapheVisuel.getEachNode()) {
                    String uiClass = sommet.getAttribute("ui.class");

                    if ("maternite".equals(uiClass)) {
                        sommet.setAttribute("ui.hide");
                    }
                }
            } else {
                for (Node sommet : grapheVisuel.getEachNode()) {
                    String uiClass = sommet.getAttribute("ui.class");

                    if ("maternite".equals(uiClass)) {
                        sommet.removeAttribute("ui.hide");
                    }
                }
            }
        }
        if (e.getSource() == listecb.get(1)) {
            if (!listecb.get(1).isSelected()) {
                for (Node sommet : grapheVisuel.getEachNode()) {
                    String uiClass = sommet.getAttribute("ui.class");

                    if ("blocOperatoire".equals(uiClass)) {
                        sommet.setAttribute("ui.hide");
                    }
                }
            } else {
                for (Node sommet : grapheVisuel.getEachNode()) {
                    String uiClass = sommet.getAttribute("ui.class");

                    if ("blocOperatoire".equals(uiClass)) {
                        sommet.removeAttribute("ui.hide");
                    }
                }
            }
        }
        if (e.getSource() == listecb.get(2)) {
            if (!listecb.get(2).isSelected()) {
                for (Node sommet : grapheVisuel.getEachNode()) {
                    String uiClass = sommet.getAttribute("ui.class");

                    if ("nutrition".equals(uiClass)) {
                        sommet.setAttribute("ui.hide");
                    }
                }
            } else {
                for (Node sommet : grapheVisuel.getEachNode()) {
                    String uiClass = sommet.getAttribute("ui.class");

                    if ("nutrition".equals(uiClass)) {
                        sommet.removeAttribute("ui.hide");
                    }
                }
            }
        }
        if (e.getSource() == listecb.get(3)) {
            if (!listecb.get(3).isSelected()) {
                for (Edge route : grapheVisuel.getEachEdge()) {
                    route.setAttribute("ui.hide");
                }
            } else {
                for (Edge route : grapheVisuel.getEachEdge()) {
                    route.removeAttribute("ui.hide");
                }
            }
        }
        if (e.getSource() == listecb.get(4)) {
            if (listecb.get(4).isSelected()) {
                String saisie = JOptionPane.showInputDialog(panneauGlobal,
                        "En dessous de quel probabilité sur 10 considerez vous une route comme dangereuse ?",
                        "Affichage des routes dangereuses",
                        JOptionPane.PLAIN_MESSAGE);
                if (saisie != null && !saisie.trim().isEmpty()) {
                    try {
                        int danger = Integer.parseInt(saisie);

                        for (Edge route : grapheVisuel.getEachEdge()) {

                            Object attr = route.getAttribute("fiabilite");//Conseiller par l'IA pour récuperer la fiabilité et ne marche pas sans.

                            if (attr instanceof Number) {
                                int fiabilite = ((Number) attr).intValue();

                                if (fiabilite < danger) {
                                    route.setAttribute("ui.class", "danger");
                                } else {
                                    route.removeAttribute("ui.class");
                                }
                            }
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(panneauGlobal, "Veuillez entrer un nombre valide.", "Erreur", JOptionPane.ERROR_MESSAGE);
                        listecb.get(4).setSelected(false);
                    }
                } else {
                    listecb.get(4).setSelected(false);
                }
            } else {
                for (Edge route : grapheVisuel.getEachEdge()) {
                    route.removeAttribute("ui.class");
                }
            }
        }
        if (e.getSource() == listecb.get(5)) {
            listecb.get(7).setSelected(false);
            listecb.get(6).setSelected(false);
            if (listecb.get(5).isSelected()) {
                for (Edge route : grapheVisuel.getEachEdge()) {
                    String ditance = route.getAttribute("distance").toString();
                    route.setAttribute("ui.label", ditance + "km");
                }
            } else {
                for (Edge route : grapheVisuel.getEachEdge()) {
                    route.removeAttribute("ui.label");
                }
            }
        }
        if (e.getSource() == listecb.get(6)) {
            listecb.get(7).setSelected(false);
            listecb.get(5).setSelected(false);
            if (listecb.get(6).isSelected()) {
                for (Edge route : grapheVisuel.getEachEdge()) {
                    String duree = route.getAttribute("duree").toString();
                    route.setAttribute("ui.label", duree + "mins");
                }
            } else {
                for (Edge route : grapheVisuel.getEachEdge()) {
                    route.removeAttribute("ui.label");
                }
            }
        }
        if (e.getSource() == listecb.get(7)) {
            listecb.get(5).setSelected(false);
            listecb.get(6).setSelected(false);
            if (listecb.get(7).isSelected()) {
                for (Edge route : grapheVisuel.getEachEdge()) {
                    String fiabilite = route.getAttribute("fiabilite").toString();
                    route.setAttribute("ui.label", fiabilite + "/10");
                }
            } else {
                for (Edge route : grapheVisuel.getEachEdge()) {
                    route.removeAttribute("ui.label");
                }
            }
        }
        if (e.getSource() == listecb.get(8) || e.getSource() == listecb.get(9)) {
            if (listecb.get(8).isSelected() && listecb.get(9).isSelected()) {
                for (Node sommet : grapheVisuel.getEachNode()) {
                    String id = sommet.getAttribute("id").toString();
                    String type = sommet.getAttribute("type").toString();
                    sommet.setAttribute("ui.label", id + " (" + type + ")");
                }
            } else if (listecb.get(8).isSelected()) {
                for (Node sommet : grapheVisuel.getEachNode()) {
                    String id = sommet.getAttribute("id").toString();
                    sommet.setAttribute("ui.label", id);
                }
            } else if (listecb.get(9).isSelected()) {
                for (Node sommet : grapheVisuel.getEachNode()) {
                    String type = sommet.getAttribute("type").toString();
                    sommet.setAttribute("ui.label", "(" + type + ")");
                }
            } else {
                for (Node sommet : grapheVisuel.getEachNode()) {
                    sommet.removeAttribute("ui.label");
                }
            }
        }
        if (e.getSource() == boutonCalculerItineraire) {
            for (Edge route : grapheVisuel.getEachEdge()) {
                route.addAttribute("ui.class", "safe");
            }
            Recherche recherche = new Recherche(grapheVisuel);
            String res = "Le plus court chemin : \n\n";
            ArrayList<String> strListe = recherche.plusCourtCheminDuree(depart.getSelectedItem().toString(), arrivee.getSelectedItem().toString()).getIdSommets();
            for (int i = 0; i < strListe.size() - 1; i++) {
                res = res + strListe.get(i) + "\n";
                Node SommetDepart = grapheVisuel.getNode(strListe.get(i));
                Node SommetArivee = grapheVisuel.getNode(strListe.get(i + 1));
                Edge route = SommetDepart.getEdgeBetween(SommetArivee);
                route.addAttribute("ui.class", "selectionnerPCC");
            }
            res = res + strListe.get(strListe.size() - 1) + "\n";
            String cout = String.valueOf(Math.round(recherche.plusCourtCheminDuree(depart.getSelectedItem().toString(), arrivee.getSelectedItem().toString()).getCout()));
            texte.setText(res + "\n Ce trajet prend : " + cout + " min");
        }
        if (e.getSource() == boutonCalculerItineraireEstimee) {
            for (Edge route : grapheVisuel.getEachEdge()) {
                route.addAttribute("ui.class", "safe");
            }
            Recherche recherche = new Recherche(grapheVisuel);
            String res = "Le plus court chemin : \n\n";
            ArrayList<String> strListe = recherche.plusCourtCheminDureeEstimee(depart.getSelectedItem().toString(), arrivee.getSelectedItem().toString()).getIdSommets();
            for (int i = 0; i < strListe.size() - 1; i++) {
                res = res + strListe.get(i) + "\n";
                Node SommetDepart = grapheVisuel.getNode(strListe.get(i));
                Node SommetArivee = grapheVisuel.getNode(strListe.get(i + 1));
                Edge route = SommetDepart.getEdgeBetween(SommetArivee);
                route.addAttribute("ui.class", "selectionnerPCC");
            }
            res = res + strListe.get(strListe.size() - 1) + "\n";
            String cout = String.valueOf(Math.round(recherche.plusCourtCheminDureeEstimee(depart.getSelectedItem().toString(), arrivee.getSelectedItem().toString()).getCout()));
            texte.setText(res + "\n Ce trajet prend : " + cout + " min");
        }
        if (e.getSource() == boutonCalculerAlgoGlouton) {
            String[] resultatTotal = lancerGlouton();
            texteAlgo.setText(resultatTotal[0]+"Durée total du trajet : "+resultatTotal[1]+ "min");
        }
        if (e.getSource() == boutonCalculerAlgoChristofide) {
            String texteResultat = "Résultat Christofide : \n" + lancerChristofide(graphe)[0];
            texteAlgo.setText(texteResultat);
        }
        if (e.getSource() == boutonCalculerAlgo2Camions) {
            filtreTypes.setSelectedItem("Tout les centres");
            Graphe[] resultats = DiviserGraphe.diviserEnDeux(graphe);
            Graphe grapheCamion1 = resultats[0];
            Graphe grapheCamion2 = resultats[1];
            String texteResultat = "Résultat camion 1 : \n" + lancerChristofide(grapheCamion1)[0];
            texteResultat = texteResultat + "\n\nRésultat camion 2 : \n" + lancerChristofide(grapheCamion2)[0];
            texteAlgo.setText(texteResultat);
        }
        if (e.getSource() == boutonExporter1Camion) {
            String dureeGlouton =  lancerGlouton()[1];
            String dureeChristofide;
            String nomGraphe = graphe.getNom();
            String toutIDSommets;
            String ligneEcrite;

            String[] resultatsChristofide = lancerChristofide(graphe);

            toutIDSommets = resultatsChristofide[1];
            dureeChristofide = resultatsChristofide[2];
            ligneEcrite = nomGraphe + ";" + dureeGlouton + ";" + dureeChristofide + toutIDSommets + "\n";
            try {
                File fichier = new File("rendu/" + "resultats1camionEquipe1Groupe3.csv");

                try (BufferedWriter writer = new BufferedWriter(new FileWriter(fichier, true))) {
                    writer.write(ligneEcrite);
                }

            } catch (IOException ex) {
                System.out.println("IOException : Erreur lors de l'export du fichier.");
            }
        }
        if (e.getSource() == boutonExporter2Camion) {
            filtreTypes.setSelectedItem("Tout les centres");
            String dureeCamion1;
            String dureeCamion2;
            String nomGraphe = graphe.getNom();
            String toutIDSommets;
            String ligneEcrite;

            Graphe[] resultats = DiviserGraphe.diviserEnDeux(graphe);
            Graphe grapheCamion1 = resultats[0];
            Graphe grapheCamion2 = resultats[1];

            String[] resultatsChristofide1 = lancerChristofide(grapheCamion1);
            String[] resultatsChristofide2 = lancerChristofide(grapheCamion2);

            toutIDSommets = resultatsChristofide1[1] + resultatsChristofide2[1];
            dureeCamion1 = resultatsChristofide1[2];
            dureeCamion2 = resultatsChristofide2[2];
            ligneEcrite = nomGraphe + ";" + dureeCamion1 + ";" + dureeCamion2 + toutIDSommets + "\n";
            try {
                File fichier = new File("rendu/" + "resultats2camionsEquipe1Groupe3.csv");

                try (BufferedWriter writer = new BufferedWriter(new FileWriter(fichier, true))) {
                    writer.write(ligneEcrite);
                }

            } catch (IOException ex) {
                System.out.println("IOException : Erreur lors de l'export du fichier.");
            }
        }
        if (e.getSource() == boutonViderCSV) {
            viderFichiersRendu();
            texteAlgo.setText("Fichiers de rendu vidées avec succès");
            
        }
    }

    private String[] lancerChristofide(Graphe graphe) {
        List<String> typesChoisis = new ArrayList<>();
        switch (filtreTypes.getSelectedItem().toString()) {
            case "Tout les centres":
                typesChoisis.add("O");
                typesChoisis.add("M");
                typesChoisis.add("N");
                break;
            case "Blocs opératoires":
                typesChoisis.add("O");
                break;
            case "Centres de nutrition":
                typesChoisis.add("N");
                break;
            case "Maternités":
                typesChoisis.add("M");
                break;
        }
        AlgorithmeChristofide ac = new AlgorithmeChristofide(grapheVisuel, graphe, typesChoisis);
        List<String> lst = ac.executerChristofide();
        String resultatTexte = new String("");
        String resultatCSV = new String("");
        for (int i = 0; i < lst.size(); i++) {
            resultatTexte = resultatTexte + lst.get(i) + "\n";
            resultatCSV = resultatCSV + ";" + lst.get(i).substring(1);
        }
        String cout = "" + Math.round(ac.getDuree());
        resultatTexte = resultatTexte + "\n Ce trajet prend : " + cout + " min";
        String[] resultatRetourner = {resultatTexte, resultatCSV, cout};
        return resultatRetourner;
    }
    
    private String[] lancerGlouton() {
        Recherche recherche = new Recherche(grapheVisuel);
            Glouton glouton = new Glouton(recherche);
            String[] str = new String[1];
            switch (filtreTypes.getSelectedItem().toString()) {
                 case "Blocs opératoires":
                    str[0] = "O";
                    break;
                case "Centres de nutrition":
                    str[0] = "N";
                    break;
                case "Maternités":
                    str[0] = "M";
                    break;
                default : 
                    str = null;
                    break;
            }
            
            List<Sommet> lst = glouton.unCamionParType(graphe.getTabS(), graphe.getSommet(0),str);
            String resultatTexte = "";
            
            for (int i = 0; i < lst.size(); i++) {
                resultatTexte = resultatTexte + lst.get(i).getNom() + "\n";
            }
            String cout = ""+Math.round(glouton.getCoutTotalTrajet());
            String[] resultatRetourner = {resultatTexte, cout};
            return resultatRetourner;
    }
}
