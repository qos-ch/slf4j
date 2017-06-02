package org.slf4j.event;

import org.slf4j.Marker;

import java.util.Iterator;

/**
 * Totally fake Marker for testing.
 */
public class NullMarker implements Marker {
  public String getName() {
    return null;
  }

  public void add(Marker reference) {
  }

  public boolean remove(Marker reference) {
    return false;
  }

  public boolean hasChildren() {
    return false;
  }

  public boolean hasReferences() {
    return false;
  }

  public Iterator<Marker> iterator() {
    return null;
  }

  public boolean contains(Marker other) {
    return false;
  }

  public boolean contains(String name) {
    return false;
  }
}
