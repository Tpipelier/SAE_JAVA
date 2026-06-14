/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package IHM;

import Structure.Graphe;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import org.graphstream.graph.Node;

/**
 *
 * @author p2500558
 */
public class SommetClique extends JDialog implements ActionListener {

    private Node sommetCliqué;
    private JLabel texte;
    private JLabel textetab;
    private JTable tableau;
    private JButton btDistance;
    private JButton btDuree;

    public SommetClique(FenetrePrincipale f,Node n) {
        super(f, "Sommet " + n.getId());
        this.sommetCliqué = n;

        this.setLocationRelativeTo(f);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.initComposants();
        this.setVisible(true);
    }

    private void initComposants() {
        if (sommetCliqué.getAttribute("ui.class").equals("maternite")) {
            texte = new JLabel("Sommet " + sommetCliqué.getId() + ", Type : Maternité");
        } else if (sommetCliqué.getAttribute("ui.class").equals("blocOperatoire")) {
            texte = new JLabel("Sommet " + sommetCliqué.getId() + ", Type : Bloc opératoire");

        } else if (sommetCliqué.getAttribute("ui.class").equals("nutrition")) {
            texte = new JLabel("Sommet " + sommetCliqué.getId() + ", Type : Centre de nutrition");

        }
        textetab = new JLabel("Les centres les plus proches du sommet "+sommetCliqué.getId()+"sont : ");
        tableau = remplirTableau(new JTable(7, 4));
        btDistance = new JButton("Afficher le tableau des durées");
        btDuree = new JButton("Afficher le tableau des distances");
        btDistance.addActionListener(this);
        btDuree.addActionListener(this);

        JPanel panneauGlobal = new JPanel();
        this.setContentPane(panneauGlobal);

        panneauGlobal.setLayout(new GridBagLayout());
        GridBagConstraints gb = new GridBagConstraints();
        gb.anchor = GridBagConstraints.NORTHWEST;
        gb.fill = GridBagConstraints.HORIZONTAL;
        gb.gridx = 0;
        gb.gridy = 0;
        panneauGlobal.add(texte, gb);
        gb.gridy += 1;
        panneauGlobal.add(textetab, gb);
        gb.gridy += 1;
        panneauGlobal.add(tableau, gb);
        gb.gridy += 1;
        panneauGlobal.add(btDistance, gb);
        gb.gridy += 1;
        panneauGlobal.add(btDuree, gb);
        this.pack();
    }

    private JTable remplirTableau(JTable tab){
        
        tab.setValueAt("ID", 0, 0);
        tab.setValueAt("Type du centre", 0, 1);
        tab.setValueAt("Durée", 0, 2);
        tab.setValueAt("Durée estimée", 0, 3);
        
        
        
        
        return tab;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btDistance){
            JOptionPane.showMessageDialog(this,
                        "Fonctionnalité pas encore crée",
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE);
        }
        if(e.getSource() == btDuree){
            JOptionPane.showMessageDialog(this,
                        "Fonctionnalité pas encore crée",
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE);
        }
    }
}
