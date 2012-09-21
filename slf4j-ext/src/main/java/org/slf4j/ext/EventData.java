/**
 * Copyright (c) 2004-2011 QOS.ch
 * All rights reserved.
 *
 * Permission is hereby granted, free  of charge, to any person obtaining
 * a  copy  of this  software  and  associated  documentation files  (the
 * "Software"), to  deal in  the Software without  restriction, including
 * without limitation  the rights to  use, copy, modify,  merge, publish,
 * distribute,  sublicense, and/or sell  copies of  the Software,  and to
 * permit persons to whom the Software  is furnished to do so, subject to
 * the following conditions:
 *
 * The  above  copyright  notice  and  this permission  notice  shall  be
 * included in all copies or substantial portions of the Software.
 *
 * THE  SOFTWARE IS  PROVIDED  "AS  IS", WITHOUT  WARRANTY  OF ANY  KIND,
 * EXPRESS OR  IMPLIED, INCLUDING  BUT NOT LIMITED  TO THE  WARRANTIES OF
 * MERCHANTABILITY,    FITNESS    FOR    A   PARTICULAR    PURPOSE    AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE,  ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package org.slf4j.ext;

import java.io.Serializable;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.beans.ExceptionListener;

/**
 * Base class for Event Data. Event Data contains data to be logged about an
 * event. Users may extend this class for each EventType they want to log.
 * 
 * @author Ralph Goers
 */
public class EventData implements Serializable {
  
  private static final long serialVersionUID = 153270778642103985L;
  
  private Map<String, Object> eventData = new HashMap<String, Object>();
  public static final String EVENT_MESSAGE = "EventMessage";
  public static final String EVENT_TYPE = "EventType";
  public static final String EVENT_DATETIME = "EventDateTime";
  public static final String EVENT_ID = "EventId";

  /**
   * Default Constructor
   */
  public EventData() {
  }

  /**
   * Constructor to create event data from a Map.
   * 
   * @param map
   *          The event data.
   */
  public EventData(Map<String, Object> map) {
    eventData.putAll(map);
  }

  /**
   * Construct from a serialized form of the Map containing the RequestInfo
   * elements
   * 
   * @param xml
   *          The serialized form of the RequestInfo Map.
   */
  @SuppressWarnings("unchecked")
  public EventData(String xml) {
    ByteArrayInputStream bais = new ByteArrayInputStream(xml.getBytes());
    try {
      XMLDecoder decoder = new XMLDecoder(bais);
      this.eventData = (Map<String, Object>) decoder.readObject();
    } catch (Exception e) {
      throw new EventException("Error decoding " + xml, e);
    }
  }

  /**
   * Serialize all the EventData items into an XML representation.
   * 
   * @return an XML String containing all the EventDAta items.
   */
  public String toXML() {
    return toXML(eventData);
  }

  /**
   * Serialize all the EventData items into an XML representation.
   * 
   * @param map the Map to transform
   * @return an XML String containing all the EventDAta items.
   */
  public static String toXML(Map<String, Object> map) {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    try {
      XMLEncoder encoder = new XMLEncoder(baos);
      encoder.setExceptionListener(new ExceptionListener() {
        public void exceptionThrown(Exception exception) {
          exception.printStackTrace();
        }
      });
      encoder.writeObject(map);
      encoder.close();
      return baos.toString();
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Retrieve the event identifier.
   * 
   * @return The event identifier
   */
  public String getEventId() {
    return (String) this.eventData.get(EVENT_ID);
  }

  /**
   * Set the event identifier.
   * 
   * @param eventId
   *          The event identifier.
   */
  public void setEventId(String eventId) {
    if (eventId == null) {
      throw new IllegalArgumentException("eventId cannot be null");
    }
    this.eventData.put(EVENT_ID, eventId);
  }

  /**
   * Retrieve the message text associated with this event, if any.
   * 
   * @return The message text associated with this event or null if there is
   *         none.
   */
  public String getMessage() {
    return (String) this.eventData.get(EVENT_MESSAGE);
  }

  /**
   * Set the message text associated with this event.
   * 
   * @param message
   *          The message text.
   */
  public void setMessage(String message) {
    this.eventData.put(EVENT_MESSAGE, message);
  }

  /**
   * Retrieve the date and time the event occurred.
   * 
   * @return The Date associated with the event.
   */
  public Date getEventDateTime() {
    return (Date) this.eventData.get(EVENT_DATETIME);
  }

  /**
   * Set the date and time the event occurred in case it is not the same as when
   * the event was logged.
   * 
   * @param eventDateTime
   *          The event Date.
   */
  public void setEventDateTime(Date eventDateTime) {
    this.eventData.put(EVENT_DATETIME, eventDateTime);
  }

  /**
   * Set the type of event that occurred.
   * 
   * @param eventType
   *          The type of the event.
   */
  public void setEventType(String eventType) {
    this.eventData.put(EVENT_TYPE, eventType);
  }

  /**
   * Retrieve the type of the event.
   * 
   * @return The event type.
   */
  public String getEventType() {
    return (String) this.eventData.get(EVENT_TYPE);
  }

  /**
   * Add arbitrary attributes about the event.
   * 
   * @param name
   *          The attribute's key.
   * @param obj
   *          The data associated with the key.
   */
  public void put(String name, Serializable obj) {
    this.eventData.put(name, obj);
  }

  /**
   * Retrieve an event attribute.
   * 
   * @param name
   *          The attribute's key.
   * @return The value associated with the key or null if the key is not
   *         present.
   */
  public Serializable get(String name) {
    return (Serializable) this.eventData.get(name);
  }

  /**
   * Populate the event data from a Map.
   * 
   * @param data
   *          The Map to copy.
   */
  public void putAll(Map<String, Object> data) {
    this.eventData.putAll(data);
  }

  /**
   * Returns the number of attributes in the EventData.
   * 
   * @return the number of attributes in the EventData.
   */
  public int getSize() {
    return this.eventData.size();
  }

  /**
   * Returns an Iterator over all the entries in the EventDAta.
   * 
   * @return an Iterator that can be used to access all the event attributes.
   */
  public Iterator<Map.Entry<String, Object>> getEntrySetIterator() {
    return this.eventData.entrySet().iterator();
  }

  /**
   * Retrieve all the attributes in the EventData as a Map. Changes to this map
   * will be reflected in the EventData.
   * 
   * @return The Map of attributes in this EventData instance.
   */
  public Map<String, Object> getEventMap() {
    return this.eventData;
  }

  /**
   * Convert the EventData to a String.
   * 
   * @return The EventData as a String.
   */
  @Override
  public String toString() {
    return toXML();
  }

  /**
   * Compare two EventData objects for equality.
   * 
   * @param o
   *          The Object to compare.
   * @return true if the objects are the same instance or contain all the same
   *         keys and their values.
   */
  @SuppressWarnings("unchecked")
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof EventData || o instanceof Map)) {
      return false;
    }
    Map<String, Object> map = (o instanceof EventData) ? ((EventData) o)
        .getEventMap() : (Map<String, Object>) o;

    return this.eventData.equals(map);
  }

  /**
   * Compute the hashCode for this EventData instance.
   * 
   * @return The hashcode for this EventData instance.
   */
  @Override
  public int hashCode() {
    return this.eventData.hashCode();
  }
}