package org.slf4j.migrator;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;

import org.slf4j.migrator.internal.ProgressListener;
import org.slf4j.migrator.line.LineConverter;
import org.slf4j.migrator.line.RuleSet;

public class InplaceFileConverter {

  final static int BUFFER_LEN = 8 * 1024;
  final LineConverter lineConverter;
  final String lineTerminator;
  final ProgressListener pl;
  
  InplaceFileConverter(RuleSet ruleSet, ProgressListener pl) {
    this.lineConverter = new LineConverter(ruleSet);
    lineTerminator = System.getProperty("line.separator");
    this.pl = pl;
  }

  private byte[] readIntoByteArray(File file) throws IOException {
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

  void convert(File file) throws IOException {
    byte[] originalBytes = readIntoByteArray(file);
    byte[] convertedBytes = convertIntoTempByteArray(originalBytes);
    if (lineConverter.atLeastOneMatchOccured()) {
      //System.out.println("Converting ["+file+"]");
      writeConvertedBytesIntoFile(file, convertedBytes);
      pl.onInplaceConversion(file);
    } else {
      //System.out.println("Not touching ["+file+"]");
    }
  }

  private void writeConvertedBytesIntoFile(File file, byte[] convertedBytes) throws IOException {
    FileOutputStream fos = new FileOutputStream(file);
    fos.write(convertedBytes);
    fos.flush();
    fos.close();
  }

  private byte[] convertIntoTempByteArray(byte[] input) throws IOException {
    ByteArrayInputStream bais = new ByteArrayInputStream(input);
    Reader reader = new InputStreamReader(bais);
    BufferedReader breader = new BufferedReader(reader);
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    while (true) {
      String line = breader.readLine();
      if (line != null) {
        String[] replacement = lineConverter.getReplacement(line);
        writeReplacement(baos, replacement);
      } else {
        break;
      }
    }
    return baos.toByteArray();
  }

  private  void writeReplacement(OutputStream os, String[] replacement)
      throws IOException {
    for (int i = 0; i < replacement.length; i++) {
      os.write(replacement[i].getBytes());
      os.write(lineTerminator.getBytes());
    }
  }
}
