module org.slf4j.log4j12 { 
  requires org.slf4j;
  requires org.slf4j.spi;
  provides org.slf4j.spi.SLF4JServiceProvider with org.slf4j.impl.Log4j12ServiceProvider
}
