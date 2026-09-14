/**
 * The simple logger, a bare-bones implementation of logging backend.
 *
 *
 */
module org.slf4j.simple {
  requires org.slf4j;
  // exporting ?
  exports org.slf4j.simple;
  provides org.slf4j.spi.SLF4JServiceProvider with org.slf4j.simple.SimpleServiceProvider;
  opens org.slf4j.simple to org.slf4j;
}
