package se.istenes.spy.util;

/**
 *
 * @author istenes
 */
public class Fields {
    private Field[] fields;
    private int fieldCount;
    
    public void setFieldCount(int fieldCount) {
        this.fieldCount = fieldCount;
        fields = new Field[fieldCount];
    }
    
    public int getFieldCount(){
        return fieldCount;
    }
    
    public Field getFiled(int index) {
        return fields[index];
    }
    
    public void addField(Field field) {
        fields[field.getIndex()] = field;
    }
    
}
