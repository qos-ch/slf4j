package org.slf4j.converter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;

public class Writer {

  BufferedWriter bwriter;

  boolean isFirstLine;

  public Writer() {
  }

  public void initFileWriter(File file) {
    try {
      FileWriter fileWriter = new FileWriter(file);
      bwriter = new BufferedWriter(fileWriter);
      isFirstLine = true;
    } catch (IOException exc) {
      System.out.println("error creating filewriter " + file.getAbsolutePath());
    }
  }

  public void closeFileWriter() {
    if (bwriter != null) {
      try {
        bwriter.flush();
        bwriter.close();
      } catch (IOException e) {
        System.out.println("error closing filewriter " + bwriter.toString());
      }
    }
  }

  public void rewrite(Matcher matcher, String replacement) {
    String text = matcher.replaceAll(replacement);
    if (bwriter != null) {
      try {
        if (!isFirstLine) {
          bwriter.newLine();
        } else {
          isFirstLine = false;
        }
        bwriter.write(text);
        // System.out.println("new entry " + text);
      } catch (IOException exc) {
        System.out.println("error writing file " + bwriter.toString());
      }
    }
  }

  public void write(String text) {
    if (bwriter != null) {
      try {
        if (!isFirstLine) {
          bwriter.newLine();
        } else {
          isFirstLine = false;
        }
        bwriter.write(text);
        // System.out.println("new entry " + text);
      } catch (IOException exc) {
        System.out.println("error writing file " + bwriter.toString());
      }
    }
  }

}
