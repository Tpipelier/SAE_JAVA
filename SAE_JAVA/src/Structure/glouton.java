/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Structure;


import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author walid
 */
public class glouton {
    
   
    private List<Sommet> sommetAdjacents;
    private Sommet Sdep ;
    private List<Route> parcours;
    private Route[][] tabR;
    
    
    public glouton(Route[][] tabR){
        
        //this.Sdep =cam.getSommetDepart();
        this.sommetAdjacents = new ArrayList<>();
        
    }
    public void ajouterSommetAdjacents(){
        Route r ;
        for(int i = 0 ; i < tabR.length ; i++){
            for(int j = 0 ; j < tabR.length ; j ++){
                r = tabR[i][j] ;
                if (r.getSommetDepart() == this.Sdep && this.sommetAdjacents.contains(r.getSommetArrivee())!=true){
                    this.sommetAdjacents.add(r.getSommetArrivee());
                }
                if (r.getSommetArrivee() == this.Sdep && this.sommetAdjacents.contains(r.getSommetDepart())!=true){ 
                    this.sommetAdjacents.add(r.getSommetDepart());
                }
                
                    
            }
        }
    
    }
}
