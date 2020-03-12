module org.slf4j.jdk {
    requires org.slf4j;
    provides java.lang.System.LoggerFinder
            with org.slf4j.jdk.SLF4JSystemLoggerFinder;
}