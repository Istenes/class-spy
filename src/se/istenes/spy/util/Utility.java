package se.istenes.spy.util;

/**
 *
 * @author istenes
 */
public class Utility {

    public static final int CAFEBABE = 0xCAFEBABE;

    public static enum MajorVersion {

        J2SE7("JSE 7", 51),
        J2SE60("J2SE 6.0", 50),
        J2SE50("J2SE 5.0", 49),
        JDK14("JDK 1.4", 48),
        JDK13("JDK 1.3", 47),
        JDK12("JDK 1.2", 46),
        JDK11("JDK 1.1", 45),
        Unknown("Unknown version 0",-1);
        private final String versionName;
        private final int versionByte;

        MajorVersion(String versionName, int versionByte) {
            this.versionName = versionName;
            this.versionByte = versionByte;
        }

        public static String getVersionName(int versionByte) {
            for (MajorVersion v : MajorVersion.values()) {
                if (v.versionByte == versionByte) {
                    return v.versionName;
                }
            }
            return "Unknown Major Version";
        }
        
        
        public static MajorVersion getVersion(int versionByte) {
            for (MajorVersion v : MajorVersion.values()) {
                if (v.versionByte == versionByte) {
                    return v;
                }
            }
            return Unknown;
        }

        public String versionName() {
            return versionName;
        }

        public int versionByte() {
            return versionByte;
        }
    }

    public static enum AccesFlag {

        ACC_PUBLIC("ACC_PUBLIC", 0x0001, false),
        ACC_PRIVATE("ACC_PRIVATE", 0x0002, false),
        ACC_PROTECTED("ACC_PROTECTED", 0x0004, false),
        ACC_STATIC("ACC_STATIC", 0x0008, false),
        ACC_FINAL("ACC_FINAL", 0x0010, false),
        ACC_SUPER("ACC_SUPER", 0x0020, false),
        ACC_SYNCHRONIZED("ACC_SYNCHRONIZED", 0x0020, false),
        ACC_VOLATILE("ACC_VOLATILE", 0x0040, false),
        ACC_TRANSIENT("ACC_TRANSIENT", 0x0080, false),
        ACC_NATIVE("ACC_NATIVE", 0x0100, false),
        ACC_INTERFACE("ACC_INTERFACE", 0x0200, false),
        ACC_ABSTRACT("ACC_ABSTRACT", 0x0400, false),
        ACC_STRICT("ACC_STRICT", 0x0800, false),
        ACC_UNKNOWN("ACC_UNKNOWN", 0x0000, false);
        private final String flagName;
        private final int flagBit;
        public boolean isSet;

        AccesFlag(String flagName, int flagBit, boolean isSet) {
            this.flagName = flagName;
            this.flagBit = flagBit;
            this.isSet = isSet;
        }

        public static String getFlagName(int flagBit) {
            for (AccesFlag f : AccesFlag.values()) {
                if (f.flagBit == flagBit) {
                    return f.flagName;
                }
            }
            return ACC_UNKNOWN.flagName;
        }

        public String flagName() {
            return flagName;
        }

        public int flagBit() {
            return flagBit;
        }
    }    

    public static enum ConstantType {

        CONSTANT_Utf8("CONSTANT_Utf8", 1),
        CONSTANT_Integer("CONSTANT_Integer", 3),
        CONSTANT_Float("CONSTANT_Float", 4),
        CONSTANT_Long("CONSTANT_Long", 5),
        CONSTANT_Double("CONSTANT_Double", 6),
        CONSTANT_Class("CONSTANT_Class", 7),
        CONSTANT_String("CONSTANT_String", 8),
        CONSTANT_Fieldref("CONSTANT_Fieldref", 9),
        CONSTANT_Metodref("CONSTANT_Methodref", 10),
        CONSTANT_InterfaceMetodref("CONSTANT_InterfaceMethodref", 11),
        CONSTANT_NameAndType("CONSTANT_NameAndType", 12),
        CONSTANT_Unknown("CONSTANT_Unkown", -1);
        private final String constantTypeName;
        public final int constantByte;

        ConstantType(String constantTypeName, int constantByte) {
            this.constantTypeName = constantTypeName;
            this.constantByte = constantByte;
        }

        public static String getConstantTypeName(int constantByte) {
            for (ConstantType c : ConstantType.values()) {
                if (c.constantByte == constantByte) {
                    return c.constantTypeName;
                }
            }
            return CONSTANT_Unknown.name();
        }

        public static ConstantType getConstantType(int constantByte) {
            for (ConstantType c : ConstantType.values()) {
                if (c.constantByte == constantByte) {
                    return c;
                }
            }
            return CONSTANT_Unknown;
        }

        public String constantTypeName() {
            return constantTypeName;
        }
    }
}
