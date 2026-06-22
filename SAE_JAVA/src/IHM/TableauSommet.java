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
 * Fenetre affichant un tableau ({@link JTable}) dans une zone defilante. Elle
 * sert a presenter en grand le detail des distances ou des durees vers tous les
 * centres, depuis la boite de dialogue {@link SommetClique}.
 *
 * @author Alexis Chazal
 */
public class TableauSommet extends JFrame {
    private JTable tab;
    private JScrollPane scrolltableau;

    /**
     * Construit et affiche la fenetre contenant le tableau donne.
     *
     * @param tab le tableau a afficher dans la fenetre
     */
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
