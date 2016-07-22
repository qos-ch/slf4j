package org.slf4j.helpers;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

public class CollectionHelper {

  public static Vector getValues(Hashtable ht) {
    return toVector(ht.elements(), ht.size());
  }

  public static Vector getKeys(Hashtable ht) {
    return toVector(ht.keys(), ht.size());
  }

  private static Vector toVector(Enumeration values, int size) {
    Vector results = new Vector(size);
    while (values.hasMoreElements()) {
      results.addElement(values.nextElement());
    }
    return results;
  }

  //reimplementation of Queue.drainTo()
  public static int drain(Vector from, Vector to, int maxDrain) {
    synchronized (from) {
      int size = from.size();
      int i;
      for (i = 0; i < size && i < maxDrain; i++) {
        Object o = from.firstElement();
        from.removeElementAt(0);
        to.addElement(o);
      }
      return i;
    }
  }

  public static String toString(Object[] array) {
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < array.length; i++) {
      sb.append(array[i]);
      if (i < array.length-1) {
        sb.append(", ");
      }
    }
    return sb.toString();
  }

}
