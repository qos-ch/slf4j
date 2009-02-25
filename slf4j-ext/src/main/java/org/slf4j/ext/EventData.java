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
 * Base class for Event Data. Event Data contains data to be logged about an event. Users may
 * extend this class for each EventType they want to log.
 *
 * @author Ralph Goers
 */
public class EventData implements Serializable
{
    private Map<String, Object> eventData = new HashMap<String,Object>();
    public static final String EVENT_MESSAGE = "EventMessage";
    public static final String EVENT_TYPE = "EventType";
    public static final String EVENT_DATETIME = "EventDateTime";
    public static final String EVENT_ID = "EventId";

    /**
     * Default Constructor
     */
    public EventData()
    {
    }

    /**
     * Constructor to create event data from a Map.
     * @param map The event data.
     */
    public EventData(Map<String, Object> map)
    {
        eventData.putAll(map);
    }

    /**
     * Construct from a serialized form of the Map containing the RequestInfo elements
     * @param xml The serialized form of the RequestInfo Map.
     */
    public EventData(String xml)
    {
        ByteArrayInputStream bais = new ByteArrayInputStream(xml.getBytes());
        try
        {
            XMLDecoder decoder = new XMLDecoder(bais);
            this.eventData = (Map<String, Object>)decoder.readObject();
        }
        catch (Exception e)
        {
            throw new EventException("Error decoding " + xml, e);
        }
    }
    
    /**
     * Serialize all the EventData items into an XML representation.
     * @return an XML String containing all the EventDAta items.
     */
    public String toXML()
    {
        return toXML(eventData);
    }

    /**
     * Serialize all the EventData items into an XML representation.
     * @return an XML String containing all the EventDAta items.
     */
    public static String toXML(Map<String, Object>map)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try
        {
            XMLEncoder encoder = new XMLEncoder(baos);
            encoder.setExceptionListener(new ExceptionListener()
            {
                public void exceptionThrown(Exception exception)
                {
                    exception.printStackTrace();
                }
            });
            encoder.writeObject(map);
            encoder.close();
            return baos.toString();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public String getEventId()
    {
        return (String)this.eventData.get(EVENT_ID);
    }

    public void setEventId(String eventId)
    {
        if (eventId == null)
        {
            throw new IllegalArgumentException("eventId cannot be null");
        }
        this.eventData.put(EVENT_ID, eventId);
    }

    public String getMessage()
    {
        return (String)this.eventData.get(EVENT_MESSAGE);
    }

    public void setMessage(String message)
    {
        this.eventData.put(EVENT_MESSAGE, message);
    }

    public Date getEventDateTime()
    {
        return (Date)this.eventData.get(EVENT_DATETIME);
    }

    public void setEventDateTime(Date eventDateTime)
    {
        this.eventData.put(EVENT_DATETIME, eventDateTime);
    }

    public void setEventType(String eventType)
    {
        this.eventData.put(EVENT_TYPE, eventType);
    }

    public String getEventType()
    {
        return (String)this.eventData.get(EVENT_TYPE);
    }


    public void put(String name, Serializable obj)
    {
        this.eventData.put(name, obj);
    }

    public Serializable get(String name)
    {
        return (Serializable) this.eventData.get(name);
    }

    public void putAll(Map data)
    {
        this.eventData.putAll(data);
    }

    public int getSize()
    {
        return this.eventData.size();
    }

    public Iterator getEntrySetIterator()
    {
        return this.eventData.entrySet().iterator();
    }

    public Map getEventMap()
    {
        return this.eventData;
    }

    public String toString()
    {
        return toXML();
    }

    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof EventData || o instanceof Map)) return false;
        Map<String, Object>map = (o instanceof EventData) ? ((EventData)o).getEventMap() : (Map<String, Object>)o;

        return this.eventData.equals(map);
    }

    public int hashCode()
    {
        return this.eventData.hashCode();
    }
}