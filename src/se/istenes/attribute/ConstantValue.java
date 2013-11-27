package se.istenes.attribute;

public class ConstantValue extends Attribute {

	private int constant_value_index;

	public int getConstant_value_index() {
		return constant_value_index;
	}

	public void setConstant_value_index(int constant_value_index) {
		this.constant_value_index = constant_value_index;
	}
	
	@Override
	public String toString() {
		return "attribute_name_index: " + getAttribute_name_index() + "; attribute_length: " + getAttribute_length() + "; constant_value_index: " + getConstant_value_index() + ";";
	}
	
}
