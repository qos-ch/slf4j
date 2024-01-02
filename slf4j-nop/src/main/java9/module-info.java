/**
 * A no-operation logging provider, aka back-end, that drops all events.
 */
module org.slf4j.nop {
  requires org.slf4j;
  exports org.slf4j.nop;
  provides org.slf4j.spi.SLF4JServiceProvider with org.slf4j.nop.NOPServiceProvider;
}
