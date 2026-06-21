/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package IHM;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JScrollPane;

/**
 *
 * @author Alexis Chazal
 */
public class TableauSommet extends JFrame {
    private JTable tab;
    private JScrollPane scrolltableau;
    public TableauSommet(JTable tab){
        this.tab = tab;
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setVisible(true);
        
        scrolltableau = new JScrollPane(tab);
        JPanel panneauGlobal = new JPanel();
        panneauGlobal.add(scrolltableau);
        
        this.setContentPane(panneauGlobal);
        this.pack();
    }
}
