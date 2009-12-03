/* 
 * Copyright (c) 2004-2008 QOS.ch
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
 */

package org.slf4j.helpers;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.slf4j.Marker;

/**
 * A simple implementation of the {@link Marker} interface.
 * 
 * @author Ceki G&uuml;lc&uuml;
 * @author Joern Huxhorn
 */
public class BasicMarker implements Marker {

  private static final long serialVersionUID = 1803952589649545191L;

  private final String name;
  private List refereceList;

  BasicMarker(String name) {
    if (name == null) {
      throw new IllegalArgumentException("A merker name cannot be null");
    }
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public synchronized void add(Marker reference) {
    if (reference == null) {
      throw new IllegalArgumentException(
          "A null value cannot be added to a Marker as reference.");
    }

    // no point in adding the reference multiple times
    if (this.contains(reference)) {
      return;

    } else if (reference.contains(this)) { // avoid recursion
      // a potential reference should not its future "parent" as a reference
      return;
    } else {
      // let's add the reference
      if (refereceList == null) {
        refereceList = new Vector();
      }
      refereceList.add(reference);
    }

  }

  public synchronized boolean hasReferences() {
    return ((refereceList != null) && (refereceList.size() > 0));
  }
  
  public boolean hasChildren() {
    return hasReferences();
  }

  public synchronized Iterator iterator() {
    if (refereceList != null) {
      return refereceList.iterator();
    } else {
      return Collections.EMPTY_LIST.iterator();
    }
  }

  public synchronized boolean remove(Marker referenceToRemove) {
    if (refereceList == null) {
      return false;
    }

    int size = refereceList.size();
    for (int i = 0; i < size; i++) {
      Marker m = (Marker) refereceList.get(i);
      if (referenceToRemove.equals(m)) {
        refereceList.remove(i);
        return true;
      }
    }
    return false;
  }

  public boolean contains(Marker other) {
    if (other == null) {
      throw new IllegalArgumentException("Other cannot be null");
    }

    if (this.equals(other)) {
      return true;
    }

    if (hasReferences()) {
      for (int i = 0; i < refereceList.size(); i++) {
        Marker ref = (Marker) refereceList.get(i);
        if (ref.contains(other)) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * This method is mainly used with Expression Evaluators.
   */
  public boolean contains(String name) {
    if (name == null) {
      throw new IllegalArgumentException("Other cannot be null");
    }

    if (this.name.equals(name)) {
      return true;
    }

    if (hasReferences()) {
      for (int i = 0; i < refereceList.size(); i++) {
        Marker ref = (Marker) refereceList.get(i);
        if (ref.contains(name)) {
          return true;
        }
      }
    }
    return false;
  }

  private static String OPEN = "[ ";
  private static String CLOSE = " ]";
  private static String SEP = ", ";


  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (!(obj instanceof Marker))
      return false;

    final Marker other = (Marker) obj;
    return name.equals(other.getName());
  }

  public int hashCode() {
    return name.hashCode();
  }

  public String toString() {
    if (!this.hasReferences()) {
      return this.getName();
    }
    Iterator it = this.iterator();
    Marker reference;
    StringBuffer sb = new StringBuffer(this.getName());
    sb.append(' ').append(OPEN);
    while (it.hasNext()) {
      reference = (Marker) it.next();
      sb.append(reference.getName());
      if (it.hasNext()) {
        sb.append(SEP);
      }
    }
    sb.append(CLOSE);

    return sb.toString();
  }
}
