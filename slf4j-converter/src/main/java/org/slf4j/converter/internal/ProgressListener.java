package org.slf4j.converter.internal;

import java.io.File;

public interface ProgressListener {

  public void onMigrationBegin();
  public void onDirectory(File file);
  public void onFileAddition(File file);
  public void onFileScanBegin();
  public void onFileScan(File file);
  public void onInplaceConversion(File file);
  public void onDone();

}
  
