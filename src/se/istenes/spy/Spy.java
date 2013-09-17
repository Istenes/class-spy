package se.istenes.spy;

import java.io.File;
import java.util.ArrayList;

import se.istenes.spy.util.ClassFile;

public class Spy {
	
	private ArrayList<ClassFile> classFiles = new ArrayList<ClassFile>();
	
	public Spy() {
		
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
		
	}
	
	public Spy(ClassFile[] classFiles) {
		
	}
}
