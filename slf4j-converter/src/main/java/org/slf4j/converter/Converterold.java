package org.slf4j.converter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.nio.channels.FileChannel;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Converterold {
  private Logger logger;

  private List<File> javaFiles;

  private String source;

  private String destination;

  private static Pattern regex = Pattern.compile("converter");

  /**
   * @param args
   */
  public static void main(String[] args) {

    Converterold converter = new Converterold();
    converter.init();

    converter.getPaths();
    File fileSource = converter.initSource();
    File fileDest = converter.initDestination();
    converter.copy(fileSource);
    converter.selectFiles(fileDest);
    converter.convert(converter.javaFiles);
  }

  public void init() {
    logger = LoggerFactory.getLogger(Converterold.class);
  }

  /**
   * 
   * 
   */
  private void getPaths() {
    source = "c:/projets/slf4j/slf4j-converter/src";
    destination = "c:/temp";
  }

  /**
   * 
   * @return
   */
  private File initSource() {
    File fileSource = new File(source);
    if (!fileSource.isDirectory()) {
      logger.info("source path is not a valid source directory");
    }
    return fileSource;
  }

  /**
   * 
   * @return
   */
  private File initDestination() {
    File fileDest = new File(destination);
    if (fileDest.exists()) {
      delete(fileDest);
    }
    fileDest.mkdir();
    return fileDest;
  }

  /**
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
      logger.info("Deleting " + fdest.getName());
    } else {
      logger.info("Deleting " + fdest.getName());
      fdest.delete();
    }
  }

  /**
   * 
   * @param fsource
   */
  private void copy(File fsource) {
    String curentFileName = fsource.getAbsolutePath()
        .substring(source.length());
    File fdest = new File(destination + "/" + curentFileName);
    if (fsource.isDirectory()) {
      logger.info("Current directory " + fsource.getAbsolutePath());
      fdest.mkdir();
      logger.info("New directory " + fdest.getAbsolutePath());
      File[] files = fsource.listFiles();
      if (files != null) {
        for (int i = 0; i < files.length; i++) {
          copy(files[i]);
        }
      }
    } else {
      try {
        FileInputStream fis = new FileInputStream(fsource);
        FileOutputStream fos = new FileOutputStream(fdest);
        FileChannel channelSource = fis.getChannel();
        FileChannel channelDest = fos.getChannel();
        if (channelSource.isOpen() && channelDest.isOpen()) {
          channelSource.transferTo(0, channelSource.size(), channelDest);
          logger.info("file " + fsource.getName() + " transfered");
          channelSource.close();
          channelDest.close();
        } else {
          logger.error("error copying file " + fsource.getAbsolutePath());
        }

      } catch (FileNotFoundException exc) {
        logger.error(exc.toString());
      } catch (IOException e) {
        logger.error(e.toString());
      }
    }
  }

  /**
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
        logger.info("Adding java file " + file.getAbsolutePath());
      }
    }
    return javaFiles;
  }

  /**
   * 
   * @param lstFiles
   */
  private void convert(List<File> lstFiles) {
    Iterator<File> itFile = lstFiles.iterator();
    while (itFile.hasNext()) {
      File currentFile = itFile.next();
      logger.info("reading file " + currentFile.getAbsolutePath());
      try {
        boolean isEmpty = false;
        FileReader freader = new FileReader(currentFile);
        BufferedReader breader = new BufferedReader(freader);
        String line = breader.readLine();
        logger.info("reading line " + line);
        while (!isEmpty) {
          line = breader.readLine();
          if (line != null) {
            logger.info("reading line " + line);
            Matcher matcher = isMatching(line);
            if (matcher != null) {
              replace(matcher, line);
            }
          } else {
            isEmpty = true;
          }
        }
      } catch (IOException exc) {
        logger.error("error reading file " + exc);
      }
    }
  }

  public Matcher isMatching(String line) {
    Matcher matcher = regex.matcher(line);
    if (matcher.find()) {
      logger.info("found regex in" + line);
      return matcher;
    }
    // if(matcher.matches()){
    // logger.info("found regex " + line);
    // return true;
    // }
    return null;
  }

  /**
   * Rewrite the line
   * 
   * @param line
   */
  public void replace(Matcher matcher, String line) {
    // String replacement = getReplacement(regex);
    logger.info("replacing into" + line);
    line = matcher.replaceAll("");
    logger.info("new line " + line);
  }
}
