package se.istenes.spy;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import se.istenes.attribute.ConstantValue;
import se.istenes.spy.constantpool.Constant;
import se.istenes.spy.constantpool.ConstantPool;
import se.istenes.spy.constantpool.ConstantUtf8;
import se.istenes.spy.util.ClassFile;
import se.istenes.spy.util.Field;
import se.istenes.spy.util.Fields;

public class MainWindow {

	private JFrame frame;

	private static boolean isCafeBabeFound = false;

	private static boolean isPublic = false;
	private static boolean isFinal = false;
	private static boolean isSuper = false;
	private static boolean isInterface = false;
	private static boolean isAbstract = false;

	private static int thisIndex;
	private static int superIndex;

	private static int currentConstantPoolIndex = 1; // Constant pool index
														// starts with 1
	private static int constantPoolCount;

	private static int currentInterface = 1; // Constant pool index starts with
												// 1
	private static int interfaceCount;

	private static int currentByte = 0;
	private static int lastByte;
	private JTextPane textPane;
	
	private Spy spy;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
//		JFileChooser jfc = new JFileChooser();
//		jfc.showOpenDialog(null);
//		spy = new Spy(jfc.getSelectedFile());
		spy = new Spy("/home/istenes/workspace/ByteCodeExample/bin/se/istenes/bytecode/Main.class");

		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mntmOpenFile = new JMenuItem("Open file");
		mnFile.add(mntmOpenFile);

		ClassFile classFile = spy.getClassAt(0);
		
		DefaultMutableTreeNode top = new DefaultMutableTreeNode(classFile.getThisClassName());
		
		DefaultMutableTreeNode metaData = new DefaultMutableTreeNode("Meta Data");
		DefaultMutableTreeNode cafeBabe = new DefaultMutableTreeNode("CAFEBABE: " + classFile.getCafebabe());
		DefaultMutableTreeNode version = new DefaultMutableTreeNode("Version: " + classFile.getVersion());
		DefaultMutableTreeNode superClass = new DefaultMutableTreeNode("SuperClass: " + classFile.getSuperClassName());
		DefaultMutableTreeNode accessFlags = new DefaultMutableTreeNode("AccesFlags: " + Arrays.toString(classFile.getClassAccesFlags()));

		metaData.add(cafeBabe);
		metaData.add(version);
		metaData.add(superClass);
		metaData.add(accessFlags);
		top.add(metaData);
		
		DefaultMutableTreeNode constantPoolNode = new DefaultMutableTreeNode("Constant Pool ("+classFile.getConstantPool().getConstantPoolCount()+")");
		ConstantPool constantPool = classFile.getConstantPool();
		for(int c = 1; c <= constantPool.getConstantPoolCount(); c++) {
			Constant constant = constantPool.getConstant(c);
			DefaultMutableTreeNode constantNode = new DefaultMutableTreeNode(constant.toString());
			if(constant instanceof ConstantUtf8) {
				ConstantUtf8 constantutf8 = (ConstantUtf8) constant;
				DefaultMutableTreeNode details = new DefaultMutableTreeNode(constantutf8.getBytesAsString());
				constantNode.add(details);
			}
			constantPoolNode.add(constantNode);
		}
		
		top.add(constantPoolNode);
		
		DefaultMutableTreeNode fieldsNode = new DefaultMutableTreeNode("Fields (" + classFile.getFields().getFieldCount() + ")");
		Fields fields = classFile.getFields();
		for(int f = 1; f <= fields.getFieldCount(); f++) {
			Field field = fields.getFiled(f);
			DefaultMutableTreeNode fieldNode = new DefaultMutableTreeNode(field);
			
			//Read attributes
			for(int c = 1; c<= field.getAttributes_count(); c++) {
				ConstantValue cv = field.getAttribute(c);
				DefaultMutableTreeNode constantValueNode = new DefaultMutableTreeNode(cv);
				fieldNode.add(constantValueNode);
			}
			
			fieldsNode.add(fieldNode);
		}
		
		
		top.add(fieldsNode);
		
		JTree tree = new JTree(top);
		
		JScrollPane scrollPane = new JScrollPane(tree);
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		textPane = new JTextPane();
		frame.getContentPane().add(textPane, BorderLayout.SOUTH);
	}

