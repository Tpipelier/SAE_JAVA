/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package IHM;

import java.awt.BorderLayout;
import static java.awt.BorderLayout.WEST;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author alexi
 */
public class FenetrePrincipale extends JFrame {

    private JButton boutonChargerCarte;
    private JButton boutonRavitailler;
    private JButton boutonNotifications;

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

    }

    /**
     * Génère les composants de la FenetrePrincipale
     */
    public void initComposants() {

        boutonChargerCarte = new JButton("Charger une carte");
        boutonRavitailler = new JButton("Ravitailler");
        boutonNotifications = new JButton("Notifications");

        appliquerStyleBouton(boutonChargerCarte);
        appliquerStyleBouton(boutonRavitailler);
        appliquerStyleBouton(boutonNotifications);

        JPanel panneauGlobal = new JPanel();
        JPanel panneauDeroulant = new JPanel();
        JPanel vide = new JPanel();
        this.setContentPane(panneauGlobal);
        panneauGlobal.setLayout(new BorderLayout());
        panneauDeroulant.setLayout(new GridBagLayout());
        GridBagConstraints gb = new GridBagConstraints();
        gb.anchor = GridBagConstraints.NORTHWEST;

        gb.gridx = 0;
        gb.gridy = 0;
        gb.weighty = 0;
        panneauDeroulant.add(boutonChargerCarte, gb);
        gb.gridx = 0;
        gb.gridy = 1;
        panneauDeroulant.add(boutonRavitailler, gb);
        gb.gridx = 0;
        gb.gridy = 2;
        gb.weighty = 1;
        gb.fill = GridBagConstraints.BOTH;
        panneauDeroulant.add(vide, gb);
        gb.gridx = 0;
        gb.gridy = 3;
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

    private void afficherGraphe() {
        
    }
}
