module org.slf4j.reload4j {
    requires org.slf4j;
    requires ch.qos.reload4j;
    provides org.slf4j.spi.SLF4JServiceProvider with org.slf4j.reload4j.Reload4jServiceProvider;
}