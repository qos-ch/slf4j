module org.slf4j.log4j12 { 
  requires org.slf4j;
  requires log4j;
  provides org.slf4j.spi.SLF4JServiceProvider with org.slf4j.log4j12.Log4j12ServiceProvider;
}
