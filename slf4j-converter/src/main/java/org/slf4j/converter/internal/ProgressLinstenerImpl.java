package org.slf4j.converter.internal;

import java.io.File;
import java.io.IOException;

import javax.swing.JLabel;

import org.slf4j.converter.helper.Abbreviator;

public class ProgressLinstenerImpl implements ProgressListener {

  int addFileCount = 0;
  int conversionCount = 0;
  final JLabel jLabel;
  
  Abbreviator abbr;
  
  public ProgressLinstenerImpl(File projectFolder, JLabel jLabel) {
    this.jLabel = jLabel;
    this.abbr = new Abbreviator((int) projectFolder.length(), 50, File.separatorChar);
  }
  
  
  public void onDirectory(File file) {
    String abbreviatedName;
    try {
      abbreviatedName = abbr.abbreviate(file.getCanonicalPath());
    } catch (IOException e) {
      abbreviatedName = file.toString();
    }
    jLabel.setText("Searching folder ["+abbreviatedName+"]");
  }

  public void onDone() {
  }

  public void onFileAddition(File file) {
    addFileCount++;
  }

  public void onFileConversion(File file) {
    // TODO Auto-generated method stub

  }

}
