package org.slf4j.converter.internal;

import java.io.File;

public class NopProgressListener implements ProgressListener {

  public void onDirectory(File file) {
  }

  public void onDone() {
  }

  public void onFileAddition(File file) {
  }

  public void onFileConversion(File file) {
  }

}
