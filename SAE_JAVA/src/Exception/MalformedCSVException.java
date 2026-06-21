/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Exception;

/**
 * Exception levee lorsqu'un fichier CSV decrivant un graphe est mal forme
 * (par exemple une ligne ne respectant pas le format attendu).
 *
 * @author theop
 */
public class MalformedCSVException extends Exception {

    /**
     * Construit une exception de fichier CSV mal forme.
     */
    public MalformedCSVException() {
        super();
    }
}
