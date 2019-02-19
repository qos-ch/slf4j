package org.slf4j.test.fluent;

import ch.qos.logback.classic.spi.ILoggingEvent;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.logstash.logback.encoder.LogstashEncoder;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

/**
 * Hacky encoder that converts the resulting json back into a {@link JsonNode} and stores the appended nodes into a static Vector
 * Used the check the actual content of the logged json
 * Not thread safe
 */
public class TestLogstashEncoder extends LogstashEncoder {

  public static final List<JsonNode> JSON_OUTPUTS = new Vector<>();

  public static void init() {
    JSON_OUTPUTS.clear();
  }

  private final ObjectMapper objectMapper;

  public TestLogstashEncoder() {
    objectMapper = new ObjectMapper();
  }

  @Override
  public byte[] encode(ILoggingEvent iLoggingEvent) {
    byte[] result = super.encode(iLoggingEvent);
    append(result);
    return result;
  }

  private void append(byte[] result) {
    try {
      JSON_OUTPUTS.add(objectMapper.readTree(result));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
