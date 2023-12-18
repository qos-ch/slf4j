module org.apache.commons.logging {
  requires org.slf4j;
  exports org.apache.commons.logging;
  exports org.apache.commons.logging.impl to org.apache.commons.logging.blackbox;
}
