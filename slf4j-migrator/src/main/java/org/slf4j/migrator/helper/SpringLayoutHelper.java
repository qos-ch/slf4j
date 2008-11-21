package org.slf4j.migrator.helper;

import java.awt.Component;

import javax.swing.SpringLayout;

public class SpringLayoutHelper {
  
  
  final SpringLayout sl;
  final int basicPadding;
  
  public SpringLayoutHelper(SpringLayout springLayout, int basicPadding) {
    sl = springLayout;
    this.basicPadding = basicPadding;
  }
  
  public void placeToTheRight(Component relativeTo, Component componentToPlace, int horizontalPadding, int verticalPadding) {
    sl.putConstraint(SpringLayout.WEST, componentToPlace, horizontalPadding,
        SpringLayout.EAST, relativeTo);
    
    sl.putConstraint(SpringLayout.NORTH, componentToPlace, verticalPadding,
        SpringLayout.NORTH, relativeTo);
  }
  
  public void placeToTheRight(Component relativeTo, Component componentToPlace) {
    placeToTheRight(relativeTo, componentToPlace, basicPadding, 0);
  }

  public void placeBelow(Component relativeTo, Component componentToPlace) {
    placeBelow(relativeTo,  componentToPlace, 0, basicPadding);
  }

  public void placeBelow(Component relativeTo, Component componentToPlace, int horizontalPadding, int verticalPadding) {
    sl.putConstraint(SpringLayout.WEST, componentToPlace, horizontalPadding,
        SpringLayout.WEST, relativeTo);
    
    sl.putConstraint(SpringLayout.NORTH, componentToPlace, verticalPadding,
        SpringLayout.SOUTH, relativeTo);
  }
  
}
