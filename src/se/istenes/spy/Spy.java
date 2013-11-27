package se.istenes.spy;

import java.io.File;
import java.util.ArrayList;

import se.istenes.spy.util.ClassFile;

public class Spy {
	
	private ArrayList<ClassFile> classFiles = new ArrayList<ClassFile>();
	
	public Spy() {
		
	}
	
	public Spy(File file) {
		ClassFile classFile = new ClassFile(file);
		if(!classFile.readClassFile()) {
			System.err.println("Could not read class file, CAFEBABE is missing.");
		}
		classFiles.add(classFile);
	}
	
	public Spy(String classFilePath) {
		File file = new File(classFilePath);
		ClassFile classFile = new ClassFile(file);
		if(!classFile.readClassFile()) {
			System.err.println("Could not read class file, CAFEBABE is missing.");
		}
		classFiles.add(classFile);		
	}
	
	public Spy(ClassFile classFile) {
		System.err.println("Not implemented");
	}
	
	public Spy(ClassFile[] classFiles) {
		System.err.println("Not implemented");
	}
	
	public ClassFile getClassAt(int index) {
		if(classFiles.size()<=0) {
			System.err.println("No class file loaded yet");
			return null;
		}
		return classFiles.get(index);
	}
}
