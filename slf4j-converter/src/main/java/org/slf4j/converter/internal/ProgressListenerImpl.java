package org.slf4j.converter.internal;

import java.io.File;
import java.io.IOException;

import org.slf4j.converter.helper.Abbreviator;

public class ProgressListenerImpl implements ProgressListener {

  static final int TARGET_FILE_LENGTH = 85;
  int addFileCount = 0;
  int scanFileCount = 0;
  int inplaceConversionCount = 0;
  final MigratorFrame frame;

  Abbreviator abbr;

  public ProgressListenerImpl(File projectFolder, MigratorFrame frame) {
    this.frame = frame;
    this.abbr = new Abbreviator((int) projectFolder.length(),
        TARGET_FILE_LENGTH, File.separatorChar);
  }

  public void onMigrationBegin() {
    frame.disableInput();
  }
  
  public void onDirectory(File file) {
    String abbreviatedName = getShortName(file);
    frame.otherLabel.setText("<html><p>Searching folder [" + abbreviatedName
        + "]<p>Found " + addFileCount + " java files to scan.</html>");
  }

  public void onDone() {
    frame.otherLabel.setText("<html><font color='BLUE'>Scanned " + addFileCount + " java files, "+inplaceConversionCount+" files were modified.</font></html>");

    System.out.println("<html><FONT COLOR=BLUE>Scanned "+addFileCount+", converted="+inplaceConversionCount);
    frame.progressBar.setVisible(false);
  }

  public void onFileAddition(File file) {
    addFileCount++;
  }

  public void onFileScan(File file) {
    String abbreviatedName = getShortName(file);
    scanFileCount++;
    
    frame.otherLabel.setText("<html><p>Scanning file [" + abbreviatedName + "]<p>File "
        + scanFileCount + " out of "+ addFileCount+" files to scan."+ inplaceConversionCount+ " files converted.</html>");
    frame.progressBar.setValue(scanFileCount);
  }

  public void onInplaceConversion(File file) {
    inplaceConversionCount++;
  }
  
  String getShortName(File file) {
    try {
      return abbr.abbreviate(file.getCanonicalPath());
    } catch (IOException e) {
      return file.toString();
    }
  }

  public void onFileScanBegin() {
    frame.progressBar.setMaximum(addFileCount);
    frame.progressBar.setValue(0);
    frame.progressBar.setVisible(true);
  }


}
