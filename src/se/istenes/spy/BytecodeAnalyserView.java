//package se.istenes.spy;
///*
// * BytecodeAnalyserView.java
// */
//
//
//
//import com.studiofreaks.bytealyzer.Utility;
//import com.studiofreaks.bytealyzer.constantpool.Constant;
//import com.studiofreaks.bytealyzer.constantpool.ConstantClass;
//import com.studiofreaks.bytealyzer.constantpool.ConstantDouble;
//import com.studiofreaks.bytealyzer.constantpool.ConstantFieldref;
//import com.studiofreaks.bytealyzer.constantpool.ConstantFloat;
//import com.studiofreaks.bytealyzer.constantpool.ConstantInteger;
//import com.studiofreaks.bytealyzer.constantpool.ConstantInterfaceMethodref;
//import com.studiofreaks.bytealyzer.constantpool.ConstantLong;
//import com.studiofreaks.bytealyzer.constantpool.ConstantMethodref;
//import com.studiofreaks.bytealyzer.constantpool.ConstantPool;
//import com.studiofreaks.bytealyzer.constantpool.ConstantUtf8;
//import com.studiofreaks.bytealyzer.fields.Fields;
//import com.studiofreaks.bytealyzer.fields.Field;
//import java.awt.Component;
//import org.jdesktop.application.Action;
//import org.jdesktop.application.ResourceMap;
//import org.jdesktop.application.SingleFrameApplication;
//import org.jdesktop.application.FrameView;
//import org.jdesktop.application.TaskMonitor;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.io.FileInputStream;
//import java.io.InputStream;
//import javax.swing.Timer;
//import javax.swing.Icon;
//import javax.swing.JDialog;
//import javax.swing.JFrame;
//import javax.swing.JTree;
//import javax.swing.tree.DefaultMutableTreeNode;
//import javax.swing.tree.DefaultTreeModel;
//import javax.swing.tree.TreeModel;
//import javax.swing.tree.TreeNode;
//
///**
// * The application's main frame.
// */
//public class BytecodeAnalyserView extends FrameView {
//
//    public BytecodeAnalyserView(SingleFrameApplication app) {
//        super(app);
//
//        initComponents();
//        // status bar initialization - message timeout, idle icon and busy animation, etc
//        ResourceMap resourceMap = getResourceMap();
//        int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
//        messageTimer = new Timer(messageTimeout, new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//            }
//        });
//        messageTimer.setRepeats(false);
//        int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
//        for (int i = 0; i < busyIcons.length; i++) {
//            busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
//        }
//        busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
//            }
//        });
//        idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
//
//        // connecting action tasks to status bar via TaskMonitor
//        TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
//        taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
//            public void propertyChange(java.beans.PropertyChangeEvent evt) {
//                String propertyName = evt.getPropertyName();
//                if ("started".equals(propertyName)) {
//                    if (!busyIconTimer.isRunning()) {
//                        busyIconIndex = 0;
//                        busyIconTimer.start();
//                    }
//                } else if ("done".equals(propertyName)) {
//                    busyIconTimer.stop();
//                } else if ("message".equals(propertyName)) {
//                    String text = (String)(evt.getNewValue());
//                    messageTimer.restart();
//                } else if ("progress".equals(propertyName)) {
//                    int value = (Integer)(evt.getNewValue());
//                }
//            }
//        });
//    }
//    
//    public static void printBytecode(String s){
//        textField.append(s+"\n");
//    }
//        
//    public static void printHeadline(String s){
//        textField.append(s+"\n======================\n");
//    }
//    
//        
//    public static void printField(String s){
//        textField.append(s+"\n\n");
//    }
//    
//    public static void fillTree(com.studiofreaks.bytealyzer.ClassFile classFile){
//        DefaultMutableTreeNode root = new DefaultMutableTreeNode(classFile.getName());
//        
//        DefaultMutableTreeNode classInfo =  new DefaultMutableTreeNode("Class Information");
//        DefaultMutableTreeNode cafebabe = new DefaultMutableTreeNode("<html>0xCAFEBABE: <b>"+classFile.getCafebabe()+"</b></html>");
//        DefaultMutableTreeNode version = new DefaultMutableTreeNode("<html>version: <b>"+classFile.getVersion()+"</b></html>");
//        DefaultMutableTreeNode thisClassIndex = new DefaultMutableTreeNode("<html>this_class_index: <b>"+classFile.getThisClassIndex()+"</b></html>");
//        DefaultMutableTreeNode thisClassName = new DefaultMutableTreeNode("<html>this_class_name: <b>"+classFile.getThisClassName()+"</b></html>");
//        DefaultMutableTreeNode superClassIndex = new DefaultMutableTreeNode("<html>super_class_index: <b>"+classFile.getSuperClassIndex()+"</b></html>");
//        DefaultMutableTreeNode superClassName = new DefaultMutableTreeNode("<html>super_class_name: <b>"+classFile.getSuperClassName()+"</b></html>");
//        DefaultMutableTreeNode accesFlags =  new DefaultMutableTreeNode("Acces Flags");
//        for(int accesFlag = 0;accesFlag<classFile.getClassAccesFlags().length;accesFlag++){
//            accesFlags.add(new DefaultMutableTreeNode(classFile.getClassAccesFlags()[accesFlag].flagName()));
//        }
//        classInfo.add(cafebabe);
//        classInfo.add(version);
//        classInfo.add(thisClassIndex);
//        classInfo.add(thisClassName);
//        classInfo.add(superClassIndex);
//        classInfo.add(superClassName);
//        classInfo.add(accesFlags);
//        root.add(classInfo);
//        
//        ConstantPool constantPool = classFile.getConstantPool();
//        DefaultMutableTreeNode constantPoolNode =  new DefaultMutableTreeNode("Constant Pool");
//        int constantPoolCount = constantPool.getConstantPoolCount();
//        DefaultMutableTreeNode constantPoolCountNode =  new DefaultMutableTreeNode("<html>constant_pool_count: <b>"+classFile.getConstantPool().getConstantPoolCount()+"</b></html>");
//        constantPoolNode.add(constantPoolCountNode);
//        for(int c = 1;c<=constantPoolCount;c++){
//            Constant constant = constantPool.getConstant(c);
//            Utility.ConstantType type = Utility.ConstantType.getConstantType(constant.tag);
//            DefaultMutableTreeNode constantNode = new DefaultMutableTreeNode(type.constantTypeName()+"_"+constant.index);
//            for(int attr = 0;attr<constant.attributes.size();attr++ ){
//                constantNode.add(new DefaultMutableTreeNode("<html>"+constant.attributes.get(attr).attributeName+": <b>"+constant.attributes.get(attr).attributeValue.toString()+"</b></html>"));
//            }
//            constantPoolNode.add(constantNode);
//        }
//        
//        DefaultMutableTreeNode interfaceNode = new DefaultMutableTreeNode("Interfaces");
//        interfaceNode.add(new DefaultMutableTreeNode("<html>interface_count: <b>"+classFile.getInterfacesCount()+"</b></html>"));
//        for(int i = 0;i<classFile.getInterfacesCount();i++){
//            interfaceNode.add(new DefaultMutableTreeNode("<html>"+classFile.getInterfaceNames()[i]+": <b>"+classFile.getInterfaceIndexes()[i]+"</b></html>"));
//        }
//        root.add(constantPoolNode);
//        
//        DefaultMutableTreeNode fieldsNode = new DefaultMutableTreeNode("Fields");
//        Fields fields = classFile.getFields();
//        final int fieldsCount = fields.getFieldCount();
//        interfaceNode.add(new DefaultMutableTreeNode("<html>fields_count: <b>"+fieldsCount+"</b></html>"));
//        for(int f = 0;f<fieldsCount;f++){
//            Field field = fields.getFiled(f);
//            interfaceNode.add(new DefaultMutableTreeNode("<html>fields_count: <b>"+field+"</b></html>"));
//        }
//        
//        root.add(interfaceNode);
//        
//        
//        
//        
//        treeStructure.setModel(new javax.swing.tree.DefaultTreeModel(root));
//    }
//
//    /** This method is called from within the constructor to
//     * initialize the form.
//     * WARNING: Do NOT modify this code. The content of this method is
//     * always regenerated by the Form Editor.
//     */
//    @SuppressWarnings("unchecked")
//    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
//    private void initComponents() {
//
//        mainPanel = new javax.swing.JPanel();
//        jTabbedPane1 = new javax.swing.JTabbedPane();
//        jScrollPane1 = new javax.swing.JScrollPane();
//        textField = new javax.swing.JTextArea();
//        jScrollPane2 = new javax.swing.JScrollPane();
//        treeStructure = new javax.swing.JTree();
//        menuBar = new javax.swing.JMenuBar();
//        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
//        itemLoadFile = new javax.swing.JMenuItem();
//        jSeparator1 = new javax.swing.JPopupMenu.Separator();
//        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
//        jFileChooser1 = new javax.swing.JFileChooser();
//
//        mainPanel.setName("mainPanel"); // NOI18N
//
//        jTabbedPane1.setName("jTabbedPane1"); // NOI18N
//
//        jScrollPane1.setName("jScrollPane1"); // NOI18N
//
//        textField.setColumns(20);
//        textField.setEditable(false);
//        textField.setRows(5);
//        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance().getContext().getResourceMap(BytecodeAnalyserView.class);
//        textField.setText(resourceMap.getString("textField.text")); // NOI18N
//        textField.setName("textField"); // NOI18N
//        jScrollPane1.setViewportView(textField);
//
//        jTabbedPane1.addTab(resourceMap.getString("jScrollPane1.TabConstraints.tabTitle"), jScrollPane1); // NOI18N
//
//        jScrollPane2.setName("jScrollPane2"); // NOI18N
//
//        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("No .class file loaded.");
//        treeStructure.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
//        treeStructure.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
//        treeStructure.setName("treeStructure"); // NOI18N
//        jScrollPane2.setViewportView(treeStructure);
//        treeStructure.getAccessibleContext().setAccessibleName(resourceMap.getString("jTree1.AccessibleContext.accessibleName")); // NOI18N
//
//        jTabbedPane1.addTab(resourceMap.getString("jScrollPane2.TabConstraints.tabTitle"), jScrollPane2); // NOI18N
//
//        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
//        mainPanel.setLayout(mainPanelLayout);
//        mainPanelLayout.setHorizontalGroup(
//            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGroup(mainPanelLayout.createSequentialGroup()
//                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 640, javax.swing.GroupLayout.PREFERRED_SIZE)
//                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
//        );
//        mainPanelLayout.setVerticalGroup(
//            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGroup(mainPanelLayout.createSequentialGroup()
//                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)
//                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
//        );
//
//        jTabbedPane1.getAccessibleContext().setAccessibleName(resourceMap.getString("jTabbedPane1.AccessibleContext.accessibleName")); // NOI18N
//
//        menuBar.setName("menuBar"); // NOI18N
//
//        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
//        fileMenu.setName("fileMenu"); // NOI18N
//
//        itemLoadFile.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
//        itemLoadFile.setText(resourceMap.getString("itemLoadFile.text")); // NOI18N
//        itemLoadFile.setName("itemLoadFile"); // NOI18N
//        itemLoadFile.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                itemLoadFileActionPerformed(evt);
//            }
//        });
//        fileMenu.add(itemLoadFile);
//
//        jSeparator1.setName("jSeparator1"); // NOI18N
//        fileMenu.add(jSeparator1);
//
//        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance().getContext().getActionMap(BytecodeAnalyserView.class, this);
//        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
//        exitMenuItem.setName("exitMenuItem"); // NOI18N
//        fileMenu.add(exitMenuItem);
//
//        menuBar.add(fileMenu);
//
//        jFileChooser1.setName("jFileChooser1"); // NOI18N
//        jFileChooser1.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                jFileChooser1ActionPerformed(evt);
//            }
//        });
//
//        setComponent(mainPanel);
//        setMenuBar(menuBar);
//    }// </editor-fold>//GEN-END:initComponents
//
//private void itemLoadFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemLoadFileActionPerformed
//jFileChooser1.showDialog(this.mainPanel, null);
//}//GEN-LAST:event_itemLoadFileActionPerformed
//
//private void jFileChooser1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFileChooser1ActionPerformed
//    textField.setText("");
//    BytecodeAnalyserApp.readClassFile(jFileChooser1.getSelectedFile());
//}//GEN-LAST:event_jFileChooser1ActionPerformed
//
//    // Variables declaration - do not modify//GEN-BEGIN:variables
//    private javax.swing.JMenuItem itemLoadFile;
//    private javax.swing.JFileChooser jFileChooser1;
//    private javax.swing.JScrollPane jScrollPane1;
//    private javax.swing.JScrollPane jScrollPane2;
//    private javax.swing.JPopupMenu.Separator jSeparator1;
//    private javax.swing.JTabbedPane jTabbedPane1;
//    private javax.swing.JPanel mainPanel;
//    private javax.swing.JMenuBar menuBar;
//    private static javax.swing.JTextArea textField;
//    private static javax.swing.JTree treeStructure;
//    // End of variables declaration//GEN-END:variables
//
//    private final Timer messageTimer;
//    private final Timer busyIconTimer;
//    private final Icon idleIcon;
//    private final Icon[] busyIcons = new Icon[15];
//    private int busyIconIndex = 0;
//
//    private JDialog aboutBox;
//}
