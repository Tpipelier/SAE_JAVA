/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Structure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author theop
 */
public class plusCourtTrajet {
    private Sommet sommetDepart;
    private Sommet sommetArrivee;
    private int tempsTotalTrajet;
    private List<Sommet> etapeParcours;
    

    public plusCourtTrajet(Sommet sommetDepart, Sommet sommetArrivee) {
        this.sommetDepart = sommetDepart;
        this.sommetArrivee = sommetArrivee;
        this.tempsTotalTrajet = 0;
        this.etapeParcours = new ArrayList<>();
    }
    public void calculPlusCourtTrajet(){
        //utilisation de djikstra pour trouver 
    }
}
