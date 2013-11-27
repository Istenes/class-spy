package se.istenes.spy.util;

import java.util.Arrays;

import se.istenes.attribute.Attribute;
import se.istenes.attribute.ConstantValue;


/**
 *
 * @author niklasis
 */
public class Field {
    private Utility.AccesFlag[] acces_flags;
    private int index;
    private int name_index;
    private int descriptor_index;
    private int attributes_count;
    private ConstantValue[] attributes;
    
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
    
    
    public int getName_index() {
		return name_index;
	}

	public void setName_index(int name_index) {
		this.name_index = name_index;
	}

	public int getDescriptor_index() {
		return descriptor_index;
	}

	public void setDescriptor_index(int descriptor_index) {
		this.descriptor_index = descriptor_index;
	}

	public int getAttributes_count() {
		return attributes_count;
	}

	public void setAttributes_count(int attributes_count) {
		this.attributes_count = attributes_count;
		this.attributes = new ConstantValue[attributes_count];
	}
	
	public void setAttribute(int index, ConstantValue attribute) {
		this.attributes[index-1] = attribute;
	}
	
	public ConstantValue getAttribute(int index) {
		return this.attributes[index-1];
	}

	@Override
    public String toString() {
    	return "name_index: " + name_index + "; descriptor_index: " + descriptor_index + "; " + Arrays.toString(acces_flags);
    }
    
}
