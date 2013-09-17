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
public class ConstantFieldref extends Constant {
    
    private int class_index, name_and_type_index;
    
    public ConstantFieldref(int index, int tag, int class_index, int name_and_type_index) {
        super(index, tag);
        this.class_index = class_index;
        this.name_and_type_index = name_and_type_index;
        
        attributes = new ArrayList<Attribute>(2);
        
        Attribute attr_classIndex = new Attribute("class_index", class_index);     
        Attribute attr_nameAndTypeIndex = new Attribute("name_and_type_index", name_and_type_index);        
        attributes.add(attr_classIndex);    
        attributes.add(attr_nameAndTypeIndex);
    }
    
    public int getClassIndex(){
        return class_index;
    }
    
    public int getNameAndTypeIndex(){
        return name_and_type_index;
    }
        
    @Override
    public String toString() {
        String s = Utility.ConstantType.CONSTANT_Fieldref.constantTypeName()+" {";
        s += "\n\tindex: "+index+";";
        s += "\n\ttag: "+tag+";";
        s += "\n\tclass_index: "+class_index+";";
        s += "\n\tname_and_type_index: "+name_and_type_index+";";
        s += "\n}\n";
        return s;
    }
    
}
