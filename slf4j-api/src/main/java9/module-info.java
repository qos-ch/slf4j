/**
 *  <p>This module defines the client-facing SLF4J API. </p>
 *
 *  <p>More specifically, the {@link org.slf4j} package contains client-facing classes and interfaces.</p>
 *
 *  <p>The {@link org.slf4j.spi} package contains classes which are intended for logging backends. In particular, </p>
 *  ogging back-ends must provide a {@link org.slf4j.spi.SLF4JServiceProvider} implementation in order to be picked
 *  up by SLF4J at initialization time.
 *  </p>
 *
 * @moduleGraph
 */
module org.slf4j {
  exports org.slf4j;
  exports org.slf4j.spi;
  exports org.slf4j.event;
  exports org.slf4j.helpers;
  uses org.slf4j.spi.SLF4JServiceProvider;
  requires java.base;
}
