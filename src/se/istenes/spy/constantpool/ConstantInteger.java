package se.istenes.spy.constantpool;

import java.util.ArrayList;

import se.istenes.spy.util.Utility;

/**
 *
 * @author istenes
 */
public class ConstantInteger extends Constant {
    
    private int value;
    
    public ConstantInteger(int index, int tag, int value, int byte_position) {
        super(index, tag, byte_position);
        this.value = value;    
        
        attributes = new ArrayList<Attribute>(1);
        
        Attribute attr_value = new Attribute("value", value);
        
        attributes.add(attr_value);
        
    }
    
    public int getValue(){
        return value;
    }
    
    @Override
    public String toString() {
        String s = Utility.ConstantType.CONSTANT_Integer.constantTypeName()+" {";
        s += "\n\tbyte_position: "+byte_position+";";
        s += "\n\tindex: "+index+";";
        s += "\n\ttag: "+tag+";";
        s += "\n\tvalue: "+value+";";
        s += "\n}\n";
        return s;
    }
}
