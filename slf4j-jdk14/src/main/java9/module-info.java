module org.slf4j.jul { 
  requires org.slf4j;
  requires java.logging;
  provides org.slf4j.spi.SLF4JServiceProvider with org.slf4j.jul.JULServiceProvider;
}

