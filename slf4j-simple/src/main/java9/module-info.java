module org.slf4j.simple {
  requires org.slf4j;
  provides org.slf4j.spi.SLF4JServiceProvider with org.slf4j.simple.SimpleServiceProvider;
  exports org.slf4j.simple;
  opens org.slf4j.simple to org.slf4j;
}
