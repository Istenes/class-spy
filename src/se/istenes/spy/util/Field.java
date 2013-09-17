/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se.istenes.spy.util;


/**
 *
 * @author niklasis
 */
public class Field {
    private Utility.AccesFlag[] acces_flags;
    private int index;
    
    public Field(int index){
        this.index = index;
    }
    
    public int getIndex() {
        return index;
    }
    
    public void setAccesFlags(Utility.AccesFlag[] acces_flags){
        this.acces_flags = acces_flags;
    }
    
    public Utility.AccesFlag[] getAccesFlags(){
        return acces_flags;
    }
    
}
