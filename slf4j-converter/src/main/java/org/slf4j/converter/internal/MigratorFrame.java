package org.slf4j.converter.internal;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import org.slf4j.converter.Constant;
import org.slf4j.converter.helper.SpringLayoutHelper;

/**
 * This code was edited or generated using CloudGarden's Jigloo SWT/Swing GUI
 * Builder, which is free for non-commercial use. If Jigloo is being used
 * commercially (ie, by a corporation, company or business for any purpose
 * whatever) then you should purchase a license for each developer using Jigloo.
 * Please visit www.cloudgarden.com for details. Use of Jigloo implies
 * acceptance of these licensing terms. A COMMERCIAL LICENSE HAS NOT BEEN
 * PURCHASED FOR THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED LEGALLY FOR
 * ANY CORPORATE OR COMMERCIAL PURPOSE.
 */
public class MigratorFrame extends JFrame implements ActionListener {
  private static final long serialVersionUID = 1L;

  private static final int BASIC_PADDING = 10;
  private static final int FOLDER_COLUMNS = 40;
  private static final String MIGRATE_COMMAND = "MIGRATE_COMMAND";
  private static final String BROWSE_COMMAND = "BROWSE_COMMAND";
  static final String EXIT_COMMAND = "EXIT_COMMAND";

  static final int X_SIZE = 700;
  static final int Y_SIZE = 400;

  private SpringLayout layoutManager = new SpringLayout();
  private SpringLayoutHelper slh = new SpringLayoutHelper(layoutManager,
      BASIC_PADDING);

  private JLabel slf4jLabel;
  private JLabel migrationLabel;

  private JRadioButton radioLog4j;
  private JRadioButton radioJCL;
  private ButtonGroup buttonGroup;

  private JTextField folderTextField;
  private JLabel warningLabel;
  JButton migrateButton;
  private JButton browseButton;
  private JLabel folderLabel;

  private JCheckBox awareCheckBox;
  private JLabel awareLabel;

