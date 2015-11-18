package org.slf4j.ext.mdc;

import org.apache.commons.lang.NotImplementedException;

import java.util.Map;

/**
 * Abstract class for representing intermediate nodes of a POJO tree.
 * i.e. POJOs that are simply made up of other POJOs.
 * <p/>
 * Eg. If our POJO tree is like:
 * <pre>
 * EventCollector --> Resource --> URI
 *                --> Subject --> ID
 *                --> Environment --> HostName
 *                                --> IP
 *                --> Action --> Name
 *                           --> Arguments
 *                           --> Outcome --> Result
 *                                       --> Error
 * </pre>
 * Then Resource, Subject, Environment, Action, Outcome are non-leaf / intermediate nodes.
 * URI, ID, Hostname, IP, Name, Arguments, Result, Error are leaf nodes.
 *
 * @author Himanshu Vijay
 */
public abstract class NonLeafNode<T> extends Node<T> {
//    private T value;

  protected NonLeafNode(String name, Node parent) {
    super(name, parent);
  }

//    public T get(){
//        return value;
//    }
//
//    public void set(T t){
//        if(t != null){
//            value = t;
//        }
//    }

  /**
   * Override if you need to log as json. Default implementation throws NotImplementedException.
   * Use your favorite library like Jackson, Gson etc. to produce json string.
   * <p/>
   * You may choose to implement eventCollector.toKeyValuePairs() such that it would result in a json:
   * <pre>
   *  {
   *      "resource": {
   *          "uri": "/library/book/45"
   *      },
   *      "subject": {
   *          "id": "johndoe",
   *          "sessionId": "4162f74a-67f0-4aba-a95a-af1754d355d4"
   *      },
   *      "action": {
   *          "name": "get",
   *          "arguments": ["45"],
   *          "outcome": {
   *              "result": {
   *                  "book": {
   *                      "id": 45,
   *                      "author": "William Shakespeare",
   *                      "title": "Hamlet"
   *                  }
   *              },
   *              "error": ""
   *          }
   *      },
   *      "environment": {
   *          "hostName": "somehost.example.com",
   *          "ip": "10.0.255.255"
   *      }
   *  }
   * </pre>
   * <p/>
   * environment.toJson() may result in json:
   * <pre>
   *  {
   *      "hostName": "somehost.example.com",
   *      "ip": "10.0.255.255"
   *  }
   * </pre>
   *
   * @return Json representation of POJO tree.
   * @throws NotImplementedException
   */
  public String toJson() {
    throw new NotImplementedException("toJson() method not overridden!");
  }

  /**
   * Override if you need to log as xml. Default implementation throws NotImplementedException.
   * <p/>
   * You may choose to implement eventCollector.toXml() such that it would result in:
   * <pre>
   * {@code
   * <event>
   *  <resource>
   *      <uri>/library/book/45</uri>
   *  </resource>
   *  <subject>
   *      <id>johndoe</id>
   *      <sessionId>4162f74a-67f0-4aba-a95a-af1754d355d4</sessionId>
   *  </subject>
   *  <action>
   *    <name>get</name>
   *    <arguments>
   *        <argument>45</argument>
   *    </arguments>
   *    <outcome>
   *        <result>"{"book":{"id":45,"author": "William Shakespeare","title": "Hamlet"}}"</result>
   *        <error></error>
   *    </outcome>
   *  </action>
   *  <environment>
   *    <hostName>somehost.example.com</hostName>
   *    <ip>10.0.255.255</ip>
   *  </environment>
   * </event>
   * }
   * </pre>
   * environment.toXml() may result in:
   * <pre>
   * {@code
   * <environment>
   *   <hostName>somehost.example.com</hostName>
   *   <ip>10.0.255.255</ip>
   * </environment>
   * }
   * </pre>
   *
   * @return XML representation of POJO tree.
   * @throws NotImplementedException
   */
  public String toXml() {
    throw new NotImplementedException("toXml() method not overridden!");
  }

  /**
   * Override if you need to log as key-value pairs. Default implementation throws NotImplementedException.
   * <p/>
   * You may choose to implement eventCollector.toKeyValuePairs() such that it would result in a map having keys:
   * <pre>
   * 'Event.Resource.URI'
   * 'Event.Subject.ID'
   * 'Event.Action.Name'
   * 'Event.Action.Arguments'
   * 'Event.Action.Outcome.Result'
   * 'Event.Action.Outcome.Error'
   * 'Event.Environment.HostName'
   * 'Event.Environment.IP'
   * </pre>
   * environment.toKeyValuePairs() may result in a map having keys:
   * <pre>
   * 'Environment.HostName'
   * 'Environment.IP'
   * </pre>
   *
   * @return
   * @throws NotImplementedException
   */
  public Map<String, String> toKeyValuePairs() {
    throw new NotImplementedException("toKeyValuePairs() method not overridden!");
  }

  /**
   * Converts the map returned by toKeyValuePairs() to comma separated key value list. Separators can be specified.
   * Override this method to handle any escaping of characters.
   *
   * @param keyAndValueSeparator     Could be '=' or ':' etc. If this character is present in any key or value it won't be escaped.
   * @param successiveEntrySeparator Could be ',' or ';' etc. If this character is present in any key or value it won't be escaped.
   * @return Something like:
   * <pre>
   * Event.Resource.URI=/library/book/45,Event.Subject.ID=johndoe,Event.Subject.SessionId=4162f74a-67f0-4aba-a95a-af1754d355d4,Event.Action.Name=get,Event.Action.Arguments={"book":{"id":45,"author": "William Shakespeare","title": "Hamlet"}},Event.Environment.HostName=somehost.example.com,Event.Environment.IP=10.0.255.255
   * </pre>
   * @throws NotImplementedException if toKeyValuePairs() is not implemented.
   */
  public String toCSKV(String keyAndValueSeparator, String successiveEntrySeparator) {
    StringBuilder sb = new StringBuilder();
    for(Map.Entry<String, String> entry : toKeyValuePairs().entrySet()) {
      sb.append(entry.getKey());
      sb.append(keyAndValueSeparator);
      sb.append(entry.getValue());
      sb.append(successiveEntrySeparator);
    }
    if(sb.length() > 0) {
      sb.deleteCharAt(sb.length() - 1);//Delete the last separator
    }
    return sb.toString();
  }
}
