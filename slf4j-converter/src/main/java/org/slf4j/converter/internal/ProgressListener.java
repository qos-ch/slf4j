package org.slf4j.converter.internal;

import java.io.File;

public interface ProgressListener {

  public void onDirectory(File file);
  public void onFileAddition(File file);
  public void onFileConversion(File file);
  public void onDone();

}
  
