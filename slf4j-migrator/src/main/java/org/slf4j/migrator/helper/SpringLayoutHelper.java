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
        sl.putConstraint(SpringLayout.WEST, componentToPlace, horizontalPadding, SpringLayout.EAST, relativeTo);

        sl.putConstraint(SpringLayout.NORTH, componentToPlace, verticalPadding, SpringLayout.NORTH, relativeTo);
    }

    public void placeToTheRight(Component relativeTo, Component componentToPlace) {
        placeToTheRight(relativeTo, componentToPlace, basicPadding, 0);
    }

    public void placeBelow(Component relativeTo, Component componentToPlace) {
        placeBelow(relativeTo, componentToPlace, 0, basicPadding);
    }

    public void placeBelow(Component relativeTo, Component componentToPlace, int horizontalPadding, int verticalPadding) {
        sl.putConstraint(SpringLayout.WEST, componentToPlace, horizontalPadding, SpringLayout.WEST, relativeTo);

        sl.putConstraint(SpringLayout.NORTH, componentToPlace, verticalPadding, SpringLayout.SOUTH, relativeTo);
    }

}
