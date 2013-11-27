package se.istenes.attribute;

public abstract class Attribute {

	private int attribute_name_index;
	private int attribute_length;
	
	public Attribute() {
		
	}
	
	public Attribute(int attribute_name_index, int attribute_length) {
		this.attribute_name_index = attribute_name_index;
		this.attribute_length = attribute_length;
	}
	
	public int getAttribute_name_index() {
		return attribute_name_index;
	}
	
	public void setAttribute_name_index(int attribute_name_index) {
		this.attribute_name_index = attribute_name_index;
	}
	
	public int getAttribute_length() {
		return attribute_length;
	}
	
	public void setAttribute_length(int attribute_length) {
		this.attribute_length = attribute_length;
	}

}
