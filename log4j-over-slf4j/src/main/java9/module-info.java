module log4j {
	requires org.slf4j;
	requires java.xml;
	exports org.apache.log4j;
	exports org.apache.log4j.helpers;
	exports org.apache.log4j.spi;
	exports org.apache.log4j.xml;
}