module jul.to.slf4j {

    requires org.slf4j;
    requires java.logging;

    exports org.slf4j.bridge;
}