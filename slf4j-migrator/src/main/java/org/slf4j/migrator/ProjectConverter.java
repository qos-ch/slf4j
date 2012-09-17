/**
 * Copyright (c) 2004-2011 QOS.ch
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
 *
 */
package org.slf4j.migrator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.SwingUtilities;

import org.slf4j.migrator.internal.MigratorFrame;
import org.slf4j.migrator.internal.ProgressListener;
import org.slf4j.migrator.line.RuleSet;

public class ProjectConverter {

  private RuleSet ruleSet;
  private List<ConversionException> exception;

  ProgressListener progressListener;

  public static void main(String[] args) throws IOException {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        MigratorFrame inst = new MigratorFrame();
        inst.setLocationRelativeTo(null);
        inst.setVisible(true);
      }
    });
  }

  /**
   * Ask for concrete matcher implementation depending on the conversion mode
   * Ask for user confirmation to convert the selected source directory if valid
   * Ask for user confirmation in case of number of files to convert > 1000
   *
   * @param conversionType 
   * @param progressListener 
   */
  public ProjectConverter(int conversionType, ProgressListener progressListener) {
    this.progressListener = progressListener;
    ruleSet = RuleSetFactory.getMatcherImpl(conversionType);
    if (ruleSet == null) {
      addException(new ConversionException(ConversionException.NOT_IMPLEMENTED));
    }
  }

  public void convertProject(File folder) {
    FileSelector fs = new FileSelector(progressListener);
    List<File> fileList = fs.selectJavaFilesInFolder(folder);
    scanFileList(fileList);
    progressListener.onDone();
  }

  /**
   * Convert a list of files
   * 
   * @param lstFiles
   */
  private void scanFileList(List<File> lstFiles) {
    progressListener.onFileScanBegin();
    Iterator<File> itFile = lstFiles.iterator();
    while (itFile.hasNext()) {
      File currentFile = itFile.next();
      progressListener.onFileScan(currentFile);
      scanFile(currentFile);
    }
  }

  /**
   * Convert the specified file Read each line and ask matcher implementation
   * for conversion Rewrite the line returned by matcher
   * 
   * @param file
   */
  private void scanFile(File file) {
    try {
      InplaceFileConverter fc = new InplaceFileConverter(ruleSet,
          progressListener);
      fc.convert(file);
    } catch (IOException exc) {
      addException(new ConversionException(exc.toString()));
    }
  }

  public void addException(ConversionException exc) {
    if (exception == null) {
      exception = new ArrayList<ConversionException>();
    }
    exception.add(exc);
  }

  public void printException() {
    if (exception != null) {
      Iterator<ConversionException> iterator = exception.iterator();
      while (iterator.hasNext()) {
        ConversionException exc = (ConversionException) iterator.next();
        exc.print();
      }
      exception = null;
    }
  }
}