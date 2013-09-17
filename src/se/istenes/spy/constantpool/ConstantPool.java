/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se.istenes.spy.constantpool;

/**
 *
 * @author niklasis
 */



public class ConstantPool {
    protected Constant[] constantPoolData;
    protected int constantPoolCount;
    
    public void setConstantPoolCount(int count){
        this.constantPoolCount = count-1;
        constantPoolData = new Constant[count];
    }
    
    public int getConstantPoolCount(){
        return constantPoolCount;
    }
    
    public Constant getConstant(int index){
        return constantPoolData[index];
    }
    
    public void addConstant(Constant constant){
        constantPoolData[constant.index] = constant;
    }
    
    @Override
    public String toString(){
        String s = "\nConstantPool\n======================\n";
        s += "constant_pool_count: "+constantPoolCount+";\n";
        for(int i = 1;i<=constantPoolCount;i++){
            s+=getConstant(i).toString();
        }
        return s;
    }
}

