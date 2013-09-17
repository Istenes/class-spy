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
public class ConstantNameAndType extends Constant {
    
    private int name_index, descriptor_index;
    
    public ConstantNameAndType(int index, int tag, int name_index, int descriptor_index) {
        super(index, tag);
        this.name_index = name_index;
        this.descriptor_index = descriptor_index;
        
        attributes = new ArrayList<Attribute>(2);
        
        Attribute attr_nameIndex = new Attribute("name_index", name_index);
        Attribute attr_descriptorIndex = new Attribute("descriptor_index", descriptor_index);
        
        attributes.add(attr_nameIndex);
        attributes.add(attr_descriptorIndex);
    }
    
    @Override
    public String toString() {
        String s = Utility.ConstantType.CONSTANT_NameAndType.constantTypeName()+" {";
        s += "\n\tindex: "+index+";";
        s += "\n\ttag: "+tag+";";
        s += "\n\tname_index: "+name_index+";";
        s += "\n\tdescriptor_index: "+descriptor_index+";";
        s += "\n}\n";
        return s;
    }
    
    
}
