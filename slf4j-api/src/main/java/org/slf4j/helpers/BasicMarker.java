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
package org.slf4j.helpers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

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
  private List<Marker> refereceList;

  BasicMarker(String name) {
    Util.checkNotNull(name, "A marker name cannot be null");
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public synchronized void add(Marker reference) {
    Util.checkNotNull(reference, "A null value cannot be added to a Marker as reference");

    // no point in adding the reference multiple times
    if (this.contains(reference)) {
      return;
    }

    if (reference.contains(this)) { // avoid recursion
      // a potential reference should not its future "parent" as a reference
      return;
    }

    // let's add the reference
    if (refereceList == null) {
      refereceList = new ArrayList<Marker>();
    }
    refereceList.add(reference);
  }

  public synchronized boolean hasReferences() {
    return refereceList != null && !refereceList.isEmpty();
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
    Util.checkNotNull(referenceToRemove, "referenceToRemove cannot be null");

    if (hasReferences()) {
      int size = refereceList.size();
      for (int i = 0; i < size; i++) {
        Marker m = refereceList.get(i);
        if (referenceToRemove.equals(m)) {
          refereceList.remove(i);
          return true;
        }
      }
    }
    return false;
  }

  public synchronized boolean contains(Marker other) {
    Util.checkNotNull(other, "Other cannot be null");

    if (this.equals(other)) {
      return true;
    }

    if (hasReferences()) {
      for (Marker ref : refereceList) {
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
  public synchronized boolean contains(String name) {
    Util.checkNotNull(name, "name cannot be null");

    if (this.name.equals(name)) {
      return true;
    }

    if (hasReferences()) {
      for (Marker ref : refereceList) {
        if (ref.contains(name)) {
          return true;
        }
      }
    }
    return false;
  }

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
    if (!hasReferences()) {
      return getName();
    }

    StringBuilder sb = new StringBuilder(getName());
    sb.append(' ').append("[ ");
    for (Iterator it = this.iterator(); it.hasNext();) {
      Marker reference = (Marker) it.next();
      sb.append(reference.getName());
      if (it.hasNext()) {
        sb.append(", ");
      }
    }
    sb.append(" ]");
    return sb.toString();
  }
}
