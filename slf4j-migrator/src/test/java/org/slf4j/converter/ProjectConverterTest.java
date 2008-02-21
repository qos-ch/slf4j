package org.slf4j.converter;

import java.io.File;

import org.slf4j.converter.internal.NopProgressListener;

import junit.framework.TestCase;

public class ProjectConverterTest extends TestCase {

  public void test() {
  }

  public void XtestBarracuda() {
    ProjectConverter pc = new ProjectConverter(Constant.LOG4J_TO_SLF4J,
        new NopProgressListener());
    File projectFolder = new File("c:/home/ceki//Varia/Barracuda");
    pc.convertProject(projectFolder);
  }
}
