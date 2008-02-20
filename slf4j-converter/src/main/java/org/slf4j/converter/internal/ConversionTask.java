package org.slf4j.converter.internal;

import java.io.File;

import org.slf4j.converter.ProjectConverter;

public class ConversionTask implements Runnable {

  final File folder;
  final MigratorFrame frame;
  final int conversionType;
 
  
  ConversionTask(File folder, MigratorFrame frame, int conversionType) {
    this.folder = folder;
    this.frame = frame;
    this.conversionType = conversionType;
  }

  public void run() {
    ProgressListener pl = new ProgressListenerImpl(folder, frame);
    pl.onMigrationBegin();
    ProjectConverter converter = new ProjectConverter(conversionType, pl);
    converter.convertProject(folder);
  }

  public void launch() {
    Thread t = new Thread(this);
    t.setDaemon(true);
    t.start();
  }

}
