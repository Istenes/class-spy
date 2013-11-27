package se.istenes.spy.constantpool;

import java.util.ArrayList;

import se.istenes.spy.util.Utility;

/**
 *
 * @author nistenes
 */
public class ConstantFloat extends Constant {
    
    private float value;
    
    public ConstantFloat(int index, int tag, int value, int byte_position) {
        super(index, tag, byte_position);
        this.value = Float.intBitsToFloat(value);
        
        attributes = new ArrayList<Attribute>(1);
        
        Attribute attr_value = new Attribute("value", value);
        
        attributes.add(attr_value);
    }
        
    public float getValue(){
        return value;
    }
    
    @Override
    public String toString() {
        String s = Utility.ConstantType.CONSTANT_Float.constantTypeName()+" {";
        s += "\n\tindex: "+index+";";
        s += "\n\ttag: "+tag+";";
        s += "\n\tvalue: "+value+";";
        s += "\n}\n";
        return s;
    }
    
}
