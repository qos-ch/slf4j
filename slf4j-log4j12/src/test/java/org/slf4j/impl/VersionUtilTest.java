/**
 * Copyright (c) 2004-2022 QOS.ch Sarl (Switzerland)
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
package org.slf4j.impl;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class VersionUtilTest {

    @Test
    public void test() {
        System.out.println(System.getProperty("java.version"));
        assertEquals(6, VersionUtil.getJavaMajorVersion("1.6"));
        assertEquals(7, VersionUtil.getJavaMajorVersion("1.7.0_21-b11"));
        assertEquals(8, VersionUtil.getJavaMajorVersion("1.8.0_25"));
    }

    @Test 
    public void testJava9() {
        assertEquals(9, VersionUtil.getJavaMajorVersion("9"));
        assertEquals(9, VersionUtil.getJavaMajorVersion("9.12"));
        assertEquals(9, VersionUtil.getJavaMajorVersion("9ea"));
        
    }

    @Test 
    public void testJava11() {
        assertEquals(11, VersionUtil.getJavaMajorVersion("11"));
        assertEquals(11, VersionUtil.getJavaMajorVersion("11.612"));

    }

}
