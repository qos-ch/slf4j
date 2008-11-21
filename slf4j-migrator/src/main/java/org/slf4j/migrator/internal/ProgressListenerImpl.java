/* 
 * Copyright (c) 2004-2008 QOS.ch
 * All rights reserved.
 * 
 * Permission is hereby granted, free  of charge, to any person obtaining
 * a  copy  of this  software  and  associated  documentation files  (the
 * "Software"), to  deal in  the Software without  restriction, including
 * without limitation  the rights to  use, copy, modify,  merge, publish,
 * distribute,  sublicense, and/or sell  copies of  the Software,  and to
 * permit persons to whom the Software  is furnished to do so, subject to
 * the following conditions:
 * 
 * The  above  copyright  notice  and  this permission  notice  shall  be
 * included in all copies or substantial portions of the Software.
 * 
 * THE  SOFTWARE IS  PROVIDED  "AS  IS", WITHOUT  WARRANTY  OF ANY  KIND,
 * EXPRESS OR  IMPLIED, INCLUDING  BUT NOT LIMITED  TO THE  WARRANTIES OF
 * MERCHANTABILITY,    FITNESS    FOR    A   PARTICULAR    PURPOSE    AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE,  ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
 
package org.slf4j.migrator.internal;

import java.io.File;
import java.io.IOException;

import org.slf4j.migrator.helper.Abbreviator;

public class ProgressListenerImpl implements ProgressListener {

  static final int TARGET_FILE_LENGTH = 85;
  static final int UPDATE_THRESHOLD = 100;
  
  int addFileCount = 0;
  int scanFileCount = 0;
  int inplaceConversionCount = 0;
  final MigratorFrame frame;

  Abbreviator abbr;
  
  long lastUpdate = 0;
  
  

  public ProgressListenerImpl(File projectFolder, MigratorFrame frame) {
    this.frame = frame;
    this.abbr = new Abbreviator((int) projectFolder.length(),
        TARGET_FILE_LENGTH, File.separatorChar);
  }

  public void onMigrationBegin() {
    frame.disableInput();
  }

  boolean isTooSoon() {
    long now = System.currentTimeMillis();
    if(now-lastUpdate < UPDATE_THRESHOLD) {
      return true;
    } else {
      lastUpdate = now;
      return false;
    }
  }
 
  public void onDirectory(File file) {
    if(isTooSoon()) return;
      
    String abbreviatedName = getShortName(file);
    frame.otherLabel.setText("<html><p>Searching folder [" + abbreviatedName
        + "]<p>Found " + addFileCount + " java files to scan.</html>");
  }

  public void onDone() {
    frame.progressBar.setVisible(false);
    frame.otherLabel.setText("<html><font color='BLUE'>Scanned " + addFileCount
        + " java files, " + inplaceConversionCount
        + " files were modified.</font></html>");

    frame.migrateButton.setActionCommand(MigratorFrame.EXIT_COMMAND);
    frame.migrateButton.setText("Exit");
    frame.migrateButton
        .setToolTipText("Click on this button to exit this application.");
    frame.migrateButton.setEnabled(true);

  }

  public void onFileAddition(File file) {
    addFileCount++;
  }

  public void onFileScan(File file) {
  
    scanFileCount++;
    if(isTooSoon()) return;
    String abbreviatedName = getShortName(file);
    
    frame.otherLabel.setText("<html><p>Scanning file [" + abbreviatedName
        + "]<p></html>");
    // File + scanFileCount + " out of "+ addFileCount+" files to scan."+
    // inplaceConversionCount+ " files converted." +

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
