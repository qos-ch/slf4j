package org.slf4j.ext.mdc.example2;

import org.slf4j.ext.mdc.Pojo;
import org.slf4j.ext.mdc.Property;
import org.slf4j.ext.mdc.RootPojo;

/**
 * Created by himavija on 10/9/15.
 *
 * This is the desired structure. We want users of this API to write only the following. All of the other boilerplate code
 * should be taken care of automatically during annotation processing.
 */
@RootPojo(name = "event")
public class Event {
  private Who who;

  private Who what;

  @Property(name="trackingId")
  private String trackingId;

  @Pojo(name = "who")
  public static class Who {
    @Property(name="id")
    private String id;
  }

  @Pojo(name = "what")
  public static class What {
    private Outcome outcome;

    @Pojo(name="Outcome")
    public static class Outcome {
      @Property(name="result_string")
      private String resultString;

      @Property(name="result_number")
      private Number resultNumber;

      @Property(name="result_boolean")
      private Boolean resultBoolean;

//      @Property(name="result_object")
//      private Object resultObject;

//      @Property(name="result_array")
//      private List resultArray;

//      @Property(name="result_map")
//      private Map resultMap;

      @Property(name="error")
      private String error;

      @Property(name="statusCode")
      private String statusCode;
    }
  }
}
