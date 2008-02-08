package org.slf4j.converter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class XSelector {

  private List<File> javaFileList = new ArrayList<File>();

  javax.swing.JLabel jlabel;
  
  
  
  public List<File> selectJavaFilesInFolder(File folder) {
    if(folder.isDirectory()) {
      selectFiles(folder);
      return javaFileList;
    } else {
      throw new IllegalArgumentException("["+folder+"] is not a directory");
    }
  }
  
  private void selectFiles(File file) {
    System.out.println(file.getAbsolutePath());
    if (file.isDirectory()) {
      jlabel.setText(file.getAbsolutePath());
      
      File[] files = file.listFiles();
      if (files != null) {
        for (int i = 0; i < files.length; i++) {
          selectFiles(files[i]);
        }
      }
    } else {
      if (file.getName().endsWith(".java")) {
        jlabel.setText(file.getAbsolutePath());
        javaFileList.add(file);
      }

    }
  }
}
