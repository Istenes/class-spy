/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se.istenes.spy.constantpool;

import java.util.ArrayList;

import se.istenes.spy.util.Utility;

/**
 *
 * @author niklasis
 */
public class ConstantClass extends Constant {
    
    private int name_index;

    public ConstantClass(int index, int tag, int name_index, int byte_position){
        super(index, tag, byte_position);
        this.name_index = name_index;
        
        attributes = new ArrayList<Attribute>(1);
        
        Attribute attr_nameIndex = new Attribute("name_index", name_index);        
        attributes.add(attr_nameIndex);
    }
    
    public int getNameIndex(){
        return name_index;
    }
    
    @Override
    public String toString() {
        String s = Utility.ConstantType.CONSTANT_Class.constantTypeName()+" {";
        s += "\n\tindex: "+index+";";
        s += "\n\ttag: "+tag+";";
        s += "\n\tname_index: "+name_index+";";
        s += "\n}\n";
        return s;
    }
    
    
}
