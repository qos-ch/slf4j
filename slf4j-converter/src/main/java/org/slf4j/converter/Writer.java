package org.slf4j.converter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;

public class Writer {

  Converter converter;
  
  BufferedWriter bwriter;

  boolean isFirstLine;

  public Writer(Converter converter) {
    this.converter = converter;
  }

  public void initFileWriter(File file) {
    try {
      FileWriter fileWriter = new FileWriter(file);
      bwriter = new BufferedWriter(fileWriter);
      isFirstLine = true;
    } catch (Exception exc) {
      converter.addException(new ConversionException(exc.toString(),file.getAbsolutePath()));
    }
  }

  public void closeFileWriter() {
    if (bwriter != null) {
      try {
        bwriter.flush();
        bwriter.close();
      } catch (IOException e) {
        converter.addException(new ConversionException(e.toString()));
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
      } catch (IOException exc) {
        converter.addException(new ConversionException(exc.toString()));
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
      } catch (IOException exc) {
        converter.addException(new ConversionException(exc.toString()));
      }
    }
  }

}