//	public void readClassFile(java.io.File file) {
//		ClassFile classFile = new ClassFile(file);
//		classFile.init();
//		if (classFile.readClassFile()) {
//			// BytecodeAnalyserView.printHeadline("Header data");
//			//
//			// if(classFile.getCafebabe())
//			// BytecodeAnalyserView.printBytecode("0xCAFEBABE: true;");
//			// else
//			// return;
//			//
//			// BytecodeAnalyserView.printBytecode("version: "+classFile.getVersion()+";");
//			// BytecodeAnalyserView.printBytecode(classFile.getConstantPool().toString());
//			// BytecodeAnalyserView.printHeadline("Class Acces Flags");
//			// BytecodeAnalyserView.printBytecode(classFile.accesFlagsToString(classFile.getClassAccesFlags()));
//			//
//			// BytecodeAnalyserView.fillTree(classFile);
//			//
//		}
//	}
//
//	public boolean readBytecodes(java.io.File file) {
//
//		System.out.println(file);
//		InputStream classFile;
//		isCafeBabeFound = false;
//		currentByte = 0;
//		try {
//			classFile = new FileInputStream(file);
//			while (true) {
//				if (!isCafeBabeFound) {
//					isCafeBabeFound = classFile.read() == Bytecodes.CA ? classFile
//							.read() == Bytecodes.FE ? classFile.read() == Bytecodes.BA ? classFile
//							.read() == Bytecodes.BE ? true : false : false
//							: false
//							: false;
//					if (isCafeBabeFound)
//						cafeBabeFound();
//					else {
//						this.getTextPane().setText(this.getTextPane().getText());
//								.printBytecode("0xCAFEBABE: Not found!\nAre you sure this is a .class file?");
//						return false;
//					}
//				} else {
//					switch (currentByte) {
//					case 4:
//						int[] version = new int[2]; // two bytes for minor
//													// version
//						version[0] = classFile.read();
//						version[1] = classFile.read();
//						minorVersion(version);
//						break;
//
//					case 6:
//						int[] majorVersion = new int[2]; // two bytes for minor
//															// version
//						majorVersion[0] = classFile.read();
//						majorVersion[1] = classFile.read();
//						majorVersion(majorVersion);
//						break;
//					case 8:
//						int[] constantPoolCountData = new int[2]; // two bytes
//																	// for minor
//																	// version
//						constantPoolCountData[0] = classFile.read();
//						constantPoolCountData[1] = classFile.read();
//						constantPoolCount(constantPoolCountData);
//						break;
//					case 10:
//						constantPool(classFile);
//						break;
//
//					default:
//						final int code = classFile.read();
//						if (code == -1) {
//							classFile.close();
//							return false;
//						}
//						BytecodeAnalyserView.printBytecode("Unkown bytecode: "
//								+ code);
//						lastByte = code;
//						break;
//					}
//
//				}
//			}
//		} catch (Exception e) {
//			return false;
//		}
//	}
//
//	private void minorVersion(int[] byteData) {
//		BytecodeAnalyserView.printBytecode("minor_version: " + byteData[0]
//				+ "." + byteData[1]);
//		currentByte = 6;
//	}
//
//	private static void majorVersion(int[] byteData) {
//		// The first byte (version[0]) is unused... I think...
//		BytecodeAnalyserView.printBytecode("major_version: "
//				+ Bytecodes.MajorVersion.getVersionName(byteData[1]));
//		currentByte = 8;
//	}
//
//	private static void constantPoolCount(int[] byteData) {
//		// Sets the constantpool count, remembet that we need to subtract this
//		// with one when we use it.
//		constantPoolCount = byteData[0] + byteData[1];
//		BytecodeAnalyserView.printBytecode("constant_pool_count: "
//				+ constantPoolCount);
//		currentByte = 10;
//	}
//
//	private static void constantPool(InputStream bytecodes) {
//
//		BytecodeAnalyserView
//				.printBytecode("\nConstant Pool\n======================");
//		try {
//			for (int i = 1; i < constantPoolCount; i++) {
//				final int bytecode = bytecodes.read();
//				Bytecodes.ConstantType constantType = Bytecodes.ConstantType
//						.getConstantType(bytecode);
//				currentByte += 1;
//				int classIndex;
//				int nameAndTypeIndex;
//				int nameIndex;
//				int stringIndex;
//				int length;
//				int descriptorIndex;
//				String text;
//				byte[] bytes;
//
//				switch (constantType) {
//
//				case CONSTANT_Utf8:
//					bytes = new byte[2];
//
//					bytecodes.read(bytes, 0, 2);
//					length = u2(bytes);
//
//					bytes = new byte[length];
//					bytecodes.read(bytes, 0, length);
//					text = new String(bytes, "UTF-8");
//
//					BytecodeAnalyserView.printField(constantType
//							.constantTypeName()
//							+ " {\n\tconstant_pool_index: "
//							+ currentConstantPoolIndex
//							+ "\n\tlength: "
//							+ length + "\n\tbytes_as_text: " + text + "\n}");
//					break;
//
//				case CONSTANT_Integer:
//					bytes = new byte[4];
//					bytecodes.read(bytes, 0, 4);
//					int integerValue = ((0xFF & bytes[0]) << 24)
//							| ((0xFF & bytes[1]) << 16)
//							| ((0xFF & bytes[2]) << 8) | (0xFF & bytes[3]);
//					BytecodeAnalyserView.printField(constantType
//							.constantTypeName()
//							+ " {\n\tconstant_pool_index: "
//							+ currentConstantPoolIndex
//							+ "\n\tinteger: "
//							+ integerValue + "\n}");
//					break;
//
//				case CONSTANT_Float:
//					bytes = new byte[4];
//					bytecodes.read(bytes, 0, 4);
//					float floatValue = Float
//							.intBitsToFloat(((0xFF & bytes[0]) << 24)
//									| ((0xFF & bytes[1]) << 16)
//									| ((0xFF & bytes[2]) << 8)
//									| (0xFF & bytes[3]));
//					BytecodeAnalyserView.printField(constantType
//							.constantTypeName()
//							+ " {\n\tconstant_pool_index: "
//							+ currentConstantPoolIndex
//							+ "\n\tfloat: "
//							+ floatValue + "\n}");
//					break;
//
//				case CONSTANT_Long:
//					// NOT TESTED YET!
//					bytes = new byte[8];
//					bytecodes.read(bytes, 0, 8);
//					int highLong = ((0xFF & bytes[0]) << 24)
//							| ((0xFF & bytes[1]) << 16)
//							| ((0xFF & bytes[2]) << 8) | (0xFF & bytes[3]);
//					int lowLong = ((0xFF & bytes[4]) << 24)
//							| ((0xFF & bytes[5]) << 16)
//							| ((0xFF & bytes[6]) << 8) | (0xFF & bytes[7]);
//					BytecodeAnalyserView.printField(constantType
//							.constantTypeName()
//							+ " {\n\tconstant_pool_index: "
//							+ currentConstantPoolIndex
//							+ "\n\tlong: "
//							+ highLong + lowLong + "\n}");
//					break;
//
//				case CONSTANT_Double:
//					// NOT TESTED YET!
//					bytes = new byte[8];
//					bytecodes.read(bytes, 0, 8);
//					int highDouble = ((0xFF & bytes[0]) << 24)
//							| ((0xFF & bytes[1]) << 16)
//							| ((0xFF & bytes[2]) << 8) | (0xFF & bytes[3]);
//					int lowDouble = ((0xFF & bytes[4]) << 24)
//							| ((0xFF & bytes[5]) << 16)
//							| ((0xFF & bytes[6]) << 8) | (0xFF & bytes[7]);
//					BytecodeAnalyserView.printField(constantType
//							.constantTypeName()
//							+ " {\n\tconstant_pool_index: "
//							+ currentConstantPoolIndex
//							+ "\n\tlong: "
//							+ highDouble + "." + lowDouble + "\n}");
//					break;
//
//				case CONSTANT_Class:
//					bytes = new byte[2];
//					bytecodes.read(bytes, 0, 2);
//					nameIndex = u2(bytes);
//					BytecodeAnalyserView.printField(constantType
//							.constantTypeName()
//							+ " {\n\tconstant_pool_index: "
//							+ currentConstantPoolIndex
//							+ "\n\tname_index: "
//							+ nameIndex + "\n}");
//					break;
//
//				case CONSTANT_String:
//					bytes = new byte[2];
//					bytecodes.read(bytes, 0, 2);
//					stringIndex = u2(bytes);
//					BytecodeAnalyserView.printField(constantType
//							.constantTypeName()
//							+ " {\n\tconstant_pool_index: "
//							+ currentConstantPoolIndex
//							+ "\n\tstring_index: "
//							+ stringIndex + "\n}");
//					break;
//
//				case CONSTANT_Fieldref:
//					bytes = new byte[2];
//
//					bytecodes.read(bytes, 0, 2);
//					classIndex = u2(bytes);
//
//					bytecodes.read(bytes, 0, 2);
//					nameAndTypeIndex = u2(bytes);
//					BytecodeAnalyserView.printField(constantType
//							.constantTypeName()
//							+ " {\n\tconstant_pool_index: "
//							+ currentConstantPoolIndex
//							+ "\n\tclass_index: "
//							+ classIndex
//							+ "\n\tname_and_type_index: "
//							+ nameAndTypeIndex + "\n}");
//					break;
//				case CONSTANT_Metodref:
//					bytes = new byte[2];
//
//					bytecodes.read(bytes, 0, 2);
//					classIndex = u2(bytes);
//
//					bytecodes.read(bytes, 0, 2);
//					nameAndTypeIndex = u2(bytes);
//
//					BytecodeAnalyserView.printField(constantType
//							.constantTypeName()
//							+ " {\n\tconstant_pool_index: "
//							+ currentConstantPoolIndex
//							+ "\n\tclass_index: "
//							+ classIndex
//							+ "\n\tname_and_type_index: "
//							+ nameAndTypeIndex + "\n}");
//					break;
//
//				case CONSTANT_InterfaceMetodref:
//					bytes = new byte[2];
//
//					bytecodes.read(bytes, 0, 2);
//					classIndex = u2(bytes);
//
//					bytecodes.read(bytes, 0, 2);
//					nameAndTypeIndex = u2(bytes);
//
//					BytecodeAnalyserView.printField(constantType
//							.constantTypeName()
//							+ " {\n\tconstant_pool_index: "
//							+ currentConstantPoolIndex
//							+ "\n\tclass_index: "
//							+ classIndex
//							+ "\n\tname_and_type_index: "
//							+ nameAndTypeIndex + "\n}");
//					break;
//
//				case CONSTANT_NameAndType:
//					bytes = new byte[2];
//
//					bytecodes.read(bytes, 0, 2);
//					nameIndex = u2(bytes);
//
//					bytecodes.read(bytes, 0, 2);
//					descriptorIndex = u2(bytes);
//
//					BytecodeAnalyserView.printField(constantType
//							.constantTypeName()
//							+ " {\n\tconstant_pool_index: "
//							+ currentConstantPoolIndex
//							+ "\n\tname_index: "
//							+ nameIndex
//							+ "\n\tdescriptor_index: "
//							+ descriptorIndex + "\n}");
//					break;
//
//				case CONSTANT_Unknown:
//					BytecodeAnalyserView.printBytecode("Unkown Constant Type: "
//							+ bytecode);
//					break;
//				}
//
//				currentConstantPoolIndex++;
//			}
//
//			byte[] bytes;
//
//			BytecodeAnalyserView
//					.printBytecode("\nClass Data\n======================");
//			bytes = new byte[2];
//			bytecodes.read(bytes, 0, 2);
//			setAccesFlags(bytes);
//
//			bytes = new byte[2];
//			bytecodes.read(bytes, 0, 2);
//			setClassIndex(bytes);
//
//			bytes = new byte[2];
//			bytecodes.read(bytes, 0, 2);
//			setSuperIndex(bytes);
//
//			BytecodeAnalyserView
//					.printBytecode("\nInterfaces\n======================");
//			bytes = new byte[2];
//			bytecodes.read(bytes, 0, 2);
//			interfaceCount = u2(bytes);
//			BytecodeAnalyserView.printBytecode("interface_count: "
//					+ interfaceCount);
//
//			for (int i = 0; i < interfaceCount; i++) {
//				bytes = new byte[2];
//				bytecodes.read(bytes, 0, 2);
//				int interface_index = u2(bytes);
//				BytecodeAnalyserView.printBytecode("interface_index: "
//						+ interface_index);
//			}
//
//			BytecodeAnalyserView
//					.printBytecode("\nFields\n======================");
//			bytes = new byte[2];
//			bytecodes.read(bytes, 0, 2);
//			int fields_count = u2(bytes);
//			BytecodeAnalyserView.printBytecode("fields_count: " + fields_count);
//			for (int f = 0; f < fields_count; f++) {
//				bytes = new byte[2];
//				bytecodes.read(bytes, 0, 2);
//				int acces_flag_bits = u2(bytes);
//				Bytecodes.AccesFlag[] acces_flags = setFieldAccesFlags(acces_flag_bits);
//
//				bytes = new byte[2];
//				bytecodes.read(bytes, 0, 2);
//				int name_index = u2(bytes);
//
//				bytes = new byte[2];
//				bytecodes.read(bytes, 0, 2);
//				int descriptor_index = u2(bytes);
//
//				bytes = new byte[2];
//				bytecodes.read(bytes, 0, 2);
//				int attributes_count = u2(bytes);
//
//				String attribute_info_text = "attribute_info {";
//				for (int attr = 0; attr < attributes_count; attr++) {
//					// Only ConstantValue_attributes here!
//					// TODO: add Synthetic and Deprecated
//					/*
//					 * The value of the attribute_name_index item must be a
//					 * valid index into the constant_pool table. The
//					 * constant_pool entry at that index must be a
//					 * CONSTANT_Utf8_info (§4.4.7) structure representing the
//					 * string "Synthetic".
//					 * 
//					 * The value of the attribute_name_index item must be a
//					 * valid index into the constant_pool table. The
//					 * constant_pool entry at that index must be a
//					 * CONSTANT_Utf8_info (§4.4.7) structure representing the
//					 * string "Deprecated".
//					 */
//
//					attribute_info_text += "\n\t\tConstantValue_attribute {";
//					bytes = new byte[2];
//					bytecodes.read(bytes, 0, 2);
//					int attribute_name_index = u2(bytes);
//
//					bytes = new byte[4];
//					bytecodes.read(bytes, 0, 4);
//					int attribute_length = u4(bytes);
//
//					bytes = new byte[2];
//					bytecodes.read(bytes, 0, 2);
//					int constantvalue_index = u2(bytes);
//					attribute_info_text += "\n\t\tattribute_name_index: "
//							+ attribute_name_index + "\n\t\tattribute_length: "
//							+ attribute_length + "\n\t\tconstantvalue_index: "
//							+ constantvalue_index + "\n\t\t}";
//				}
//				attribute_info_text += "\n\t}";
//
//				String acces_flags_text = "acces_flags {";
//				for (int flag = 0; flag < acces_flags.length; flag++) {
//					if (acces_flags[flag].isSet == true)
//						acces_flags_text += "\n\t\t"
//								+ acces_flags[flag].flagName();
//				}
//				acces_flags_text += "\n\t}";
//
//				BytecodeAnalyserView.printField("field_" + f + " {\n\t"
//						+ acces_flags_text + "\n\tname_index: " + name_index
//						+ "\n\tdescriptor_index: " + descriptor_index
//						+ "\n\tattributes_count: " + attributes_count + "\n\t"
//						+ attribute_info_text + "\n}");
//
//			}
//
//			BytecodeAnalyserView
//					.printBytecode("\nMethods\n======================");
//			bytes = new byte[2];
//			bytecodes.read(bytes, 0, 2);
//			int methods_count = u2(bytes);
//			BytecodeAnalyserView.printBytecode("methods_count: "
//					+ methods_count);
//
//			for (int m = 0; m < methods_count; m++) {
//				bytes = new byte[2];
//				bytecodes.read(bytes, 0, 2);
//				int acces_flags_bits = u2(bytes);
//				Bytecodes.AccesFlag[] acces_flags = setMethodAccesFlags(acces_flags_bits);
//
//				bytecodes.read(bytes, 0, 2);
//				int name_index = u2(bytes);
//
//				bytecodes.read(bytes, 0, 2);
//				int descriptor_index = u2(bytes);
//
//				bytecodes.read(bytes, 0, 2);
//				int attributes_count = u2(bytes);
//
//				// If method_info is Native or Abstract, it must NOT contain any
//				// Code Attributes.
//				// Otherwise it must contain exactly one Code attribute
//				// TODO: add Syntethic and Deprecated
//
//				String attribute_info_text = "";
//
//				for (int attr = 0; attr < attributes_count; attr++) {
//					attribute_info_text += "Code_attribute {";
//
//					bytecodes.read(bytes, 0, 2);
//					int attribute_name_index = u2(bytes);
//					attribute_info_text += "\n\t\tattribute_info: "
//							+ attribute_name_index;
//
//					bytes = new byte[4];
//					bytecodes.read(bytes, 0, 4);
//					int attribute_length = u4(bytes);
//					attribute_info_text += "\n\t\tattribute_length: "
//							+ attribute_length;
//
//					bytes = new byte[2];
//					bytecodes.read(bytes, 0, 2);
//					int max_stacks = u2(bytes);
//					attribute_info_text += "\n\t\tmax_stacks: " + max_stacks;
//
//					bytecodes.read(bytes, 0, 2);
//					int max_locals = u2(bytes);
//					attribute_info_text += "\n\t\tmax_locals: " + max_locals;
//
//					bytes = new byte[4];
//					bytecodes.read(bytes, 0, 4);
//					int code_length = u4(bytes);
//					attribute_info_text += "\n\t\tcode_length: " + code_length;
//
//					attribute_info_text += "\n\t\tbytecodes {";
//					int[] code = new int[code_length];
//					// Here we go!
//					for (int c = 0; c < code_length; c++) {
//						code[c] = bytecodes.read();
//						attribute_info_text += "\n\t\t\tbytecode: " + code[c];
//					}
//					attribute_info_text += "\n\t\t}";
//
//					bytes = new byte[2];
//					bytecodes.read(bytes, 0, 2);
//					int exception_table_length = u2(bytes);
//
//					attribute_info_text += "\n\t\texception_table {";
//					for (int e = 0; e < exception_table_length; e++) {
//						bytecodes.read(bytes, 0, 2);
//						int start_pc = u2(bytes);
//						attribute_info_text += "\n\t\tstart_pc: " + start_pc;
//						bytecodes.read(bytes, 0, 2);
//						int end_pc = u2(bytes);
//						attribute_info_text += "\n\t\tend_pc: " + end_pc;
//						bytecodes.read(bytes, 0, 2);
//						int handler_pc = u2(bytes);
//						attribute_info_text += "\n\t\thandler_pc: "
//								+ handler_pc;
//						bytecodes.read(bytes, 0, 2);
//						int catch_type = u2(bytes);
//						attribute_info_text += "\n\t\tcatch_type: "
//								+ catch_type;
//					}
//					attribute_info_text += "\n\t}";
//
//					bytecodes.read(bytes, 0, 2);
//					attributes_count = u2(bytes);
//					attribute_info_text += "\n\t\tattributes_count: "
//							+ attributes_count;
//
//				}
//
//				String acces_flags_text = "acces_flags {";
//				for (int flag = 0; flag < acces_flags.length; flag++) {
//					if (acces_flags[flag].isSet == true)
//						acces_flags_text += "\n\t\t"
//								+ acces_flags[flag].flagName();
//				}
//				acces_flags_text += "\n\t}";
//
//				BytecodeAnalyserView.printField("method_" + m + " {\n\t"
//						+ acces_flags_text + "\n\tname_index: " + name_index
//						+ "\n\tdescriptor_index: " + descriptor_index
//						+ "\n\tattributes_count: " + attributes_count + "\n\t"
//						+ attribute_info_text + "\n}");
//
//			}
//
//		} catch (Exception e) {
//
//		}
//
//	}
//
//	private static void setAccesFlags(byte[] bytes) {
//		int acces_flags = u2(bytes);
//
//		isPublic = (acces_flags & Bytecodes.AccesFlag.ACC_PUBLIC.flagBit()) == Bytecodes.AccesFlag.ACC_PUBLIC
//				.flagBit() ? true : false;
//		isFinal = (acces_flags & Bytecodes.AccesFlag.ACC_FINAL.flagBit()) == Bytecodes.AccesFlag.ACC_FINAL
//				.flagBit() ? true : false;
//		isSuper = (acces_flags & Bytecodes.AccesFlag.ACC_SUPER.flagBit()) == Bytecodes.AccesFlag.ACC_SUPER
//				.flagBit() ? true : false;
//		isInterface = (acces_flags & Bytecodes.AccesFlag.ACC_INTERFACE
//				.flagBit()) == Bytecodes.AccesFlag.ACC_INTERFACE.flagBit() ? true
//				: false;
//		isAbstract = (acces_flags & Bytecodes.AccesFlag.ACC_ABSTRACT.flagBit()) == Bytecodes.AccesFlag.ACC_ABSTRACT
//				.flagBit() ? true : false;
//
//		BytecodeAnalyserView.printField("Acces_Flags {\n\tPublic: " + isPublic
//				+ "\n\tFinal: " + isFinal + "\n\tSuper: " + isSuper
//				+ "\n\tInterface: " + isInterface + "\n\tAbstract: "
//				+ isAbstract + "\n}");
//	}
//
//	private static Bytecodes.AccesFlag[] setFieldAccesFlags(int bytes) {
//
//		Bytecodes.AccesFlag[] acces_flags = { Bytecodes.AccesFlag.ACC_PUBLIC,
//				Bytecodes.AccesFlag.ACC_PRIVATE,
//				Bytecodes.AccesFlag.ACC_PROTECTED,
//				Bytecodes.AccesFlag.ACC_STATIC, Bytecodes.AccesFlag.ACC_FINAL,
//				Bytecodes.AccesFlag.ACC_VOLATILE,
//				Bytecodes.AccesFlag.ACC_TRANSIENT };
//
//		for (int i = 0; i < acces_flags.length; i++) {
//			acces_flags[i].isSet = (bytes & acces_flags[i].flagBit()) == acces_flags[i]
//					.flagBit() ? true : false;
//		}
//
//		return acces_flags;
//	}
//
//	private static Bytecodes.AccesFlag[] setMethodAccesFlags(int bytes) {
//
//		Bytecodes.AccesFlag[] acces_flags = { Bytecodes.AccesFlag.ACC_PUBLIC,
//				Bytecodes.AccesFlag.ACC_PRIVATE,
//				Bytecodes.AccesFlag.ACC_PROTECTED,
//				Bytecodes.AccesFlag.ACC_STATIC, Bytecodes.AccesFlag.ACC_FINAL,
//				Bytecodes.AccesFlag.ACC_SYNCHRONIZED,
//				Bytecodes.AccesFlag.ACC_NATIVE,
//				Bytecodes.AccesFlag.ACC_ABSTRACT,
//				Bytecodes.AccesFlag.ACC_STRICT };
//
//		for (int i = 0; i < acces_flags.length; i++) {
//			acces_flags[i].isSet = (bytes & acces_flags[i].flagBit()) == acces_flags[i]
//					.flagBit() ? true : false;
//		}
//
//		return acces_flags;
//	}
//
//	private static void setClassIndex(byte[] bytes) {
//		thisIndex = u2(bytes);
//
//		BytecodeAnalyserView.printField("Class_Index {\n\tindex: " + thisIndex
//				+ "\n}");
//	}
//
//	private static void setSuperIndex(byte[] bytes) {
//		superIndex = u2(bytes);
//		BytecodeAnalyserView.printField("Super_Index {\n\tindex: " + superIndex
//				+ "\n}");
//	}
//
//	private static int u2(byte[] bytes) {
//		if (bytes.length < 2)
//			throw new IllegalArgumentException("bytes must be u2");
//
//		return (((0xFF & bytes[0]) << 8) | bytes[1]);
//	}
//
//	private static int u4(byte[] bytes) {
//		if (bytes.length < 4)
//			throw new IllegalArgumentException("bytes must be u4");
//		return ((0xFF & bytes[0]) << 24) | ((0xFF & bytes[1]) << 16)
//				| ((0xFF & bytes[2]) << 8) | (0xFF & bytes[3]);
//	}
//
//	private static void cafeBabeFound() {
//		isCafeBabeFound = true;
//		currentByte = 4;
//		BytecodeAnalyserView.printBytecode("0xCAFEBABE: Found!");
//	}
//
//	public JTextPane getTextPane() {
//		return textPane;
//	}
}
