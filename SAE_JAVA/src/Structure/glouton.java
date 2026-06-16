/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Structure;

import Structure.Camion;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author walid
 */
public class glouton {
    
    Camion c ;
    private List<Sommet> sommetAdjacents;
    private Sommet Sdep ;
    private List<Route> parcours;
    private Route[][] tabR;
    
    
    public glouton(Camion cam, Route[][] tabR){
        this.c=cam;
        this.Sdep =cam.getSommetDepart();
        this.sommetAdjacents = new ArrayList<>();
        
    }
    public void ajouterSommetAdjacents(){
        Route r ;
        for(int i = 0 ; i < tabR.length ; i++){
            for(int j = 0 ; j < tabR.length ; j ++){
                r = tabR[i][j] ;
                if (r.getsDépart() == this.Sdep && this.sommetAdjacents.contains(r.getsArrivée())!=true){
                    this.sommetAdjacents.add(r.getsArrivée());
                }
                if (r.getsArrivée() == this.Sdep && this.sommetAdjacents.contains(r.getsDépart())!=true){ 
                    this.sommetAdjacents.add(r.getsDépart());
                }
                
                    
            }
        }
    
    }
}
