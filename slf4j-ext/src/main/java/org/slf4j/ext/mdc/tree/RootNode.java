package org.slf4j.ext.mdc.tree;

import org.apache.commons.lang.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

/**
 * Root of the POJO tree which is used to capture event information. The tree must be clonable. This is required for it's internal workings.
 * Simple way of making tree clonable is to make each node clonable.
 * <p/>
 * Recommendations for POJO tree:
 * 1.
 * <p/>
 * Recommendations on usage:
 * 1. Do not use this API for storing sessions, metrics etc. Use it only for the intended purpose - capture event information
 * and log it.
 * 2.
 *
 * @author Himanshu Vijay
 */
public abstract class RootNode<R extends RootNode> extends NonLeafNode<R> {
  private final Class<R> clazz;
  public final String EVENT_MARKER_STRING;
  private final Marker MARKER;
  private static final Logger LOGGER = LoggerFactory.getLogger(RootNode.class);
  private static final InheritableThreadLocal<RootNode> holder = new InheritableThreadLocal<RootNode>() {
    @Override
    protected RootNode initialValue() {
      return RootNode.newInstance();
    }

    @Override
    protected RootNode childValue(RootNode parentValue) {
      if(parentValue != null) {
        return (RootNode) parentValue.copy();
      }
      return RootNode.newInstance();
    }
  };

  /**
   * @param marker Could be something like 'EVENT' or 'AUDIT' etc.
   */
  protected RootNode(String marker, Class<R> type, String name) {
    super(name, type, null);
    clazz = type;
    EVENT_MARKER_STRING = marker;
    MARKER = MarkerFactory.getDetachedMarker(EVENT_MARKER_STRING);
  }

  /**
   * Calls copy(). Ignores the param 'parent'.
   * @param parent Ignored
   * @return
   */
  @Override
  public R copy(Node parent){
    return copy();
  }

  /**
   * Sends the json for this event to log4j log appenders with highest log level i.e. error and marker string 'EVENT'.
   * <p/>
   * Log level = Error is just to ensure that this record does get logged.
   * Log level does not matter here much b'se typically there's something similar inside the json/xml/key-value representation of event itself, something like 'event severity'.
   * <p/>
   * The marker string 'EVENT' is used by log4j2.xml / logback.xml / log4j.properties to distinguish between these records and the regular application logs.
   */
  public void logJson() {
    LOGGER.error(MARKER, toJson());
  }

  public void logXml() {
    LOGGER.error(MARKER, toXml());
  }

  /**
   * Log as comma separated list of key-value pairs.
   */
  public void logCSKV() {
    LOGGER.error(MARKER, toCSKV("=", ","));
  }

  public static RootNode get() {
    return (RootNode) holder.get();
  }

  public static void reset() {
    holder.get().setToDefault();
  }

  /**
   * Replaces only if other != null.
   *
   * @throws CloneNotSupportedException If clone() has not been implemented by child class.
   */
  public static void replace(RootNode other) throws CloneNotSupportedException {
    if(other != null) {
      holder.set((RootNode) other.copy());
    }
  }

  /**
   * <b>Must override this method.</b>
   */
  protected static RootNode newInstance() {
    throw new NotImplementedException();
  }
}
