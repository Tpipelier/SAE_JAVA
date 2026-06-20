/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package IHM;

import Outils.AlgorithmeChristofide;
import Outils.DiviserGraphe;
import Structure.Graphe;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author alexi
 */
public class FenetreAlgorithmes extends JDialog implements ActionListener {

    private Graphe graphe;
    private GrapheVisuel grapheVisuel;
    private JComboBox cbAlgo;
    private JComboBox cbNombreCamions;
    private JComboBox cbTypeSommets;
    private JButton btCalculerAlgo;
    private JButton btExporterCSV;
    private JTextField tfExporterCSV;
    private JLabel labelExporterCSV;
    private JTextArea texte;
    private String strExportCSV;

    public FenetreAlgorithmes(FenetrePrincipale f, GrapheVisuel grapheVisuel, Graphe graphe) {
        super(f, "Algorithmes");
        this.graphe = graphe;
        this.grapheVisuel = grapheVisuel;

        this.setLocationRelativeTo(f);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.initComposants();
        this.setVisible(true);
    }

    private void initComposants() {
        cbAlgo = new JComboBox();
        cbNombreCamions = new JComboBox();
        cbTypeSommets = new JComboBox();
        btCalculerAlgo = new JButton("Calculer");
        btExporterCSV = new JButton("Exporter les résultats");
        tfExporterCSV = new JTextField(10);
        labelExporterCSV = new JLabel(".csv");
        texte = new JTextArea(2, 40);

        btCalculerAlgo.addActionListener(this);
        btExporterCSV.addActionListener(this);

        cbAlgo.addItem("Glouton");
        cbAlgo.addItem("Christofide");
        cbNombreCamions.addItem("1 Camion");
        cbNombreCamions.addItem("2 Camions");
        cbTypeSommets.addItem("Tout les centres");
        cbTypeSommets.addItem("Blocs opératoires");
        cbTypeSommets.addItem("Centres de nutritions");
        cbTypeSommets.addItem("Maternités");

        tfExporterCSV.setText("Resultat");

        JPanel panneauGlobal = new JPanel();
        this.setContentPane(panneauGlobal);

        panneauGlobal.setLayout(new GridBagLayout());
        GridBagConstraints gb = new GridBagConstraints();
        gb.anchor = GridBagConstraints.NORTHWEST;
        gb.fill = GridBagConstraints.HORIZONTAL;
        gb.insets = new java.awt.Insets(10, 5, 10, 5);//padding
        gb.gridx = 0;
        gb.gridy = 0;
        panneauGlobal.add(cbAlgo, gb);
        gb.gridx = 1;
        panneauGlobal.add(cbNombreCamions, gb);
        gb.gridx = 2;
        panneauGlobal.add(cbTypeSommets, gb);
        gb.gridx = 3;
        panneauGlobal.add(btCalculerAlgo, gb);
        gb.gridx = 0;
        gb.gridy = 1;
        gb.gridwidth = 4;
        panneauGlobal.add(texte, gb);
        gb.gridwidth = 1;
        gb.gridy = 2;
        panneauGlobal.add(btExporterCSV, gb);
        gb.gridwidth = 2;
        gb.gridx = 1;
        panneauGlobal.add(tfExporterCSV, gb);
        gb.gridwidth = 1;
        gb.gridx = 3;
        panneauGlobal.add(labelExporterCSV, gb);
        this.pack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btCalculerAlgo) {
            if (cbAlgo.getSelectedItem().equals("Christofide")) {
                String res;
                if (cbNombreCamions.getSelectedItem().equals("1 Camion")) {
                    List<String> typesChoisis = new ArrayList<>();
                    typesChoisis.add("O");
                    typesChoisis.add("M");
                    typesChoisis.add("N");
                    String[] resultatsChristofide = lancerChristofide(graphe,typesChoisis);
                    res = "Trajet du camion : " + resultatsChristofide[0];
                    strExportCSV = resultatsChristofide[2]+";"+resultatsChristofide[1];
                } else {
                    Graphe[] resultats = DiviserGraphe.diviserEnDeux(graphe);
                    Graphe grapheCamion1 = resultats[0];
                    Graphe grapheCamion2 = resultats[1];
                    List<String> typesChoisis = new ArrayList<>();
                    typesChoisis.add("O");
                    typesChoisis.add("M");
                    typesChoisis.add("N");
                    String[] resultatsChristofide1 = lancerChristofide(grapheCamion1,typesChoisis);
                    String[] resultatsChristofide2 = lancerChristofide(grapheCamion2,typesChoisis);
                    res = resultatsChristofide1[0];
                    strExportCSV = resultatsChristofide1[1];
                    res = res + "\n\n" + resultatsChristofide2[0];
                    strExportCSV = strExportCSV + ";" + resultatsChristofide2[1];
                }
                texte.setText(res);
            } else {
                if (cbNombreCamions.getSelectedItem().equals("1 Camion")) {

                } else {

                }
            }
            this.pack();
        }
        if (e.getSource() == btExporterCSV) {

            try {
                // .getText() est indispensable pour avoir le vrai nom tapé par l'utilisateur !
                String nomFichier = tfExporterCSV.getText().trim() + ".csv";
                File fichier = new File(nomFichier);

                try (BufferedWriter writer = new BufferedWriter(new FileWriter(fichier))) {
                    writer.write("data/"+strExportCSV + "\n");
                }

                System.out.println("Fichier CSV créé avec succès dans : " + fichier.getAbsolutePath());
            } catch (IOException ex) {
                System.out.println("IOException : Erreur lors de l'export du fichier.");
            }
        }

    }

    private String[] lancerChristofide(Graphe graphe,List<String> typesChoisis) {
        AlgorithmeChristofide ac = new AlgorithmeChristofide(grapheVisuel, graphe, typesChoisis);
        List<String> lst = ac.executerChristofide();
        String resultatTexte = new String("");
        String resultatCSV = new String("");
        for (int i = 0; i < lst.size(); i++) {
            resultatTexte = resultatTexte + lst.get(i) + " -> ";
            resultatCSV = resultatCSV + lst.get(i).substring(1) + ";";
        }
        String cout = "" + Math.round(ac.getDuree());
        resultatTexte = resultatTexte + "\n Ce trajet prend : " + cout + " min";
        String[] resultatRetourner = {resultatTexte, resultatCSV, cout};
        return resultatRetourner;
    }
}