  JLabel otherLabel;
  JProgressBar progressBar;
  private JFileChooser fileChooser;

  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        MigratorFrame inst = new MigratorFrame();
        inst.setLocationRelativeTo(null);
        inst.setVisible(true);
      }
    });
  }

  public MigratorFrame() {
    super();
    initGUI();
  }

  private void initGUI() {
    try {
      setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
      getContentPane().setLayout(layoutManager);
      this.setTitle("SLF4J migrator");

      createComponents();
      constrainAll();
      addAllComponentsToContextPane();
      pack();
      this.setSize(700, 400);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void createComponents() {
    createSLF4JLabel();
    createMigrationLabel();
    createRadioJCL();
    createRadioLog4j();
    createButtonGroup();
    createFolderLabel();
    createFolderTextField();
    createBrowseButton();
    createMigrateButton();
    createAwareCheckbox();
    createAwareLabel();
    createWarningLabel();
    createFileChooser();

    otherLabel = new JLabel();
    otherLabel.setText("");
    createProgressBar();

  }

  /**
   * 
   */
  private void constrainAll() {

    // contrain migration label
    layoutManager.putConstraint(SpringLayout.WEST, migrationLabel,
        BASIC_PADDING, SpringLayout.EAST, this);

    layoutManager.putConstraint(SpringLayout.NORTH, migrationLabel,
        BASIC_PADDING, SpringLayout.NORTH, this);

        
    slh.placeToTheRight(migrationLabel, radioJCL, BASIC_PADDING,
        -BASIC_PADDING / 2);
    slh.placeBelow(radioJCL, radioLog4j, 0, 0);

    slh.placeToTheRight(radioJCL, slf4jLabel, BASIC_PADDING*2, 0);
    
    slh.placeBelow(migrationLabel, folderLabel, 0, BASIC_PADDING * 5);
    slh.placeToTheRight(folderLabel, folderTextField);
    slh.placeToTheRight(folderTextField, browseButton, BASIC_PADDING,
        -BASIC_PADDING / 2);

    slh.placeBelow(folderLabel, warningLabel, 0, BASIC_PADDING * 3);

    slh.placeBelow(warningLabel, awareCheckBox, 0, (int) (BASIC_PADDING * 1.5));
    slh.placeToTheRight(awareCheckBox, awareLabel);

    slh.placeBelow(awareCheckBox, migrateButton, 0, BASIC_PADDING * 3);

    slh.placeBelow(migrateButton, otherLabel, 0, BASIC_PADDING * 2);

    slh.placeBelow(otherLabel, progressBar, 0, BASIC_PADDING);
  }

  private void addAllComponentsToContextPane() {
    getContentPane().add(slf4jLabel);
    getContentPane().add(migrationLabel);
    getContentPane().add(radioJCL);
    getContentPane().add(radioLog4j);

    getContentPane().add(folderLabel);
    getContentPane().add(folderTextField);
    getContentPane().add(browseButton);
    getContentPane().add(migrateButton);

    getContentPane().add(awareCheckBox);
    getContentPane().add(awareLabel);

    getContentPane().add(warningLabel);

    getContentPane().add(otherLabel);
    getContentPane().add(progressBar);
  }

  private void createButtonGroup() {
    buttonGroup = new ButtonGroup();
    buttonGroup.add(radioJCL);
    buttonGroup.add(radioLog4j);
  }

  private void createSLF4JLabel() {
    String resoucePath = "/images/slf4j-small.jpg";

    java.net.URL imgURL = this.getClass().getResource(resoucePath);
    if (imgURL != null) {
      ImageIcon icon = new ImageIcon(imgURL);
      slf4jLabel = new JLabel("", icon, JLabel.CENTER);
    } else {
      slf4jLabel = new JLabel("");
    }
  }

  private void createMigrationLabel() {
    migrationLabel = new JLabel();
    migrationLabel.setText("Migration Type");
  }

  private void createRadioJCL() {
    radioJCL = new JRadioButton();
    radioJCL.setText("from Jakarta Commons Logging to SLF4J");
    radioJCL
        .setToolTipText("Select this button if you wish to migrate a Java project using Jakarta Commons Logging to use SLF4J.");
  }

  private void createRadioLog4j() {
    radioLog4j = new JRadioButton();
    radioLog4j.setText("from log4j to SLF4J ");
    radioLog4j
        .setToolTipText("Select this button if you wish to migrate a Java project using log4j to use SLF4J.");
  }

  private void createFolderLabel() {
    folderLabel = new JLabel();
    folderLabel.setText("Project Directory");
  }

  private void createFolderTextField() {
    folderTextField = new JTextField();
    folderTextField.setColumns(FOLDER_COLUMNS);
  }

  private void createBrowseButton() {
    browseButton = new JButton();
    browseButton.setText("Browse");
    browseButton.addActionListener(this);
    browseButton.setActionCommand(BROWSE_COMMAND);
    browseButton
        .setToolTipText("Click this button to browse the file systems on your computer.");
  }

  private void createAwareCheckbox() {
    awareCheckBox = new JCheckBox();
    awareCheckBox
        .setToolTipText("<html><p>Check this box of you understand that the migration tool<p>will <b>not</b> backup your Java source files.</html>");
  }

  private void createAwareLabel() {
    awareLabel = new JLabel();
    awareLabel
        .setText("<html>"
            + "<p>I am aware that this tool will directly modify all Java source files</p>"
            + "<p>in the selected folder without creating backup files.</p>"
            + "</html>");
  }

  private void createWarningLabel() {
    warningLabel = new JLabel();
    warningLabel
        .setText("<html>"
            + "<p><span color=\"red\">WARNING:</span> This SLF4J migration tool will directly modify all Java source files</p>"
            + "<p>in the selected project without creating a backup of the original files.</p>"
            + "</html>");
  }

  private void createMigrateButton() {
    migrateButton = new JButton();
    migrateButton.setText("Migrate Project to SLF4J");
    migrateButton
        .setToolTipText("Click this button to initiate migration of your project.");
    migrateButton.addActionListener(this);
    migrateButton.setActionCommand(MIGRATE_COMMAND);
  }

  private void createFileChooser() {
    fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Source folder selector");
    fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
  }

  private void createProgressBar() {
    progressBar = new JProgressBar(0, 1);
    progressBar
        .setPreferredSize(new java.awt.Dimension((int) (X_SIZE * 0.8), 5));
    progressBar.setVisible(false);
  }

  public void disableInput() {
    radioJCL.setEnabled(false);
    radioLog4j.setEnabled(false);

    browseButton.setEnabled(false);

    folderTextField.setEnabled(false);
    awareCheckBox.setEnabled(false);
    migrateButton.setText("Migration in progress");
    migrateButton.setEnabled(false);

  }

  public void actionPerformed(ActionEvent e) {

    if (MIGRATE_COMMAND.equals(e.getActionCommand())) {

      List<String> errorList = doSanityAnalysis();
      if (errorList.size() > 0) {
        showDialogBox(errorList);
      } else {

        File projectFolder = new File(folderTextField.getText());
        ConversionTask task = new ConversionTask(projectFolder, this,
            Constant.EMPTY_RULE_SET);
        task.launch();
      }
    } else if (BROWSE_COMMAND.equals(e.getActionCommand())) {
      showFileChooser();
    } else if (EXIT_COMMAND.equals(e.getActionCommand())) {
      this.dispose();
    }
  }

  void showFileChooser() {
    int returnVal = fileChooser.showOpenDialog(this);
    if (returnVal == JFileChooser.APPROVE_OPTION) {
      File selectedFile = fileChooser.getSelectedFile();
      System.out.println(selectedFile);
      folderTextField.setText(selectedFile.getAbsolutePath());
    }
  }

  List<String> doSanityAnalysis() {

    List<String> errorList = new ArrayList<String>();
    if (!radioJCL.isSelected() && !radioLog4j.isSelected()) {
      errorList
          .add("Please select the migration type (JCL to SLF4J or log4j to SLF4J)");
    }

    String folder = folderTextField.getText();

    if (folder == null || folder.length() == 0) {
      errorList.add("Please select the folder of the project to migrate");
    } else if (!isDirectory(folder)) {
      errorList.add("[" + folder + "] does not look like a valid folder");
    }

    if (!awareCheckBox.isSelected()) {
      errorList
          .add("Cannot initiate migration unless you acknowledge<p>that files will be modified without creating backup files");
    }
    return errorList;
  }

  void showDialogBox(List<String> errorList) {
    StringBuffer buf = new StringBuffer();
    buf.append("<html>");
    int i = 1;
    for (String msg : errorList) {
      buf.append("<p>");
      buf.append(i);
      buf.append(". ");
      buf.append(msg);
      buf.append("</p>");
      i++;
    }
    buf.append("</html>");

    JOptionPane.showMessageDialog(this, buf.toString(), "",
        JOptionPane.ERROR_MESSAGE);
  }

  boolean isDirectory(String filename) {
    if (filename == null) {
      return false;
    }
    File file = new File(filename);
    if (file.exists() && file.isDirectory()) {
      return true;
    } else {
      return false;
    }
  }
}
