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
        return fields[index-1];
    }
    
    public void addField(Field field) {
        fields[field.getIndex()-1] = field;
    }
    
}
