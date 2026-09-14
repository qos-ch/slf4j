/**
 * Jakarta/Apache Commons Logging implemented over SLF4J.
 */
module org.apache.commons.logging {
  requires org.slf4j;
  exports org.apache.commons.logging;
  exports org.apache.commons.logging.impl;
}
