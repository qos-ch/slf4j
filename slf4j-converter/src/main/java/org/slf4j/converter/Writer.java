package org.slf4j.converter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Writer {

  ProjectConverter converter;

  BufferedWriter bwriter;

  boolean isFirstLine;

  public Writer(ProjectConverter converter) {
    this.converter = converter;
  }

  public void initFileWriter(File file) {
    try {
      FileWriter fileWriter = new FileWriter(file);
      bwriter = new BufferedWriter(fileWriter);
      isFirstLine = true;
    } catch (Exception exc) {
      converter.addException(new ConversionException(exc.toString(), file
          .getAbsolutePath()));
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
