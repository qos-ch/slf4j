package org.slf4j.converter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Converter {

  private List<File> javaFiles;

  private AbstractMatcher matcher;

  private Writer writer;

  private File fileSource;

  private List<ConversionException> exception;

  /**
   * Run JCL to SLF4J conversion
   * 
   * @param args
   *          source folder directory optional
   * @throws IOException
   * @throws IOException
   */
  public static void main(String[] args) throws IOException {

    Converter converter = new Converter();

    ConverterFrame frame = new ConverterFrame(converter);
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
  public boolean init(String source, int conversionType) {
    matcher = AbstractMatcher.getMatcherImpl(conversionType);
    if (matcher == null) {
      addException(new ConversionException(ConversionException.NOT_IMPLEMENTED));
      return false;
    }

    writer = new Writer(this);

    fileSource = new File(source);
    if (!fileSource.isDirectory()) {
      addException(new ConversionException(
          ConversionException.INVALID_DIRECTORY));
      return false;
    } else {
      return true;
    }
  }

  /**
   * delete a file
   * 
   * @param fdest
   */
  private void delete(File fdest) {
    if (fdest.isDirectory()) {
      File[] files = fdest.listFiles();
      if (files != null) {
        for (int i = 0; i < files.length; i++) {
          delete(files[i]);
        }
      }
      fdest.delete();
    } else {
      fdest.delete();
    }
  }

  /**
   * copy a file from source to destination
   * 
   * @param fsource
   * @param fdest
   */
  private void copy(File fsource, File fdest) {
    try {
      FileInputStream fis = new FileInputStream(fsource);
      FileOutputStream fos = new FileOutputStream(fdest);
      FileChannel channelSource = fis.getChannel();
      FileChannel channelDest = fos.getChannel();
      if (channelSource.isOpen() && channelDest.isOpen()) {
        channelSource.transferTo(0, channelSource.size(), channelDest);
        channelSource.close();
        channelDest.close();
      } else {
        addException(new ConversionException(ConversionException.FILE_COPY,
            fsource.getAbsolutePath()));
      }

    } catch (FileNotFoundException exc) {
      addException(new ConversionException(exc.toString()));
    } catch (IOException e) {
      addException(new ConversionException(e.toString()));
    }
  }

  /**
   * Select java files to be converted
   * 
   * @param file
   * @return
   */
  private List<File> selectFiles(File file) {
    if (javaFiles == null) {
      javaFiles = new ArrayList<File>();
    }
    if (file.isDirectory()) {
      File[] files = file.listFiles();
      if (files != null) {
        for (int i = 0; i < files.length; i++) {
          selectFiles(files[i]);
        }
      }
    } else {
      if (file.getName().endsWith(".java")) {
        javaFiles.add(file);
      }
    }
    return javaFiles;
  }

  public void convert() {
    convert(javaFiles);
  }

  public int selectFiles() {
    return selectFiles(fileSource).size();
  }

  /**
   * Convert a list of files
   * 
   * @param lstFiles
   */
  private void convert(List<File> lstFiles) {
    Iterator<File> itFile = lstFiles.iterator();
    while (itFile.hasNext()) {
      File currentFile = itFile.next();
      convert(currentFile);
    }
  }

  /**
   * Convert the specified file Read each line and ask matcher implementation
   * for conversion Rewrite the line returned by matcher
   * 
   * @param file
   */
  private void convert(File file) {
    File newFile = new File(file.getAbsolutePath() + "new");
    try {
      boolean isEmpty = false;
      writer.initFileWriter(newFile);
      FileReader freader = new FileReader(file);
      BufferedReader breader = new BufferedReader(freader);
      String line;
      String newLine;
      while (!isEmpty) {
        line = breader.readLine();
        if (line != null) {
          newLine = matcher.getReplacement(line);
          writer.write(newLine);
        } else {
          isEmpty = true;
          writer.closeFileWriter();
          copy(newFile, file);
          delete(newFile);
        }
      }
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