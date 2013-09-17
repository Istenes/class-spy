package se.istenes.spy.constantpool;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import se.istenes.spy.util.Utility;

/**
 *
 * @author istenes
 */
public class ConstantUtf8 extends Constant {
    
    private int length;
    private byte[] bytes;
    private String text;
    
    public ConstantUtf8(int index, int tag, int length, byte[] bytes) {
        super(index, tag);
        
        this.length = length;
        this.bytes = bytes;
        
        try {
            text = new String(bytes, "UTF-8");
        } catch(UnsupportedEncodingException e) {
            //Don't do anything
        }
        
        attributes = new ArrayList<Attribute>(3);
        
        Attribute attr_length = new Attribute("length", length);
        Attribute attr_bytes = new Attribute("bytes", bytes);
        Attribute attr_text = new Attribute("text", text);
        
        attributes.add(attr_length);
        attributes.add(attr_bytes);
        attributes.add(attr_text);
    }
    
    public int getLength(){
        return length;
    }
    
    public byte[] getBytes(){
        return bytes;
    }
    
    public String getText(){
        return text;
    }
    
    @Override
    public String toString() {
        String s = Utility.ConstantType.CONSTANT_Utf8.constantTypeName()+" {";
        s += "\n\tindex: "+index+";";
        s += "\n\ttag: "+tag+";";
        s += "\n\tlength: "+length+";";
        s += "\n\tbytes: "+bytes.toString()+";";
        s += "\n\ttext: "+text+";";
        s += "\n}\n";
        return s;
    }
    
}
