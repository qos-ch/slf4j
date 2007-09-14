package org.slf4j.converter;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ConverterFrame extends JFrame implements ActionListener {

  private Converter converter;
  private JButton butCancel;
  private JButton butNext;
  private JComboBox combo;
  private JPanel principalPan;
  private JPanel confirmationPan;
  private JButton butYes;
  private JButton butNo;

  public ConverterFrame(Converter parent) {
    this.converter = parent;

    setTitle("SLF4J CONVERTER");
    // setIconImage();

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    setPreferredSize(new Dimension(300, 200));
    setLocation(100, 100);

    principalPan = new JPanel();
    FlowLayout fl = new FlowLayout();
    principalPan.setLayout(fl);

    confirmationPan = new JPanel();
    confirmationPan.setLayout(fl);

    JLabel lab = new JLabel("Conversion Mode");
    principalPan.add(lab);
    String choice[] = { "JCL to SLF4J", "Log4J to SLF4J" };
    combo = new JComboBox(choice);
    principalPan.add(combo);

    butCancel = new JButton("Cancel");
    butCancel.addActionListener(this);
    principalPan.add(butCancel);
    butNext = new JButton("Next");
    butNext.addActionListener(this);
    principalPan.add(butNext);

    setContentPane(principalPan);
    pack();
    setVisible(true);
  }

  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == butCancel) {
      dispose();
    } else if (e.getSource() == butNext) {
      showFolderSelector();
    } else if (e.getSource() == butYes) {
      converter.convert();
      dispose();
    } else if (e.getSource() == butNo) {
      dispose();
    }
  }


  public void showFolderSelector() {
    JFileChooser selector = new JFileChooser();
    selector.setDialogTitle("Source folder selector");
    selector.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    int res = selector.showOpenDialog(this);
    if (res == JFileChooser.APPROVE_OPTION) {
      File folder = selector.getSelectedFile();
      if (!converter.init(folder.getAbsolutePath(), combo.getSelectedIndex())) {
        return;
      } else {
        showFileConversionConfirmationPan(converter.selectFiles());
      }
    }
  }

  public void showFileConversionConfirmationPan(int nbFiles) {
    JLabel lab;
    lab = new JLabel("RUNNING CONVERTER WILL REPLACE " + nbFiles
        + " JAVA FILES CONTAINED IN SELECTED FOLDER, DO YOU WANT TO CONTINUE ?");
    confirmationPan.add(lab);
    butNo = new JButton("No");
    butNo.addActionListener(this);
    confirmationPan.add(butNo);
    butYes = new JButton("Yes");
    butYes.addActionListener(this);
    confirmationPan.add(butYes);
    principalPan.add(confirmationPan);
    setSize(900, 200);
  }

}