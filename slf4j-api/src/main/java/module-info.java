module org.slf4j { 
  exports org.slf4j;
  exports org.slf4j.spi;
  exports org.slf4j.event;
  exports org.slf4j.helpers;
  uses org.slf4j.spi.SLF4JServiceProvider;
requires java.base;
}
