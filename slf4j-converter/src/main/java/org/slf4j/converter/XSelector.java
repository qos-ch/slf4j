package org.slf4j.converter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class XSelector implements Runnable {

  private List<File> javaFileList = new ArrayList<File>();

  javax.swing.JLabel jlabel;
  
  File folder;
  
  public void run() {
    selectFiles(folder);
  }
  
  public List<File> selectJavaFilesInFolder(File folder) {
    if(folder.isDirectory()) {
      this.folder = folder;
      Thread t = new Thread(this);
      t.setDaemon(true);
      t.start();
      return javaFileList;
    } else {
      throw new IllegalArgumentException("["+folder+"] is not a directory");
    }
  }
  
  private void selectFiles(File file) {
    //System.out.println(file);
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
        //jlabel.setText(file.getAbsolutePath());
        javaFileList.add(file);
      }

    }
  }


}
