package org.slf4j.converter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.converter.line.LineConverter;
import org.slf4j.converter.line.RuleSet;

public class ProjectConverter {

  private LineConverter lineConverter;
  private List<ConversionException> exception;

  public static void main(String[] args) throws IOException {

    ConverterFrame frame = new ConverterFrame();
    frame.setVisible(true);
  }

  /**
   * Ask for concrete matcher implementation depending on the conversion mode
   * Ask for user confirmation to convert the selected source directory if valid
   * Ask for user confirmation in case of number of files to convert > 1000
   * 
   * @return true if init operation complete
   * @throws IOException
   */
  public ProjectConverter(int conversionType) {
    RuleSet ruleSet = RuleSetFactory.getMatcherImpl(conversionType);
    if (ruleSet == null) {
      addException(new ConversionException(ConversionException.NOT_IMPLEMENTED));
    }
    lineConverter = new LineConverter(ruleSet);
  }


  public void convertProject(File folder) {
    FileSelector fs = new FileSelector();
    List<File> fileList = fs.selectJavaFilesInFolder(folder);
    convertFileList(fileList);
  }


  /**
   * Convert a list of files
   * 
   * @param lstFiles
   */
  private void convertFileList(List<File> lstFiles) {
    Iterator<File> itFile = lstFiles.iterator();
    while (itFile.hasNext()) {
      File currentFile = itFile.next();
      convertFile(currentFile);
    }
  }

  /**
   * Convert the specified file Read each line and ask matcher implementation
   * for conversion Rewrite the line returned by matcher
   * 
   * @param file
   */
  private void convertFile(File file) {
    try {
      InplaceFileConverter fc = new InplaceFileConverter(lineConverter);
      byte[] ba = fc.readFile(file);
      fc.convert(file, ba);
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
      Iterator iterator = exception.iterator();
      while (iterator.hasNext()) {
        ConversionException exc = (ConversionException) iterator.next();
        exc.print();
      }
      exception = null;
    }
  }
}