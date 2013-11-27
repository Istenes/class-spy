package se.istenes.spy.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import se.istenes.attribute.ConstantValue;
import se.istenes.spy.constantpool.Constant;
import se.istenes.spy.constantpool.ConstantClass;
import se.istenes.spy.constantpool.ConstantDouble;
import se.istenes.spy.constantpool.ConstantFieldref;
import se.istenes.spy.constantpool.ConstantFloat;
import se.istenes.spy.constantpool.ConstantInteger;
import se.istenes.spy.constantpool.ConstantInterfaceMethodref;
import se.istenes.spy.constantpool.ConstantLong;
import se.istenes.spy.constantpool.ConstantMethodref;
import se.istenes.spy.constantpool.ConstantNameAndType;
import se.istenes.spy.constantpool.ConstantPool;
import se.istenes.spy.constantpool.ConstantString;
import se.istenes.spy.constantpool.ConstantUtf8;

/**
 * 
 * @author istenes
 */
public class ClassFile {

	private byte[] classData;
	private String classFilePath;
	private String classFileName;

	private int thisClassIndex;
	private String thisClassName;
	private int superClassIndex;
	private String superClassName;

	private InputStream fileStream;
	private boolean init = false;

	private boolean cafebabe;

	private int minorVersion;
	private Utility.MajorVersion majorVersion;
	private String version;

	private ConstantPool constantPool;
	private Fields fields;

	private int interfaceCount;
	private int[] interfaceIndexes;
	private String[] interfaceNames;

	private int current_byte_position = 0;
	private int last_byte_position = 0;

	private Utility.AccesFlag[] acces_flags;

