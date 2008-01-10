package org.slf4j.converter;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;

import org.slf4j.converter.line.LineConverter;

public class InplaceFileConverter {

  final static int BUFFER_LEN = 8 * 1024;
  final LineConverter lineConverter;
  final String lineTerminator;

  InplaceFileConverter(LineConverter lineConverter) {
    this.lineConverter = lineConverter;
    lineTerminator = System.getProperty("line.separator");
  }

  byte[] readFile(File file) throws IOException {
    FileInputStream fis = new FileInputStream(file);
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    int n = 0;
    byte[] buffer = new byte[BUFFER_LEN];
    while ((n = fis.read(buffer)) != -1) {
      // System.out.println("ba="+new String(buffer, "UTF-8"));
      baos.write(buffer, 0, n);
    }
    fis.close();
    return baos.toByteArray();

  }

  void convert(File file, byte[] input) throws IOException {
    ByteArrayInputStream bais = new ByteArrayInputStream(input);
    Reader reader = new InputStreamReader(bais);
    BufferedReader breader = new BufferedReader(reader);
    FileWriter fileWriter = new FileWriter(file);
    while (true) {
      String line = breader.readLine();
      if (line != null) {
        String[] replacement = lineConverter.getReplacement(line);
        writeReplacement(fileWriter, replacement);
      } else {
        fileWriter.close();
        break;
      }
    }
  }

  void writeReplacement(Writer writer, String[] replacement) throws IOException {
    for (int i = 0; i < replacement.length; i++) {
      writer.write(replacement[i]);
      writer.write(lineTerminator);
    }
  }
}
