package se.istenes.spy.constantpool;

import java.util.ArrayList;

import se.istenes.spy.util.Utility;

/**
 *
 * @author istenes
 */
public class ConstantString extends Constant {

    private int string_index;
    
    public ConstantString(int index, int tag, int string_index, int byte_position) {
        super(index, tag, byte_position);
        this.string_index = string_index;        
        attributes = new ArrayList<Attribute>(1);
        
        Attribute attr_stringIndex = new Attribute("string_index", string_index);
        
        attributes.add(attr_stringIndex);
    }
    
    @Override
    public String toString() {
        String s = Utility.ConstantType.CONSTANT_String.constantTypeName()+" {";
        s += "\n\tindex: "+index+";";
        s += "\n\ttag: "+tag+";";
        s += "\n\tstring_index: "+string_index+";";
        s += "\n}\n";
        return s;
    }
    
    
}
