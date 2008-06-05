package org.slf4j.migrator;

public class ConversionException extends Exception {

  /**
   * 
   */
  private static final long serialVersionUID = 498961799888576668L;

  private String detail;

  public static final String NOT_IMPLEMENTED = "Conversion mode not implemented yet";
  public static final String INVALID_DIRECTORY = "Invalid source directory";
  public static final String FILE_COPY = "Error during file copy";

  public ConversionException(String message) {
    super(message);
  }

  public ConversionException(String message, String detail) {
    super(message);
    this.detail = detail;
  }

  public String getDetail() {
    return detail;
  }

  public void setDetail(String detail) {
    this.detail = detail;
  }

  public void print() {
    if (getMessage() != null) {
      System.out.println(getMessage());
    }
  }

}
