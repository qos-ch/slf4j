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

package org.slf4j;

import java.io.Serializable;
import java.util.Iterator;

/**
 * Markers are named objects used to enrich log statements. Conforming logging
 * system Implementations of SLF4J determine how information conveyed by markers
 * are used, if at all. In particular, many conforming logging systems ignore
 * marker data.
 * 
 * <p>
 * Markers can contain child markers, which in turn can contain children of
 * their own.
 * 
 * @author Ceki G&uuml;lc&uuml;
 */
public interface Marker extends Serializable {

  /**
   * This constant represents any marker, including a null marker.
   */
  public final String ANY_MARKER = "*";

  /**
   * This constant represents any non-null marker.
   */
  public final String ANY_NON_NULL_MARKER = "+";

  /**
   * Get the name of this Marker.
   * 
   * @return name of marker
   */
  public String getName();

  /**
   * Add a child Marker to this Marker.
   * 
   * @param child
   *                a child marker
   */
  public void add(Marker child);

  /**
   * Remove a child Marker.
   * 
   * @param child
   *                the child Marker to remove
   * @return true if child could be found and removed, false otherwise.
   */
  public boolean remove(Marker child);

  /**
   * Does this marker have children?
   * 
   * @return true if this marker has children, false otherwise.
   */
  public boolean hasChildren();

  /**
   * Returns an Iterator which can be used to iterate over the children of this
   * marker. An empty iterator is returned when this marker has no children.
   * 
   * @return Iterator over the children of this marker
   */
  public Iterator iterator();

  /**
   * Does this marker contain the 'other' marker? Marker A is defined to contain
   * marker B, if A == B or if B is a child of A.
   * 
   * @param other
   *                The marker to test for inclusion.
   * @throws IllegalArgumentException
   *                 if 'other' is null
   * @return Whether this marker contains the other marker.
   */
  public boolean contains(Marker other);

  /**
   * Does this marker contain the marker named 'name'?
   * 
   * If 'name' is null the returned value is always false.
   * 
   * @param other
   *                The marker to test for inclusion.
   * @return Whether this marker contains the other marker.
   */
  public boolean contains(String name);

  /**
   * Markers are considered equal if they have the same name.
   *
   * @param o
   * @return true, if this.name equals o.name
   *
   * @since 1.5.1
   */
  public boolean equals(Object o);

  /**
   * Compute the hash code based on the name of this marker.
   * Note that markers are considered equal if they have the same name.
   * 
   * @return the computed hashCode
   * @since 1.5.1
   */
  public int hashCode();

}
