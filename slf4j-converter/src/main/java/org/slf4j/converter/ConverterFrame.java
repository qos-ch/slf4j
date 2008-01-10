package org.slf4j.converter;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ConverterFrame extends JFrame implements ActionListener {

  private static final long serialVersionUID = -1535809047324769347L;

  private JButton butCancel;
  private JButton butNext;
  private JComboBox combo;
  private JPanel principalPan;
  private JPanel conversionPan;
  private JTextArea console;
  private JScrollPane consolePan;

  private String conversionMode[] = { "JCL to SLF4J", "Log4J to SLF4J" };

  public ConverterFrame() {
    buildFrame();

    ConverterStream printStream = new ConverterStream(System.out);
    printStream.setOut(console);
    System.setOut(printStream);
  }

  private void buildFrame() {
    setTitle("SLF4J CONVERTER");
    // setIconImage()
    setLocationRelativeTo(null);
    this.setPreferredSize(new Dimension(512, 384));
    setResizable(false);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    buildComponents();
  }

  private void buildComponents() {
    principalPan = new JPanel();
    principalPan.setLayout(new BorderLayout());

    conversionPan = new JPanel(new GridLayout(2, 2, 10, 10));
    JLabel lab = new JLabel("Conversion Mode");
    conversionPan.add(lab);

    combo = new JComboBox(conversionMode);
    conversionPan.add(combo);

    butCancel = new JButton("Cancel");
    butCancel.addActionListener(this);
    conversionPan.add(butCancel);

    butNext = new JButton("Next");
    butNext.addActionListener(this);
    conversionPan.add(butNext);

    consolePan = new JScrollPane();
    console = new JTextArea();
    console.setEditable(false);
    console.setLineWrap(true);
    console.setMargin(new Insets(5, 5, 5, 5));
    consolePan.setViewportView(console);

    principalPan.add(conversionPan, BorderLayout.NORTH);
    principalPan.add(consolePan, BorderLayout.CENTER);
    setContentPane(principalPan);
    pack();
  }

  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == butCancel) {
      dispose();
    } else if (e.getSource() == butNext) {
      showFolderSelector();
    }
  }

  private void showFolderSelector() {
    JFileChooser selector = new JFileChooser();
    selector.setDialogTitle("Source folder selector");
    selector.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    int res = selector.showOpenDialog(this);
    if (res == JFileChooser.APPROVE_OPTION) {
      File folder = selector.getSelectedFile();
      ProjectConverter converter = new ProjectConverter(combo
          .getSelectedIndex());
      showConfirmDialog(converter, folder);
    }
  }

  private void showConfirmDialog(ProjectConverter converter, File folder) {
    int reponse = JOptionPane.showConfirmDialog(null,
        "RUNNING CONVERTER WILL REPLACE JAVA FILES INTO " + folder,
        "CONVERSION CONFIRMATION", JOptionPane.YES_NO_OPTION);
    if (reponse == JOptionPane.YES_OPTION) {
      converter.convertProject(folder);
      converter.printException();
    } else {
      dispose();
    }
  }

  private class ConverterStream extends PrintStream {

    JTextArea console;

    public ConverterStream(OutputStream out) {
      super(out);
    }

    public void setOut(JTextArea console) {
      this.console = console;
    }

    public void println(String string) {
      console.setText(console.getText() + string + "\n");
    }
  }

}