package org.slf4j.test.fluent;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.HashMap;
import java.util.Map;

import static net.logstash.logback.argument.StructuredArguments.*;
import static net.logstash.logback.marker.Markers.append;
import static org.junit.Assert.assertEquals;

public class StructuredLoggingTest {

  @Before
  public void setUp() {
    TestLogstashEncoder.init();
  }

  @Test
  public void structuredLogging() {
    Logger logger = LoggerFactory.getLogger("org.slf4j.test.fluent.json.error.Test");

    MDC.put("mdc1", "mdc value 1");
    MDC.put("mdc2", "mdc value 2");

    logger.atError().log("log message {}", value("name", "value"));
    logger.atError().log("log message {}", keyValue("name", "value"));
    logger.atError().log("log message", keyValue("name", "value"));
    logger.atError().log("log message {} {}", keyValue("name1", "value1"), keyValue("name2", "value2"));
    logger.atError().log("log message {}", keyValue("name", "value", "{0}=[{1}]"));
    Map<String, String> myMap = new HashMap<>();
    myMap.put("name1", "value1");
    myMap.put("name2", "value2");
    logger.atError().log("log message {}", entries(myMap));
    logger.atError().log("log message {}", array("array", 1, 2, 3));
    logger.atError(append("name", "value")).log("log message");

    JsonNode json = TestLogstashEncoder.JSON_OUTPUTS.get(0);
    assertEquals("value", json.get("name").asText());

    json = TestLogstashEncoder.JSON_OUTPUTS.get(1);
    assertEquals("value", json.get("name").asText());

    json = TestLogstashEncoder.JSON_OUTPUTS.get(2);
    assertEquals("value", json.get("name").asText());

    json = TestLogstashEncoder.JSON_OUTPUTS.get(3);
    assertEquals("value1", json.get("name1").asText());
    assertEquals("value2", json.get("name2").asText());

    json = TestLogstashEncoder.JSON_OUTPUTS.get(4);
    assertEquals("value", json.get("name").asText());

    json = TestLogstashEncoder.JSON_OUTPUTS.get(5);
    assertEquals("value1", json.get("name1").asText());
    assertEquals("value2", json.get("name2").asText());

    json = TestLogstashEncoder.JSON_OUTPUTS.get(6);
    assertEquals(1, json.get("array").get(0).asInt());
    assertEquals(2, json.get("array").get(1).asInt());
    assertEquals(3, json.get("array").get(2).asInt());

    json = TestLogstashEncoder.JSON_OUTPUTS.get(7);
    assertEquals("value", json.get("name").asText());

    TestLogstashEncoder.JSON_OUTPUTS.forEach(j -> {
      assertEquals("mdc value 1", j.get("mdc1").asText());
      assertEquals("mdc value 2", j.get("mdc2").asText());
    });
  }
}
