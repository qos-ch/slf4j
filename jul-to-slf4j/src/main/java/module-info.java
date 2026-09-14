/**
 * Bridge/route all JUL log records to the SLF4J API.
 */
module jul.to.slf4j {

    requires org.slf4j;
    requires java.logging;

    exports org.slf4j.bridge;
}