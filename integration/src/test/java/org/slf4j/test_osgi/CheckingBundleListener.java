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
package org.slf4j.test_osgi;

import java.util.ArrayList;
import java.util.List;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleListener;

public class CheckingBundleListener implements BundleListener {

    List<BundleEvent> eventList = new ArrayList<BundleEvent>();

    public void bundleChanged(BundleEvent be) {
        eventList.add(be);
    }

    private void dump(BundleEvent be) {
        System.out.println("BE:" + ", source " + be.getSource() + ", bundle=" + be.getBundle() + ", type=" + be.getType());

    }

    public void dumpAll() {
        for (int i = 0; i < eventList.size(); i++) {
            BundleEvent fe = (BundleEvent) eventList.get(i);
            dump(fe);
        }
    }

    boolean exists(String bundleName) {
        for (int i = 0; i < eventList.size(); i++) {
            BundleEvent fe = (BundleEvent) eventList.get(i);
            Bundle b = fe.getBundle();
            System.out.println("===[" + b + "]");
            if (bundleName.equals(b.getSymbolicName())) {
                return true;
            }
        }
        return false;
    }

}
