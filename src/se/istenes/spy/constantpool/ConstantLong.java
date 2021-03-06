package se.istenes.spy.constantpool;

import java.util.ArrayList;

import se.istenes.spy.util.Utility;

/**
 *
 * @author istenes
 */
public class ConstantLong extends Constant {
    
    private int high_bytes, low_bytes;
    private long value;
    
    public ConstantLong(int index, int tag, int high_bytes, int low_bytes, int byte_position) {
        super(index, tag, byte_position);
        this.high_bytes = high_bytes;
        this.low_bytes = low_bytes;
        this.value = ((long) high_bytes << 32)|low_bytes;  
        
        attributes = new ArrayList<Attribute>(3);
        
        Attribute attr_highBytes = new Attribute("high_bytes", high_bytes);     
        Attribute attr_lowBytes = new Attribute("low_bytes", low_bytes);     
        Attribute attr_value = new Attribute("value", value);        
        attributes.add(attr_highBytes);    
        attributes.add(attr_lowBytes);    
        attributes.add(attr_value);
    }   
    
    public int getHighBytes(){
        return high_bytes;
    }
    
    public int getLowBytes(){
        return low_bytes;
    }
    
    public long getValue(){
        return value;
    }
        
    @Override
    public String toString() {
        String s = Utility.ConstantType.CONSTANT_Long.constantTypeName()+" {";
        s += "\n\tindex: "+index+";";
        s += "\n\ttag: "+tag+";";
        s += "\n\thigh_bytes: "+high_bytes+";";
        s += "\n\tlow_bytes: "+low_bytes+";";
        s += "\n\tvalue: "+value+";";
        s += "\n}\n";
        return s;
    }
    
}
