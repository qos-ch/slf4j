/*
 * Copyright (c) 2004-2005 SLF4J.ORG
 * Copyright (c) 2004-2005 QOS.ch
 *
 * All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to  deal in  the Software without  restriction, including
 * without limitation  the rights to  use, copy, modify,  merge, publish,
 * distribute, and/or sell copies of  the Software, and to permit persons
 * to whom  the Software is furnished  to do so, provided  that the above
 * copyright notice(s) and this permission notice appear in all copies of
 * the  Software and  that both  the above  copyright notice(s)  and this
 * permission notice appear in supporting documentation.
 *
 * THE  SOFTWARE IS  PROVIDED  "AS  IS", WITHOUT  WARRANTY  OF ANY  KIND,
 * EXPRESS OR  IMPLIED, INCLUDING  BUT NOT LIMITED  TO THE  WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR  A PARTICULAR PURPOSE AND NONINFRINGEMENT
 * OF  THIRD PARTY  RIGHTS. IN  NO EVENT  SHALL THE  COPYRIGHT  HOLDER OR
 * HOLDERS  INCLUDED IN  THIS  NOTICE BE  LIABLE  FOR ANY  CLAIM, OR  ANY
 * SPECIAL INDIRECT  OR CONSEQUENTIAL DAMAGES, OR  ANY DAMAGES WHATSOEVER
 * RESULTING FROM LOSS  OF USE, DATA OR PROFITS, WHETHER  IN AN ACTION OF
 * CONTRACT, NEGLIGENCE  OR OTHER TORTIOUS  ACTION, ARISING OUT OF  OR IN
 * CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 *
 * Except as  contained in  this notice, the  name of a  copyright holder
 * shall not be used in advertising or otherwise to promote the sale, use
 * or other dealings in this Software without prior written authorization
 * of the copyright holder.
 *
 */
package org.slf4j;

import java.util.Iterator;

import junit.framework.TestCase;

import org.slf4j.helpers.BasicMarkerFactory;

/**
 * Unit test BasicMarker
 * 
 * @author Ceki G&uuml;lc&uuml;
 * @author Joern Huxhorn
 */
public class BasicMarkerTest extends TestCase {
  static final String BLUE_STR = "BLUE";
  static final String RED_STR = "RED";
  static final String GREEN_STR = "GREEN";
  static final String COMP_STR = "COMP";
  static final String MULTI_COMP_STR = "MULTI_COMP";
  static final String PARENT_MARKER_STR = "PARENT_MARKER";
  static final String CHILD_MARKER_STR = "CHILD_MARKER";
  
  final IMarkerFactory factory;
  final Marker blue;
  final Marker red;
  final Marker green;
  final Marker comp;
  final Marker multiComp;

  public BasicMarkerTest() {
    factory = new BasicMarkerFactory();

    blue = factory.getMarker(BLUE_STR);
    red = factory.getMarker(RED_STR);
    green = factory.getMarker(GREEN_STR);
    comp = factory.getMarker(COMP_STR);
    comp.add(blue);

    multiComp = factory.getMarker(MULTI_COMP_STR);
    multiComp.add(green);
    multiComp.add(comp);
  }

  public void testPrimitive() {
    assertEquals(BLUE_STR, blue.getName());
    assertTrue(blue.contains(blue));

    Marker blue2 = factory.getMarker(BLUE_STR);
    assertEquals(BLUE_STR, blue2.getName());
    assertEquals(blue, blue2);
    assertTrue(blue.contains(blue2));
    assertTrue(blue2.contains(blue));
  }

  public void testPrimitiveByName() {
    assertTrue(blue.contains(BLUE_STR));
  }

  public void testComposite() {
    assertTrue(comp.contains(comp));
    assertTrue(comp.contains(blue));
  }

  public void testCompositeByName() {
    assertTrue(comp.contains(COMP_STR));
    assertTrue(comp.contains(BLUE_STR));
  }

  public void testMultiComposite() {
    assertTrue(multiComp.contains(comp));
    assertTrue(multiComp.contains(blue));
    assertTrue(multiComp.contains(green));
    assertFalse(multiComp.contains(red));
  }

  public void testMultiCompositeByName() {
    assertTrue(multiComp.contains(COMP_STR));
    assertTrue(multiComp.contains(BLUE_STR));
    assertTrue(multiComp.contains(GREEN_STR));
    assertFalse(multiComp.contains(RED_STR));
  }

  public void testMultiAdd() {
    Marker parent = factory.getMarker(PARENT_MARKER_STR);
    Marker child = factory.getMarker(CHILD_MARKER_STR);
    for (int i = 0; i < 10; i++) {
      parent.add(child);
    }
    
    // check that the child was added once and only once
    Iterator iterator = parent.iterator();
    assertTrue(iterator.hasNext());
    assertEquals(CHILD_MARKER_STR, iterator.next().toString());
    assertFalse(iterator.hasNext());
  }

  public void testAddRemove() {
    final String NEW_PREFIX = "NEW_";
    Marker parent = factory.getMarker(NEW_PREFIX + PARENT_MARKER_STR);
    Marker child = factory.getMarker(NEW_PREFIX + CHILD_MARKER_STR);
    assertFalse(parent.contains(child));
    assertFalse(parent.contains(NEW_PREFIX + CHILD_MARKER_STR));
    assertFalse(parent.remove(child));

    parent.add(child);

    assertTrue(parent.contains(child));
    assertTrue(parent.contains(NEW_PREFIX + CHILD_MARKER_STR));

    assertTrue(parent.remove(child));

    assertFalse(parent.contains(child));
    assertFalse(parent.contains(NEW_PREFIX + CHILD_MARKER_STR));
    assertFalse(parent.remove(child));
  }

}
