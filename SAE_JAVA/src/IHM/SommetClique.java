/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package IHM;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
    private JTable tableau;
    private JButton btDistance;
    private JButton btDuree;
    
    public SommetClique(JFrame f,Node n){
        super(f,"Sommet "+ n.getId());
        this.sommetCliqué = n;
        
        this.setLocationRelativeTo(f);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.initComposants();
        this.setVisible(true); 
    }
    
    private void initComposants(){
        texte = new JLabel("test");
        tableau = new JTable(7,3);
        btDistance = new JButton("test");
        btDuree = new JButton("test");
        
        JPanel panneauGlobal = new JPanel();
        this.setContentPane(panneauGlobal);

        panneauGlobal.setLayout(new GridBagLayout());
        GridBagConstraints gb = new GridBagConstraints();
        gb.anchor = GridBagConstraints.NORTHWEST;
        gb.fill = GridBagConstraints.VERTICAL;
        gb.gridx = 0;
        gb.gridy = 0;
        panneauGlobal.add(texte);
        gb.gridx += 1;
        panneauGlobal.add(tableau);
        gb.gridx += 1;
        panneauGlobal.add(btDistance);
        gb.gridx += 1;
        panneauGlobal.add(btDuree);
        gb.gridx += 1;
        this.pack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }
}
