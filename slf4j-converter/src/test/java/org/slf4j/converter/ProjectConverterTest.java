package org.slf4j.converter;

import java.io.File;
import java.util.List;

import junit.framework.TestCase;

public class ProjectConverterTest extends TestCase {

  
  public void test() {
  }
  
  public void Xtest() {
    ProjectConverter pc = new ProjectConverter(Constant.JCL_TO_SLF4J);
    
    File projectFolder = new File("c:/home/ceki//Varia/vfs");
    FileSelector fs = new FileSelector();
    List<File> fileList = fs.selectJavaFilesInFolder(projectFolder);
    
    for(File f : fileList) {
      System.out.println(f);
    }
    System.out.println(fileList.size());
    pc.convertProject(projectFolder);
  }
}
