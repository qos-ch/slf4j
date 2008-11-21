package org.slf4j.migrator.internal;

import java.io.File;

import org.slf4j.migrator.internal.ProgressListener;

public class NopProgressListener implements ProgressListener {

  public void onDirectory(File file) {
  }

  public void onDone() {
  }

  public void onFileAddition(File file) {
  }

  public void onFileScan(File file) {
  }

  public void onInplaceConversion(File file) {
  }

  public void onFileScanBegin() {
  }

  public void onMigrationBegin() {
  }

}
