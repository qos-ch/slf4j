package org.slf4j.converter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileSelector {

  private List<File> javaFileList = new ArrayList<File>();

  public List<File> selectJavaFilesInFolder(File folder) {
    if(folder.isDirectory()) {
      selectFiles(folder);
      return javaFileList;
    } else {
      throw new IllegalArgumentException("["+folder+"] is not a directory");
    }
  }
  
  private void selectFiles(File file) {
    if (file.isDirectory()) {
      File[] files = file.listFiles();
      if (files != null) {
        for (int i = 0; i < files.length; i++) {
          selectFiles(files[i]);
        }
      }
    } else {
      if (file.getName().endsWith(".java")) {
        javaFileList.add(file);
      }

    }
  }
}
