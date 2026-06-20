/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package IHM;

import Outils.DistanceVers;
import Structure.Graphe;
import Structure.Sommet;
import java.awt.BorderLayout;
import java.awt.Color;
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

    private Node nodeCliqué;
    private JLabel texte;
    private JLabel textetab;
    private JTable tableauBase;
    private JTable tableauDistance;
    private JTable tableauDuree;
    private JButton btDistance;
    private JButton btDuree;
    private Graphe graphe;

    public SommetClique(FenetrePrincipale f, Node n, Graphe graphe) {
        super(f, "Sommet " + n.getId());
        this.nodeCliqué = n;
        this.graphe = graphe;

        this.setLocationRelativeTo(f);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.initComposants();
        this.setVisible(true);
    }

    private void initComposants() {
        if (nodeCliqué.getAttribute("ui.class").equals("maternite")) {
            texte = new JLabel("Sommet " + nodeCliqué.getId() + ", Type : Maternité");
        } else if (nodeCliqué.getAttribute("ui.class").equals("blocOperatoire")) {
            texte = new JLabel("Sommet " + nodeCliqué.getId() + ", Type : Bloc opératoire");

        } else if (nodeCliqué.getAttribute("ui.class").equals("nutrition")) {
            texte = new JLabel("Sommet " + nodeCliqué.getId() + ", Type : Centre de nutrition");

        }
        textetab = new JLabel("Les centres les plus proches du sommet " + nodeCliqué.getId() + " sont : ");
        tableauBase = remplirTableauBase(new JTable(7, 4));
        tableauDistance = remplirGrandTableau(new JTable(graphe.getNbSommet()+2, 3),"Distances");
        tableauDuree = remplirGrandTableau(new JTable(graphe.getNbSommet()+2, 3),"Durees");
        btDistance = new JButton("Afficher le tableau des distances");
        btDuree = new JButton("Afficher le tableau des durées");
        btDistance.addActionListener(this);
        btDuree.addActionListener(this);
        
        choisirTailleColonnes();

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
        panneauGlobal.add(tableauBase, gb);
        gb.gridy += 1;
        panneauGlobal.add(btDistance, gb);
        gb.gridy += 1;
        panneauGlobal.add(btDuree, gb);
        this.pack();
    }
    
    private void choisirTailleColonnes(){
        tableauBase.getColumnModel().getColumn(0).setPreferredWidth(75);
        tableauBase.getColumnModel().getColumn(1).setPreferredWidth(150);
        tableauBase.getColumnModel().getColumn(2).setPreferredWidth(75);
        tableauBase.getColumnModel().getColumn(3).setPreferredWidth(150);
        
        tableauDistance.getColumnModel().getColumn(0).setPreferredWidth(300);
        tableauDistance.getColumnModel().getColumn(1).setPreferredWidth(75);
        tableauDistance.getColumnModel().getColumn(2).setPreferredWidth(150);
        
        tableauDuree.getColumnModel().getColumn(0).setPreferredWidth(300);
        tableauDuree.getColumnModel().getColumn(1).setPreferredWidth(75);
        tableauDuree.getColumnModel().getColumn(2).setPreferredWidth(150);
    }

    private JTable remplirTableauBase(JTable tab) {

        tab.setValueAt("ID", 0, 0);
        tab.setValueAt("Type du centre", 0, 1);
        tab.setValueAt("Durée", 0, 2);
        tab.setValueAt("Durée estimée", 0, 3);
        Sommet sommetClique = new Sommet(nodeCliqué.getAttribute("index"), nodeCliqué.getAttribute("id"), nodeCliqué.getAttribute("type"));
        List<DistanceVers> lst = graphe.distancesCroissantesVersTous(sommetClique, "Duree");
        boolean sortie = false;
        boolean typeMInseree = false;
        boolean typeOInseree = false;
        boolean typeNInseree = false;
        for (int i = 0; i < graphe.getNbSommet()-1 && !sortie; i++) {
            if (lst.get(i).getType().equals("M") && !typeMInseree) {
                tab.setValueAt(lst.get(i).getNom(), 1, 0);
                tab.setValueAt(lst.get(i).getType(), 1, 1);
                tab.setValueAt(lst.get(i).getDuree(), 1, 2);
                tab.setValueAt(lst.get(i).getDureeEstimee(), 1, 3);
                typeMInseree = true;
            }
            if (lst.get(i).getType().equals("O") && !typeOInseree) {
                tab.setValueAt(lst.get(i).getNom(), 3, 0);
                tab.setValueAt(lst.get(i).getType(), 3, 1);
                tab.setValueAt(lst.get(i).getDuree(), 3, 2);
                tab.setValueAt(lst.get(i).getDureeEstimee(), 3, 3);
                typeOInseree = true;
            }
            if (lst.get(i).getType().equals("N") && !typeNInseree) {
                tab.setValueAt(lst.get(i).getNom(), 5, 0);
                tab.setValueAt(lst.get(i).getType(), 5, 1);
                tab.setValueAt(lst.get(i).getDuree(), 5, 2);
                tab.setValueAt(lst.get(i).getDureeEstimee(), 5, 3);
                typeNInseree = true;
            }
            if(typeMInseree && typeOInseree && typeNInseree){
                sortie = true;
            }
        }
        
        lst = graphe.distancesCroissantesVersTous(sommetClique, "DureeEstimee");
         sortie = false;
         typeMInseree = false;
         typeOInseree = false;
         typeNInseree = false;
        for (int i = 0; i < graphe.getNbSommet()-1 && !sortie; i++) {
            if (lst.get(i).getType().equals("M") && !typeMInseree) {
                tab.setValueAt(lst.get(i).getNom(), 2, 0);
                tab.setValueAt(lst.get(i).getType(), 2, 1);
                tab.setValueAt(lst.get(i).getDuree(), 2, 2);
                tab.setValueAt(lst.get(i).getDureeEstimee(), 2, 3);
                typeMInseree = true;
            }
            if (lst.get(i).getType().equals("O") && !typeOInseree) {
                tab.setValueAt(lst.get(i).getNom(), 4, 0);
                tab.setValueAt(lst.get(i).getType(), 4, 1);
                tab.setValueAt(lst.get(i).getDuree(), 4, 2);
                tab.setValueAt(lst.get(i).getDureeEstimee(), 4, 3);
                typeOInseree = true;
            }
            if (lst.get(i).getType().equals("N") && !typeNInseree) {
                tab.setValueAt(lst.get(i).getNom(), 6, 0);
                tab.setValueAt(lst.get(i).getType(), 6, 1);
                tab.setValueAt(lst.get(i).getDuree(), 6, 2);
                tab.setValueAt(lst.get(i).getDureeEstimee(), 6, 3);
                typeNInseree = true;
            }
            if(typeMInseree && typeOInseree && typeNInseree){
                sortie = true;
            }
        }

        return tab;
    }
    
    private JTable remplirGrandTableau(JTable tab,String type) {
        
        Sommet sommetClique = new Sommet(nodeCliqué.getAttribute("index"),nodeCliqué.getAttribute("id"), nodeCliqué.getAttribute("type"));
        List<DistanceVers> lst = graphe.distancesCroissantesVersTous(sommetClique, type);
        int position = 1;
        String text1;
        String text3;
        if(type.equals("Durees")){
             text1 = "Durées";
             text3 = "Durées (min)";
        }else {
             text1 = "Distances";
             text3 = "Distance (km)";
        }
        
        tab.setValueAt(text1+" aux maternitées", 0, 0);
        tab.setValueAt("ID", 0, 1);
        tab.setValueAt(text3, 0, 2);
        for (int i = 0; i < graphe.getNbSommet()-1; i++) {
            if (lst.get(i).getType().equals("M")) {
                tab.setValueAt(lst.get(i).getNom(), position, 1);
                tab.setValueAt(lst.get(i).getDistance(), position, 2);
                position++;
            }
        }
        tab.setValueAt(text1+" aux centres de nutritions", position, 0);
        tab.setValueAt("ID", position, 1);
        tab.setValueAt(text3, position, 2);
        position++;
        for (int i = 0; i < graphe.getNbSommet()-1; i++) {
            if (lst.get(i).getType().equals("N")) {
                tab.setValueAt(lst.get(i).getNom(), position, 1);
                tab.setValueAt(lst.get(i).getDistance(), position, 2);
                position++;
            }
        }
        tab.setValueAt(text1+" aux blocs opératoires", position, 0);
        tab.setValueAt("ID", position, 1);
        tab.setValueAt(text3, position, 2);
        position++;
        for (int i = 0; i < graphe.getNbSommet()-1; i++) {
            if (lst.get(i).getType().equals("O")) {
                tab.setValueAt(lst.get(i).getNom(), position, 1);
                tab.setValueAt(lst.get(i).getDistance(), position, 2);
                position++;
            }
        }
        return tab;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btDistance) {
            TableauSommet tableauSommet = new TableauSommet(tableauDistance);
        }
        if (e.getSource() == btDuree) {
            TableauSommet tableauSommet = new TableauSommet(tableauDuree);
        }
    }
}
