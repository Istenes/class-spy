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
public class Constant {
    
    public int tag;
    public int index;
    
    public ArrayList<Attribute> attributes;
    
    public Constant(int index, int tag){
        this.index = index;
        this.tag = tag;
    }
    
    @Override
    public String toString() {
        String s = Utility.ConstantType.CONSTANT_Unknown.constantTypeName()+" {";
        s += "\n\tindex: "+index+";";
        s += "\n\ttag: "+tag+";";
        s += "\n}";
        return s;
    }
    
    public class Attribute {
        public String attributeName;
        public Object attributeValue;
        
        public Attribute(String name, Object value){
            attributeName = name;
            attributeValue = value;
        }
        
    }
    
}