	public ClassFile(java.io.File file) {
		this.classFilePath = file.getAbsolutePath();
		this.classFileName = file.getName();
		try {
			fileStream = new FileInputStream(classFilePath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		constantPool = new ConstantPool();
		fields = new Fields();

		init = true;
	}

	public boolean readClassFile() {
		if (readCafebabe()) {
			readVersion();
			readConstantPool();
			readClassAccesFlags();
			readThisClass();
			readSuperClass();
			readInterfaces();
			readFields();
			return true;
		}
		return false;
	}

	public boolean getCafebabe() {
		return cafebabe;
	}

	public String getVersion() {
		return version;
	}

	public String getName() {
		return classFileName;
	}

	public ConstantPool getConstantPool() {
		if (cafebabe)
			return constantPool;
		else
			return null;
	}

	public Fields getFields() {
		if (cafebabe)
			return fields;
		else
			return null;
	}

	public Utility.AccesFlag[] getClassAccesFlags() {
		return acces_flags;
	}

	public int getThisClassIndex() {
		return thisClassIndex;
	}

	public String getThisClassName() {
		return thisClassName;
	}

	public int getSuperClassIndex() {
		return superClassIndex;
	}

	public String getSuperClassName() {
		return superClassName;
	}

	public int getInterfacesCount() {
		return interfaceCount;
	}

	public String[] getInterfaceNames() {
		return interfaceNames;
	}

	public int[] getInterfaceIndexes() {
		return interfaceIndexes;
	}

	public String accesFlagsToString(Utility.AccesFlag[] acces_flags) {
		String acces_flags_text = "";
		for (int flag = 0; flag < acces_flags.length; flag++) {
			if (acces_flags[flag].isSet == true)
				acces_flags_text += acces_flags[flag].flagName() + "\n";
		}
		return acces_flags_text;
	}

	private boolean readCafebabe() {
		if (read_u4() == Utility.CAFEBABE) {
			cafebabe = true;
			return true;
		}

		return false;
	}

	private void readVersion() {
		minorVersion = read_u2();
		majorVersion = Utility.MajorVersion.getVersion(read_u2());
		version = majorVersion.versionName() + "." + minorVersion;
	}

	private void readConstantPool() {
		constantPool.setConstantPoolCount(read_u2());
		for (int i = 1; i <= constantPool.getConstantPoolCount(); i++) {
			int tag = read_u1();
			Constant constant = new Constant(i, tag, last_byte_position);
			Utility.ConstantType constantType = Utility.ConstantType
					.getConstantType(tag);
			switch (constantType) {
			case CONSTANT_Class:
				constant = new ConstantClass(i, tag, read_u2(), last_byte_position);
				break;
			case CONSTANT_Double:
				constant = new ConstantDouble(i, tag, read_u4(), read_u4(), last_byte_position);
				break;
			case CONSTANT_Fieldref:
				constant = new ConstantFieldref(i, tag, read_u2(), read_u2(), last_byte_position);
				break;
			case CONSTANT_Float:
				constant = new ConstantFloat(i, tag, read_u4(), last_byte_position);
				break;
			case CONSTANT_Integer:
				constant = new ConstantInteger(i, tag, read_u4(), last_byte_position);
				break;
			case CONSTANT_InterfaceMetodref:
				constant = new ConstantInterfaceMethodref(i, tag, read_u2(),
						read_u2(), last_byte_position);
				break;
			case CONSTANT_Long:
				constant = new ConstantLong(i, tag, read_u4(), read_u4(), last_byte_position);
				break;
			case CONSTANT_Metodref:
				constant = new ConstantMethodref(i, tag, read_u2(), read_u2(), last_byte_position);
				break;
			case CONSTANT_NameAndType:
				constant = new ConstantNameAndType(i, tag, read_u2(), read_u2(), last_byte_position);
				break;
			case CONSTANT_String:
				constant = new ConstantString(i, tag, read_u2(), last_byte_position);
				break;
			case CONSTANT_Utf8:
				int length = read_u2();
				byte[] bytes = read_length(length);
				constant = new ConstantUtf8(i, tag, length, bytes, last_byte_position);
				break;
			case CONSTANT_Unknown:
				break;
			}
			constantPool.addConstant(constant);
		}
	}

	private void readInterfaces() {
		interfaceCount = read_u2();
		interfaceIndexes = new int[interfaceCount];
		interfaceNames = new String[interfaceCount];
		for (int i = 0; i < interfaceCount; i++) {
			interfaceIndexes[i] = read_u2();
			ConstantClass constantClass = (ConstantClass) constantPool
					.getConstant(interfaceIndexes[i]);
			ConstantUtf8 constant = (ConstantUtf8) constantPool
					.getConstant(constantClass.getNameIndex());
			interfaceNames[i] = constant.getText();
		}
	}

	
	private void readFields() {
		final int fields_count = read_u2();
		fields.setFieldCount(fields_count);
		
		for (int f = 1; f <= fields.getFieldCount(); f++) {
			Field field = new Field(f);
			field.setAccesFlags(readFieldAccesFlags());
			field.setName_index(read_u2());
			field.setDescriptor_index(read_u2());
			field.setAttributes_count(read_u2());
			for(int a = 1; a <= field.getAttributes_count(); a++) {
				//This is a field, therefore a ConstantValue is the correct attribute
				ConstantValue cv = new ConstantValue();
				cv.setAttribute_name_index(read_u2());
				cv.setAttribute_length(read_u4());
				cv.setConstant_value_index(read_u2());
				field.setAttribute(a, cv);
			}
			fields.addField(field);
		}
	}

	private void readThisClass() {
		this.thisClassIndex = read_u2();
		ConstantClass constantClass = (ConstantClass) constantPool
				.getConstant(thisClassIndex);
		ConstantUtf8 constantUtf8 = (ConstantUtf8) constantPool
				.getConstant(constantClass.getNameIndex());
		this.thisClassName = constantUtf8.getText();
	}

	private void readSuperClass() {
		this.superClassIndex = read_u2();
		ConstantClass constantClass = (ConstantClass) constantPool
				.getConstant(superClassIndex);
		ConstantUtf8 constantUtf8 = (ConstantUtf8) constantPool
				.getConstant(constantClass.getNameIndex());
		this.superClassName = constantUtf8.getText();
	}

	private void readClassAccesFlags() {
		final Utility.AccesFlag[] accesFlags = { Utility.AccesFlag.ACC_PUBLIC,
				Utility.AccesFlag.ACC_FINAL, Utility.AccesFlag.ACC_SUPER,
				Utility.AccesFlag.ACC_INTERFACE };

		this.acces_flags = parseAccesFlags(read_u2(), accesFlags);
	}

	private Utility.AccesFlag[] readFieldAccesFlags() {
		final Utility.AccesFlag[] accesFlags = { Utility.AccesFlag.ACC_PUBLIC,
				Utility.AccesFlag.ACC_PRIVATE, Utility.AccesFlag.ACC_PROTECTED,
				Utility.AccesFlag.ACC_STATIC, Utility.AccesFlag.ACC_FINAL,
				Utility.AccesFlag.ACC_VOLATILE, Utility.AccesFlag.ACC_TRANSIENT };

		return parseAccesFlags(read_u2(), accesFlags);
	}

	private Utility.AccesFlag[] parseAccesFlags(int u1,
			Utility.AccesFlag[] acces_flags) {
		ArrayList<Utility.AccesFlag> set_access_flags = new ArrayList<>();
		for (int i = 0; i < acces_flags.length; i++) {
			if((u1 & acces_flags[i].flagBit()) == acces_flags[i].flagBit()) {
				set_access_flags.add(acces_flags[i]);
			}
		}
		
		
		return set_access_flags.toArray(new Utility.AccesFlag[set_access_flags.size()]);
	}

	private int read_u2() {
		if (!init) {
			System.out
					.println("[ERROR] - init() method has to be called before reading class file.");
			return -1;
		}
		byte[] bytes = new byte[2];
		try {
			fileStream.read(bytes, 0, 2);
		} catch (IOException e) {

		}
		last_byte_position = current_byte_position;
		current_byte_position += 2;
		return (((0xFF & bytes[0]) << 8) | (0xFF & bytes[1]));
	}

	private int read_u4() {
		if (!init) {
			System.out
					.println("[ERROR] - init() method has to be called before reading class file.");
			return -1;
		}
		byte[] bytes = new byte[4];
		try {
			fileStream.read(bytes, 0, 4);
		} catch (IOException e) {

		}

		last_byte_position = current_byte_position;
		current_byte_position += 4;
		return (((0xFF & bytes[0]) << 24) | ((0xFF & bytes[1]) << 16)
				| ((0xFF & bytes[2]) << 8) | (0xFF & bytes[3]));
	}

	private byte read_u1() {
		if (!init) {
			System.out
					.println("[ERROR] - init() method has to be called before reading class file.");
			return -1;
		}
		byte[] bytes = new byte[1];
		try {
			fileStream.read(bytes, 0, 1);
		} catch (IOException e) {

		}

		last_byte_position = current_byte_position;
		current_byte_position += 1;
		
		return (byte) (0xFF & bytes[0]);
	}

	private byte[] read_length(int length) {
		if (!init) {
			System.out
					.println("[ERROR] - init() method has to be called before reading class file.");
			return null;
		}
		byte[] bytes = new byte[length];
		try {
			fileStream.read(bytes, 0, length);
		} catch (IOException e) {

		}

		last_byte_position = current_byte_position;
		current_byte_position += length;
		
		return bytes;
	}

}
